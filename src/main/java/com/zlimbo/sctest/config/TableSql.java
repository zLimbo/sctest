package com.zlimbo.sctest.config;

import java.util.Arrays;

public class TableSql {
    private String tableName;
    private String createSqlInMysql;
    private String createSql;
    private String insertSql;
    private String[] selectSql;

    public TableSql(String tableName, String createSqlInMysql, String createSql, String insertSql, String[] selectSql) {
        this.tableName = tableName;
        this.createSqlInMysql = createSqlInMysql;
        this.createSql = createSql;
        this.insertSql = insertSql;
        this.selectSql = selectSql;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getCreateSqlInMysql() {
        return createSqlInMysql;
    }

    public void setCreateSqlInMysql(String createSqlInMysql) {
        this.createSqlInMysql = createSqlInMysql;
    }

    public String getCreateSql() {
        return createSql;
    }

    public void setCreateSql(String createSql) {
        this.createSql = createSql;
    }

    public String getInsertSql() {
        return insertSql;
    }

    public void setInsertSql(String insertSql) {
        this.insertSql = insertSql;
    }

    public String[] getSelectSql() {
        return selectSql;
    }

    public void setSelectSql(String[] selectSql) {
        this.selectSql = selectSql;
    }

    @Override
    public String toString() {
        return "TableSql{" +
                "\n\ttableName='" + tableName + '\'' +
                ", \n\tcreateSql='" + createSql + '\'' +
                ", \n\tinsertSql='" + insertSql + '\'' +
                ", \n\tselectSql=" + Arrays.toString(selectSql) +
                '}';
    }


}
