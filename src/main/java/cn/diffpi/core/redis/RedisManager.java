package cn.diffpi.core.redis;

import java.util.Set;

import redis.clients.jedis.Jedis;

public class RedisManager {
    private static Jedis jedis;

    static {
        jedis = new Jedis("127.0.0.1", 6379);
    }

    /**
     * 删除key，可以是一个，也可以是多个key
     *
     * @param keys
     */
    public synchronized static Set<String> get(String key) {
        return getJedis().zrange(key, 0, 127);
    }

    /**
     * 删除key，可以是一个，也可以是多个key
     *
     * @param keys
     */
    public synchronized static void deleteKey(String... keys) {
        getJedis().del(keys);
    }

    /**
     * 删除匹配的key<br>
     * 如以my为前缀的则 参数为"my*"
     *
     * @param key
     */
    public synchronized static void deleteKeys(String pattern) {
        //列出所有匹配的key
        Set<String> keySet = getJedis().keys(pattern);
        if (keySet == null || keySet.size() <= 0) {
            return;
        }
        String keyArr[] = new String[keySet.size()];
        int i = 0;
        for (String keys : keySet) {
            keyArr[i] = keys;
            i++;
        }
        deleteKey(keyArr);
    }

    /**
     * 删除前缀为{参数}的所有key<br>
     *
     * @param prefix
     */
    public synchronized static void deleteKeyByPrefix(String prefix) {
        deleteKeys(prefix + "*");
    }


    /**
     * 删除包含{参数}的所有key<br>
     *
     * @param contain
     */
    public synchronized static void deleteKeyByContain(String contain) {
        deleteKeys("*" + contain + "*");
    }

    /**
     * 删除当前中所有key
     */
    public synchronized static void flushdb() {
        getJedis().flushDB();
    }

    private static Jedis getJedis() {
        return jedis;
    }

    public static void destroy() {
        jedis.close();
    }
}
