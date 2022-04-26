package com.zlimbo.sctest.config;

public class ConnConfig {
    String host;
    String port;
    String database;
    String user;
    String password;

    public ConnConfig(String host, String port, String database, String user, String password) {
        this.host = host;
        this.port = port;
        this.database = database;
        this.user = user;
        this.password = password;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConnInfo() {
        return "[" + user + "@" + host + ":" + port + "/" + database + "]";
    }

    @Override
    public String toString() {
        return "ConnInfo{" +
                "host='" + host + '\'' +
                ", port='" + port + '\'' +
                ", databases='" + database + '\'' +
                ", user='" + user + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
