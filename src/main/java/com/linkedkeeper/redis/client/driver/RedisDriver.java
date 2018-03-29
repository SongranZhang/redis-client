package com.linkedkeeper.redis.client.driver;

import com.linkedkeeper.redis.client.config.RedisPoolConfig;
import com.linkedkeeper.redis.client.exception.RedisException;
import org.apache.log4j.Logger;
import redis.clients.jedis.JedisPool;

/**
 * @Author: zhangsr
 */
public class RedisDriver {

    private final static Logger log = Logger.getLogger(RedisDriver.class);

    private JedisPool masterJedisPool = null;
    private RedisPoolConfig redisPoolConfig = null;

    private String redisConf = null;
    private String redisPassword = null;

    final int timeout = 60 * 60;

    /**
     * 初始化redis
     *
     * @throws com.linkedkeeper.redis.client.exception.RedisException
     */
    public void init() throws RedisException {
        synchronized (this) {
            try {
                initRedisPoolConf(redisConf);
            } catch (Exception e) {
                throw new RedisException(e);
            }
            initRedisClient();
        }
    }

    /**
     * 注销redis
     */
    public void destroy() {
        destroyRedisClient();
        redisPoolConfig = null;
    }

    /**
     * zk获取数据后初始化redis参数信息
     *
     * @param redisConfData
     * @throws java.io.IOException
     */
    private void initRedisPoolConf(String redisConfData) {

        redisPoolConfig = new RedisPoolConfig();

        redisPoolConfig.setMasterConfString(redisConfData);
        redisPoolConfig.setMaxIdle(200);
        redisPoolConfig.setMaxTotal(300);
        redisPoolConfig.setTestOnBorrow(false);
        redisPoolConfig.setTestOnReturn(false);
    }

    /**
     * 根据配置信息初始化redis客户端
     */
    private void initRedisClient() {
        String masterConfString = redisPoolConfig.getMasterConfString();
        log.info("master info = " + masterConfString);

        String[] masterConf = masterConfString.split(":");
        if (masterConf.length == 1) {
            throw new ExceptionInInitializerError(masterConfString + " is not host:port");
        }
//        this.masterJedisPool = new JedisPool(redisPoolConfig, masterConf[0], Integer.parseInt(masterConf[1]));
        this.masterJedisPool = new JedisPool(redisPoolConfig, masterConf[0], Integer.parseInt(masterConf[1]), timeout, redisPassword);
        log.info("init master successful");
    }

    private void destroyRedisClient() {
        if (masterJedisPool != null) {
            masterJedisPool.destroy();
            log.info("destroy master successful!");
        }
    }

    //------------------------------ getter --------------------------

    public JedisPool getMasterJedisPool() {
        return masterJedisPool;
    }

    public void setRedisConf(String redisConf) {
        this.redisConf = redisConf;
    }

    public void setRedisPassword(String redisPassword) {
        this.redisPassword = redisPassword;
    }

}
