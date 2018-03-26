package com.linkedkeeper.redis.client.sample;

import com.linkedkeeper.redis.client.RedisClient;
import com.linkedkeeper.redis.client.driver.RedisDriver;
import com.linkedkeeper.redis.client.exception.RedisException;
import org.apache.log4j.Logger;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.TransactionBlock;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author: zhangsr
 */
public class SimpleRedisClient implements RedisClient {

    private final static Logger log = Logger.getLogger(SimpleRedisClient.class);

    private RedisDriver driver;

    private JedisPool masterJedisPool = null;

    public void init() {
        try {
            masterJedisPool = driver.getMasterJedisPool();
        } catch (Exception e) {
            log.error("SimpleRedisClient init failure.", e);
        }
    }

    public Set<String> keys(String pattern) throws RedisException {
        Jedis jedis = null;
        boolean borrowOrOprSuccess = true;
        try {
            jedis = masterJedisPool.getResource();
            return jedis.keys(pattern);
        } catch (Exception e) {
            borrowOrOprSuccess = false;
            if (jedis != null)
                masterJedisPool.returnBrokenResource(jedis);
            throw new RedisException("execute 'keys " + pattern + "' fail!", e);
        } finally {
            if (borrowOrOprSuccess)
                masterJedisPool.returnResource(jedis);
        }
    }

    /**
     * 设置key对应的值为string类型的value，并指定此键值对应的有效期。
     *
     * @param key
     * @param expire
     * @param value
     */
    public String setex(String key, int expire, String value) throws RedisException {
        Jedis jedis = null;
        boolean borrowOrOprSuccess = true;
        try {
            jedis = masterJedisPool.getResource();
            return jedis.setex(key, expire, value);
        } catch (Exception e) {
            borrowOrOprSuccess = false;
            if (jedis != null)
                masterJedisPool.returnBrokenResource(jedis);
            throw new RedisException("execute 'setex " + key + "' fail!", e);
        } finally {
            if (borrowOrOprSuccess)
                masterJedisPool.returnResource(jedis);
        }
    }

    public String set(String key, String value) throws RedisException {
        Jedis jedis = null;
        boolean borrowOrOprSuccess = true;
        try {
            jedis = masterJedisPool.getResource();
            return jedis.set(key, value);
        } catch (Exception e) {
            borrowOrOprSuccess = false;
            if (jedis != null)
                masterJedisPool.returnBrokenResource(jedis);
            throw new RedisException("execute 'set " + key + "' fail!", e);
        } finally {
            if (borrowOrOprSuccess)
                masterJedisPool.returnResource(jedis);
        }
    }

    public Long decrBy(String key, long integer) throws RedisException {
        Jedis jedis = null;
        boolean borrowOrOprSuccess = true;
        try {
            jedis = masterJedisPool.getResource();
            return jedis.decrBy(key, integer);
        } catch (Exception e) {
            borrowOrOprSuccess = false;
            if (jedis != null)
                masterJedisPool.returnBrokenResource(jedis);
            throw new RedisException("execute 'decrBy " + key + "' fail!", e);
        } finally {
            if (borrowOrOprSuccess)
                masterJedisPool.returnResource(jedis);
        }
    }

    /**
     * key对应的value值加1并返回加1后的值
     *
     * @param key 为String类型
     * @return Long  返回加1后的值
     * @throws RedisException
     */
    public Long incr(String key) throws RedisException {
        Jedis jedis = null;
        boolean borrowOrOprSuccess = true;
        try {
            jedis = masterJedisPool.getResource();
            return jedis.incr(key);
        } catch (Exception e) {
            borrowOrOprSuccess = false;
            if (jedis != null)
                masterJedisPool.returnBrokenResource(jedis);
            throw new RedisException("execute 'incr " + key + "' fail!", e);
        } finally {
            if (borrowOrOprSuccess)
                masterJedisPool.returnResource(jedis);
        }
    }

    public String get(String key) throws RedisException {
        Jedis jedis = null;
        boolean borrowOrOprSuccess = true;
        try {
            jedis = masterJedisPool.getResource();
            return jedis.get(key);
        } catch (Exception e) {
            borrowOrOprSuccess = false;
            masterJedisPool.returnBrokenResource(jedis);
            throw new RedisException("execute 'get " + key + "' fail!", e);
        } finally {
            if (borrowOrOprSuccess) {
                masterJedisPool.returnResource(jedis);
            }
        }
    }

    /**
     * Redis set类型添加元素，等价于Redis中的'sadd key member'命令
     *
     * @param key     set的key值
     * @param members 待添加的元素
     * @return Long  返回成功标识，1为成功，0为失败
     */
    public Long sadd(String key, String... members) throws RedisException {
        Jedis jedis = null;
        boolean borrowOrOprSuccess = true;
        try {
            jedis = masterJedisPool.getResource();
            return jedis.sadd(key, members);
        } catch (Exception e) {
            borrowOrOprSuccess = false;
            if (jedis != null) {
                masterJedisPool.returnBrokenResource(jedis);
            }
            throw new RedisException("execute 'sadd " + key + "' fail!", e);
        } finally {
            if (borrowOrOprSuccess) {
                masterJedisPool.returnResource(jedis);
            }
        }
    }

    public Long srem(String key, String... members) throws RedisException {
        Jedis jedis = null;
        boolean borrowOrOprSuccess = true;
        try {
            jedis = masterJedisPool.getResource();
            return jedis.srem(key, members);
        } catch (Exception e) {
            borrowOrOprSuccess = false;
            if (jedis != null)
                masterJedisPool.returnBrokenResource(jedis);
            throw new RedisException("execute 'srem " + key + "' fail!", e);
        } finally {
            if (borrowOrOprSuccess)
                masterJedisPool.returnResource(jedis);
        }
    }

    /**
     * Redis set类型获取set中的所有元素，等价于Redis中的'smembers key'命令
     *
     * @param key set的key值
     * @return Set<String>
     */
    public Set<String> smembers(String key) throws RedisException {
        Jedis jedis = null;
        boolean borrowOrOprSuccess = true;
        try {
            jedis = masterJedisPool.getResource();
            return jedis.smembers(key);
        } catch (Exception e) {
            borrowOrOprSuccess = false;
            masterJedisPool.returnBrokenResource(jedis);
            throw new RedisException("execute 'smembers " + key + "' fail!", e);
        } finally {
            if (borrowOrOprSuccess) {
                masterJedisPool.returnResource(jedis);
            }
        }
    }

    /**
     * 设置Redis中元素的过期时间，可以为Redis任意类型的元素设置过期时间
     *
     * @param key         redis元素的key
     * @param expiredTime 过期时间，单位：秒
     * @return Long  返回成功标识，1为成功，0为失败
     */
    public Long expire(String key, int expiredTime) throws RedisException {
        Jedis jedis = null;
        boolean borrowOrOprSuccess = true;
        try {
            jedis = masterJedisPool.getResource();
            return jedis.expire(key, expiredTime);
        } catch (Exception e) {
            borrowOrOprSuccess = false;
            if (jedis != null)
                masterJedisPool.returnBrokenResource(jedis);
            throw new RedisException("execute 'expire " + key + "' fail!", e);
        } finally {
            if (borrowOrOprSuccess)
                masterJedisPool.returnResource(jedis);
        }
    }

    public Boolean exists(String key) throws RedisException {
        Jedis jedis = null;
        boolean borrowOrOprSuccess = true;
        try {
            jedis = masterJedisPool.getResource();
            return jedis.exists(key);
        } catch (Exception e) {
            borrowOrOprSuccess = false;
            if (jedis != null)
                masterJedisPool.returnBrokenResource(jedis);
            throw new RedisException("execute 'exists " + key + "' fail!", e);
        } finally {
            if (borrowOrOprSuccess)
                masterJedisPool.returnResource(jedis);
        }
    }

    /**
     * 删除参数key指定的元素
     *
     * @param key
     * @return Long  返回成功标识
     */
    public Long del(String key) throws RedisException {
        Jedis jedis = null;
        boolean borrowOrOprSuccess = true;
        try {
            jedis = masterJedisPool.getResource();
            return jedis.del(key);
        } catch (Exception e) {
            borrowOrOprSuccess = false;
            if (jedis != null)
                masterJedisPool.returnBrokenResource(jedis);
            throw new RedisException("execute 'del " + key + "' fail!", e);
        } finally {
            if (borrowOrOprSuccess)
                masterJedisPool.returnResource(jedis);
        }
    }

    public Long del(String... key) throws RedisException {
        Jedis jedis = null;
        boolean borrowOrOprSuccess = true;
        try {
            jedis = masterJedisPool.getResource();
            return jedis.del(key);
        } catch (Exception e) {
            borrowOrOprSuccess = false;
            if (jedis != null)
                masterJedisPool.returnBrokenResource(jedis);
            throw new RedisException("execute 'del " + key + "' fail!", e);
        } finally {
            if (borrowOrOprSuccess)
                masterJedisPool.returnResource(jedis);
        }
    }

    /**
     * 对应Redis中的"hset key field value"命令
     *
     * @param key   Redis中hash的key值
     * @param field
     * @param value
     * @return boolean  返回成功标识，true为成功，false为失败
     * @throws RedisException
     */
    public Long hset(String key, String field, String value) throws RedisException {
        Jedis jedis = null;
        boolean borrowOrOprSuccess = true;
        try {
            jedis = masterJedisPool.getResource();
            return jedis.hset(key, field, value);
        } catch (Exception e) {
            borrowOrOprSuccess = false;
            if (jedis != null)
                masterJedisPool.returnBrokenResource(jedis);
            throw new RedisException("execute 'hset " + key + "' fail!", e);
        } finally {
            if (borrowOrOprSuccess)
                masterJedisPool.returnResource(jedis);
        }
    }

    /**
     * 对应Redis中的"hsetnx key field value"命令
     *
     * @param key   Redis中hash的key值
     * @param field
     * @param value
     * @return Long  返回成功标识，1为成功，0为失败
     * @throws RedisException
     */
    public Long hsetnx(String key, String field, String value) throws RedisException {
        Jedis jedis = null;
        boolean borrowOrOprSuccess = true;
        try {
            jedis = masterJedisPool.getResource();
            return jedis.hsetnx(key, field, value);
        } catch (Exception e) {
            borrowOrOprSuccess = false;
            if (jedis != null)
                masterJedisPool.returnBrokenResource(jedis);
            throw new RedisException("execute 'hset " + key + "' fail!", e);
        } finally {
            if (borrowOrOprSuccess)
                masterJedisPool.returnResource(jedis);
        }
    }

    public Long hdel(String key, String... fields) throws RedisException {
        Jedis jedis = null;
        boolean borrowOrOprSuccess = true;
        try {
            jedis = masterJedisPool.getResource();
            return jedis.hdel(key, fields);
        } catch (Exception e) {
            borrowOrOprSuccess = false;
            if (jedis != null)
                masterJedisPool.returnBrokenResource(jedis);
            throw new RedisException("execute 'hdel " + key + "' fail!", e);
        } finally {
            if (borrowOrOprSuccess)
                masterJedisPool.returnResource(jedis);
        }
    }

    /**
     * 对应Redis中的"hget key field"命令
     *
     * @param key
     * @param field
     * @return String 返回field对应的value值
     * @throws RedisException
     */
    public String hget(String key, String field) throws RedisException {
        Jedis jedis = null;
        boolean borrowOrOprSuccess = true;
        try {
            jedis = masterJedisPool.getResource();
            return jedis.hget(key, field);
        } catch (Exception e) {
            borrowOrOprSuccess = false;
            masterJedisPool.returnBrokenResource(jedis);
            throw new RedisException("execute 'hget " + key + "' fail!", e);
        } finally {
            if (borrowOrOprSuccess) {
                masterJedisPool.returnResource(jedis);
            }
        }
    }

    /**
     * 对应Redis中的"hmset key field1 value1 field2 value2 field3 value3"命令
     *
     * @param key  Redis中hash的key值
     * @param hash 多个field、value值对
     * @return String 返回成功标识
     * @throws RedisException
     */
    public String hmset(String key, Map<String, String> hash) throws RedisException {
        Jedis jedis = null;
        boolean borrowOrOprSuccess = true;
        try {
            jedis = masterJedisPool.getResource();
            return jedis.hmset(key, hash);
        } catch (Exception e) {
            borrowOrOprSuccess = false;
            if (jedis != null)
                masterJedisPool.returnBrokenResource(jedis);
            throw new RedisException("execute 'hmset " + key + "' fail!", e);
        } finally {
            if (borrowOrOprSuccess)
                masterJedisPool.returnResource(jedis);
        }
    }

    /**
     * 对应Redis中的"hmget key field1 field2 field3"命令
     * 如果key对应的hash表在Redis中不存在，则返回的List<String>size等于field个数，所有结果均为null
     *
     * @param key    Redis中hash的key值
     * @param fields 调用者可以传入一个String[]类型的变量，也可以传入多个String类型的变量
     * @return List<String> 按照field顺序返回多个field的value值
     * @throws RedisException
     */
    public List<String> hmget(String key, String... fields) throws RedisException {
        Jedis jedis = null;
        boolean borrowOrOprSuccess = true;
        try {
            jedis = masterJedisPool.getResource();
            return jedis.hmget(key, fields);
        } catch (Exception e) {
            borrowOrOprSuccess = false;
            if (jedis != null)
                masterJedisPool.returnBrokenResource(jedis);
            throw new RedisException("execute 'hmget " + key + "' fail!", e);
        } finally {
            if (borrowOrOprSuccess)
                masterJedisPool.returnResource(jedis);
        }
    }

    public Map<String, String> hgetAll(String key) throws RedisException {
        Jedis jedis = null;
        boolean borrowOrOprSuccess = true;
        try {
            jedis = masterJedisPool.getResource();
            return jedis.hgetAll(key);
        } catch (Exception e) {
            borrowOrOprSuccess = false;
            if (jedis != null)
                masterJedisPool.returnBrokenResource(jedis);
            throw new RedisException("execute 'hgetAll " + key + "' fail!", e);
        } finally {
            if (borrowOrOprSuccess)
                masterJedisPool.returnResource(jedis);
        }
    }

    public List<Object> multi(TransactionBlock transactionBlock) {
        Jedis jedis = null;
        boolean borrowOrOprSuccess = true;
        try {
            jedis = masterJedisPool.getResource();
            return jedis.multi(transactionBlock);
        } catch (Exception e) {
            borrowOrOprSuccess = false;
            if (jedis != null)
                masterJedisPool.returnBrokenResource(jedis);
            throw new RedisException("execute 'multi' fail!", e);
        } finally {
            if (borrowOrOprSuccess)
                masterJedisPool.returnResource(jedis);
        }
    }

    public Long lpush(String key, String... strings) {
        Jedis jedis = null;
        boolean borrowOrOprSuccess = true;
        try {
            jedis = masterJedisPool.getResource();
            return jedis.lpush(key, strings);
        } catch (Exception e) {
            borrowOrOprSuccess = false;
            if (jedis != null)
                masterJedisPool.returnBrokenResource(jedis);
            throw new RedisException("execute 'lpush' fail!", e);
        } finally {
            if (borrowOrOprSuccess)
                masterJedisPool.returnResource(jedis);
        }
    }

    public Long rpush(String key, String... strings) {
        Jedis jedis = null;
        boolean borrowOrOprSuccess = true;
        try {
            jedis = masterJedisPool.getResource();
            return jedis.rpush(key, strings);
        } catch (Exception e) {
            borrowOrOprSuccess = false;
            if (jedis != null)
                masterJedisPool.returnBrokenResource(jedis);
            throw new RedisException("execute 'lpush' fail!", e);
        } finally {
            if (borrowOrOprSuccess)
                masterJedisPool.returnResource(jedis);
        }
    }

    public String lpop(String key) {
        Jedis jedis = null;
        boolean borrowOrOprSuccess = true;
        try {
            jedis = masterJedisPool.getResource();
            return jedis.lpop(key);
        } catch (Exception e) {
            borrowOrOprSuccess = false;
            if (jedis != null)
                masterJedisPool.returnBrokenResource(jedis);
            throw new RedisException("execute 'lpop' fail!", e);
        } finally {
            if (borrowOrOprSuccess)
                masterJedisPool.returnResource(jedis);
        }
    }

    public String rpop(String key) {
        Jedis jedis = null;
        boolean borrowOrOprSuccess = true;
        try {
            jedis = masterJedisPool.getResource();
            return jedis.rpop(key);
        } catch (Exception e) {
            borrowOrOprSuccess = false;
            if (jedis != null)
                masterJedisPool.returnBrokenResource(jedis);
            throw new RedisException("execute 'rpop' fail!", e);
        } finally {
            if (borrowOrOprSuccess)
                masterJedisPool.returnResource(jedis);
        }
    }

    public List<String> lrange(String key, long start, long end) {
        Jedis jedis = null;
        boolean borrowOrOprSuccess = true;
        try {
            jedis = masterJedisPool.getResource();
            return jedis.lrange(key, start, end);
        } catch (Exception e) {
            borrowOrOprSuccess = false;
            if (jedis != null)
                masterJedisPool.returnBrokenResource(jedis);
            throw new RedisException("execute 'multi' fail!", e);
        } finally {
            if (borrowOrOprSuccess)
                masterJedisPool.returnResource(jedis);
        }
    }

    public String ltrim(String key, long start, long end) {
        Jedis jedis = null;
        boolean borrowOrOprSuccess = true;
        try {
            jedis = masterJedisPool.getResource();
            return jedis.ltrim(key, start, end);
        } catch (Exception e) {
            borrowOrOprSuccess = false;
            if (jedis != null)
                masterJedisPool.returnBrokenResource(jedis);
            throw new RedisException("execute 'multi' fail!", e);
        } finally {
            if (borrowOrOprSuccess)
                masterJedisPool.returnResource(jedis);
        }
    }

    @Override
    public Long llen(String key) {
        Jedis jedis = null;
        boolean borrowOrOprSuccess = true;
        try {
            jedis = masterJedisPool.getResource();
            return jedis.llen(key);
        } catch (Exception e) {
            borrowOrOprSuccess = false;
            if (jedis != null)
                masterJedisPool.returnBrokenResource(jedis);
            throw new RedisException("execute 'llen' fail!", e);
        } finally {
            if (borrowOrOprSuccess)
                masterJedisPool.returnResource(jedis);
        }
    }

    @Override
    public String rpoplpush(String srckey, String dstkey) {
        Jedis jedis = null;
        boolean borrowOrOprSuccess = true;
        try {
            jedis = masterJedisPool.getResource();
            return jedis.rpoplpush(srckey, dstkey);
        } catch (Exception e) {
            borrowOrOprSuccess = false;
            if (jedis != null)
                masterJedisPool.returnBrokenResource(jedis);
            throw new RedisException("execute 'rpoplpush' fail!", e);
        } finally {
            if (borrowOrOprSuccess)
                masterJedisPool.returnResource(jedis);
        }
    }

    @Override
    public Long hincrby(String key, String field, int increment) {
        Jedis jedis = null;
        boolean borrowOrOprSuccess = true;
        try {
            jedis = masterJedisPool.getResource();
            return jedis.hincrBy(key, field, increment);
        } catch (Exception e) {
            borrowOrOprSuccess = false;
            if (jedis != null)
                masterJedisPool.returnBrokenResource(jedis);
            throw new RedisException("execute 'hincrby' fail!", e);
        } finally {
            if (borrowOrOprSuccess)
                masterJedisPool.returnResource(jedis);
        }
    }

    @Override
    public void handleDataChange(String path, String data) {
    }

    //------------------------- setter ----------------------

    public void setDriver(RedisDriver driver) {
        this.driver = driver;
    }
}
