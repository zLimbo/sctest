package com.zlimbo.sctest.config;

import java.util.Arrays;

public class ScTestConfig {

    ConnConfig sqlcita;
    ConnConfig mysql;
    int clientNum = 1;
    int requestNum = 100;
    long requestInterval = 2;
    String requestDistribution = "universe";
    String[] tableSqlFiles;

    public ScTestConfig(ConnConfig sqlcita, ConnConfig mysql, int clientNum, int requestNum, long requestInterval, String requestDistribution, String[] files) {
        this.sqlcita = sqlcita;
        this.mysql = mysql;
        this.clientNum = clientNum;
        this.requestNum = requestNum;
        this.requestInterval = requestInterval;
        this.requestDistribution = requestDistribution;
        this.tableSqlFiles = files;
    }

    @Override
    public String toString() {
        return "SqlcitaTestConfig{" +
                "\n\tsqlcita=" + sqlcita +
                ", \n\tmysql=" + mysql +
                ", \n\tclientNum=" + clientNum +
                ", \n\trequestNum=" + requestNum +
                ", \n\trequestInterval=" + requestInterval +
                ", \n\trequestDistribution='" + requestDistribution + '\'' +
                ", \n\tfiles=" + Arrays.toString(tableSqlFiles) +
                '}';
    }

    public ConnConfig getSqlcita() {
        return sqlcita;
    }

    public void setSqlcita(ConnConfig sqlcita) {
        this.sqlcita = sqlcita;
    }

    public ConnConfig getMysql() {
        return mysql;
    }

    public void setMysql(ConnConfig mysql) {
        this.mysql = mysql;
    }

    public int getClientNum() {
        return clientNum;
    }

    public void setClientNum(int clientNum) {
        this.clientNum = clientNum;
    }

    public int getRequestNum() {
        return requestNum;
    }

    public void setRequestNum(int requestNum) {
        this.requestNum = requestNum;
    }

    public long getRequestInterval() {
        return requestInterval;
    }

    public void setRequestInterval(long requestInterval) {
        this.requestInterval = requestInterval;
    }

    public String getRequestDistribution() {
        return requestDistribution;
    }

    public void setRequestDistribution(String requestDistribution) {
        this.requestDistribution = requestDistribution;
    }

    public String[] getTableSqlFiles() {
        return tableSqlFiles;
    }

    public void setTableSqlFiles(String[] tableSqlFiles) {
        this.tableSqlFiles = tableSqlFiles;
    }
}
