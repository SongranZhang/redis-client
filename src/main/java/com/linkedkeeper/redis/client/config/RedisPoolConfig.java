package com.linkedkeeper.redis.client.config;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

/**
 * @Author: zhangsr
 */
public class RedisPoolConfig extends GenericObjectPoolConfig {

    private String masterConfString = null;

    private int database = 0;

    public RedisPoolConfig() {
        setTestWhileIdle(true);
        setMinEvictableIdleTimeMillis(60000);
        setTimeBetweenEvictionRunsMillis(30000);
        setNumTestsPerEvictionRun(-1);
    }

    //--------------------------- setter && getter ----------------------

    public String getMasterConfString() {
        return masterConfString;
    }

    public void setMasterConfString(String masterConfString) {
        this.masterConfString = masterConfString;
    }

    public int getDatabase() {
        return database;
    }

    public void setDatabase(int database) {
        this.database = database;
    }

}
