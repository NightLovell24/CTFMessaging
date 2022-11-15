package ctf.n0rth.messaging.manager;

import ctf.n0rth.config.model.Server;
import ctf.n0rth.messaging.entity.MessagingPool;
import org.bukkit.Bukkit;
import redis.clients.jedis.Jedis;



import java.util.Set;


import static ctf.n0rth.messaging.channels.MessagingChannels.CHANNEL_OUTPUT_NAME;

public class MessagingManager {



    public static void sendRequest(Set<Server> serverList) {
        try (Jedis jedis = MessagingPool.getJedisPoolPub().getResource()) {
            for (Server server : serverList) {

                jedis.publish(CHANNEL_OUTPUT_NAME, server.getName());
            }
        }
    }

    private MessagingManager() {

    }

}
