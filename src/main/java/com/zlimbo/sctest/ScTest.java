package com.zlimbo.sctest;

import com.alibaba.fastjson.JSONObject;
import com.zlimbo.sctest.config.ScTestConfig;
import com.zlimbo.sctest.config.TableSql;
import com.zlimbo.sctest.connect.JdbcConn;
import com.zlimbo.sctest.display.ProgressBar;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.HashMap;

public class ScTest {

    static final String configFileName = "./config/test-config.json";

    private ScTestConfig scTestConfig;
    private JdbcConn mysql;
    private JdbcConn sqlcita;

    private HashMap<String, TableSql> tableSqls;

    public ScTest() throws SQLException, IOException {
        String configJson = new String(Files.readAllBytes(Paths.get(configFileName)), StandardCharsets.UTF_8);
        scTestConfig = JSONObject.parseObject(configJson, ScTestConfig.class);
        mysql = new JdbcConn(scTestConfig.getMysql());
        sqlcita = new JdbcConn(scTestConfig.getSqlcita());

        tableSqls = new HashMap<>(scTestConfig.getTableSqlFiles().length);
        for (String fileName: scTestConfig.getTableSqlFiles()) {
            String json = new String(Files.readAllBytes(Paths.get(fileName)), StandardCharsets.UTF_8);
            tableSqls.put(fileName, JSONObject.parseObject(json, TableSql.class));
        }
    }

    public void sqlInsertRepeat(String sql, int repeatNum) throws SQLException {
        ProgressBar progressBar = new ProgressBar(1, repeatNum, "sql insert repeat");
        for (int i = 0; i < repeatNum; ++i) {
            sqlcita.sqlUpdate(sql);
            progressBar.step(i + 1);
        }
    }

    public void testCreateAndManyInsert() throws Exception {
        int requestNum = scTestConfig.getRequestNum();
        for (TableSql tableSql: tableSqls.values()) {

            System.out.printf("\n==== test table [%s] start ====\n", tableSql.getTableName());
            System.out.print("> mysql create table ...");
            long start = System.nanoTime();
            mysql.sqlUpdate(tableSql.getCreateSqlInMysql());
            double mysqlCreateTake = (System.nanoTime() - start) / 1e9;
            System.out.printf("\r> mysql create table success, take: %.2fs\n", mysqlCreateTake);

            System.out.print("> sqlcita create table ...");
            start = System.nanoTime();
            sqlcita.sqlUpdate(tableSql.getCreateSql());
            double sqlcitaCreateTake = (System.nanoTime() - start) / 1e9;
            System.out.printf("\r> sqlcita create table success, take: %.2fs\n", sqlcitaCreateTake);

            System.out.println("> sqlcita insert repeat " + requestNum + " times");
            start = System.nanoTime();
            ProgressBar onChainProgressBar = new ProgressBar(1, requestNum, "up chain count");
            sqlInsertRepeat(tableSql.getInsertSql(), requestNum);

            String onChainCountSql = "select count(*) from " + tableSql.getTableName() + " where ON_CHAIN=1";
            System.out.print("> select on chain tx count ...\n");
            double timeout = Math.max(requestNum / 10.0, 20); // 设置超时时间
            double take = 0.0;
            int onChainCount = 0;
            while (true) {
                onChainCount = mysql.queryCount(onChainCountSql);
                take = (System.nanoTime() - start) / 1e9;
                if (onChainCount > requestNum) {
                    System.out.printf("> error: onChainCount(%d) > requestNum(%d)\n", onChainCount, requestNum);
                    throw new Exception();
                }
                onChainProgressBar.step(onChainCount);
                if (onChainCount == requestNum) {
                    break;
                }
                if (take > timeout) {
                    onChainProgressBar.teminate();
                    System.out.printf("> timeout and terminate\n");
                    break;
                }
//                Thread.sleep((int)(timeout * 1e3 / 100));
            }
//            double tps = requestNum / take;
//            System.out.printf("> on chain count: %d, take:%.2fs, tps:%.2f\n", onChainCount, take, tps);
            System.out.print("> mysql drop table ...");
            mysql.sqlUpdate("drop table " + tableSql.getTableName());
            System.out.print("\r> mysql drop table success");
            System.out.printf("\n==== test table [%s] finish ====\n", tableSql.getTableName());
        }
    }

    public static void main(String[] args) throws Exception {
        ScTest scTest = new ScTest();
        scTest.testCreateAndManyInsert();
    }
}
