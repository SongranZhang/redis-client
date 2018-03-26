package com.linkedkeeper.redis.client;

import com.linkedkeeper.redis.client.exception.RedisException;
import redis.clients.jedis.TransactionBlock;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author: zhangsr
 */
public interface RedisClient {

    Set<String> keys(final String pattern) throws RedisException;

    /**
     * 设置key对应的值为string类型的value，并指定此键值对应的有效期。
     *
     * @param key
     * @param expire
     * @param value
     */
    String setex(String key, int expire, String value) throws RedisException;

    String set(String key, String value) throws RedisException;

    Long decrBy(String key, long integer) throws RedisException;

    /**
     * key对应的value值加1并返回加1后的值
     *
     * @param key 为String类型
     * @return Long  返回加1后的值
     * @throws RedisException
     */
    Long incr(String key) throws RedisException;

    String get(String key) throws RedisException;

    Long sadd(String key, String... members) throws RedisException;

    Long srem(final String key, final String... members) throws RedisException;

    /**
     * Redis set类型获取set中的所有元素，等价于Redis中的'smembers key'命令
     *
     * @param key set的key值
     * @return Set<String>
     */
    Set<String> smembers(String key) throws RedisException;

    /**
     * 设置Redis中元素的过期时间，可以为Redis任意类型的元素设置过期时间
     *
     * @param key         redis元素的key
     * @param expiredTime 过期时间，单位：秒
     * @return Long  返回成功标识，1为成功，0为失败
     */
    Long expire(String key, int expiredTime) throws RedisException;

    Boolean exists(String key) throws RedisException;

    /**
     * 删除参数key指定的元素
     *
     * @param key
     * @return Long  返回成功标识
     */
    Long del(final String key) throws RedisException;

    Long del(final String... key) throws RedisException;

    /**
     * 对应Redis中的"hset key field value"命令
     *
     * @param key   Redis中hash的key值
     * @param field
     * @param value
     * @return boolean  返回成功标识，true为成功，false为失败
     * @throws RedisException
     */
    Long hset(String key, String field, String value) throws RedisException;

    /**
     * 对应Redis中的"hsetnx key field value"命令
     *
     * @param key   Redis中hash的key值
     * @param field
     * @param value
     * @return Long  返回成功标识，1为成功，0为失败
     * @throws RedisException
     */
    Long hsetnx(String key, String field, String value) throws RedisException;

    Long hdel(String key, String... fields) throws RedisException;

    /**
     * 对应Redis中的"hget key field"命令
     *
     * @param key
     * @param field
     * @return String 返回field对应的value值
     * @throws RedisException
     */
    String hget(String key, String field) throws RedisException;

    /**
     * 对应Redis中的"hmset key field1 value1 field2 value2 field3 value3"命令
     *
     * @param key  Redis中hash的key值
     * @param hash 多个field、value值对
     * @return String 返回成功标识
     * @throws RedisException
     */
    String hmset(String key, Map<String, String> hash) throws RedisException;

    /**
     * 对应Redis中的"hmget key field1 field2 field3"命令
     * 如果key对应的hash表在Redis中不存在，则返回的List<String>size等于field个数，所有结果均为null
     *
     * @param key    Redis中hash的key值
     * @param fields 调用者可以传入一个String[]类型的变量，也可以传入多个String类型的变量
     * @return List<String> 按照field顺序返回多个field的value值
     * @throws RedisException
     */
    List<String> hmget(String key, String... fields) throws RedisException;

    Map<String, String> hgetAll(String key) throws RedisException;

    List<Object> multi(TransactionBlock transactionBlock);

    Long lpush(String key, String... strings);

    Long rpush(String key, String... strings);

    String lpop(String key);

    String rpop(String key);

    List<String> lrange(String key, long start, long end);

    String ltrim(String key, long start, long end);

    Long llen(String key);

    String rpoplpush(String srckey, String dstkey);

    Long hincrby(String key, String field, int increment);

    void handleDataChange(String path, String data);
}
