package com.uwu.wdnmd.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DatabaseConnectionPool {
    public static HikariDataSource ds;

    public static void setConnection() {
        try {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl("jdbc:mysql://localhost:3306/wdnmd");
            config.setUsername("root");
            config.setPassword("P@ssw0rd");
            config.setDriverClassName("com.mysql.cj.jdbc.Driver");
            config.addDataSourceProperty("cachePrepStmts", "true");
            config.addDataSourceProperty("prepStmtCacheSize", "250");
            config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

            ds = new HikariDataSource(config);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
