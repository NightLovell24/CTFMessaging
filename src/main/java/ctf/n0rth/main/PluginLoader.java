package ctf.n0rth.main;

import ctf.n0rth.config.CTFConfig;
import ctf.n0rth.events.EventListener;
import ctf.n0rth.gui.GUIListener;
import ctf.n0rth.gui.GUIManager;
import ctf.n0rth.gui.GUIUtils;
import ctf.n0rth.messaging.channels.MessagingChannels;
import ctf.n0rth.messaging.entity.MessagingPool;
import ctf.n0rth.messaging.listener.MessagingListener;
import ctf.n0rth.messaging.manager.MessagingManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class PluginLoader extends JavaPlugin {
    private GUIManager guiManager;

    @Override
    public void onEnable() {
        CTFConfig.setup();
        CTFConfig.getFileConfigurationGUI().options().copyDefaults(true);
        CTFConfig.getFileConfigurationServers().options().copyDefaults(true);
        CTFConfig.save();

        guiManager = new GUIManager();

        Bukkit.getServer().getPluginManager().registerEvents(new EventListener(guiManager), this);
        Bukkit.getServer().getPluginManager().registerEvents(new GUIListener(this, guiManager), this);
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");



        startRedisSubscribe(guiManager);
        startRepeatingPublish();
        startOfflineChecking(this);
    }

    private void startOfflineChecking(PluginLoader pluginLoader) {
        GUIUtils guiUtils = new GUIUtils(guiManager);
        guiUtils.startOfflineChecking(pluginLoader);
    }

    private void startRedisSubscribe(GUIManager guiManager) {
        JedisPool jedisPool = MessagingPool.getJedisPoolSub();
        MessagingListener messagingListener = new MessagingListener(guiManager);

        try (Jedis jedis = jedisPool.getResource();) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    jedis.subscribe(messagingListener, MessagingChannels.CHANNEL_INPUT_NAME);
                }
            }.runTaskAsynchronously(this);
        }
    }

    private void startRepeatingPublish() {
        new BukkitRunnable() {
            @Override
            public void run() {
                MessagingManager.sendRequest(guiManager.getServers());
            }
        }.runTaskTimer(this, 20, 20);
    }


    @Override
    public void onDisable() {
        this.getServer().getMessenger().unregisterOutgoingPluginChannel(this);
    }
}
