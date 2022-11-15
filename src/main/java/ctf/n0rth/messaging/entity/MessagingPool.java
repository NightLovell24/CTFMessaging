package ctf.n0rth.messaging.entity;

import redis.clients.jedis.JedisPool;

public class MessagingPool {

    private static JedisPool jedisPoolSub;
    private static JedisPool jedisPoolPub;

    public static JedisPool getJedisPoolSub() {
        if (jedisPoolSub == null) {
            jedisPoolSub = new JedisPool();
        }
        return jedisPoolSub;
    }
    public static JedisPool getJedisPoolPub() {
        if (jedisPoolPub == null) {
            jedisPoolPub = new JedisPool();
        }
        return jedisPoolPub;
    }

    private MessagingPool() {
    }
}
