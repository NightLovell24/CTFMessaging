package ctf.n0rth.gui;

import ctf.n0rth.main.PluginLoader;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

import java.math.BigInteger;
import java.security.SecureRandom;

public class GUIUtils {
    public static final int TIME_WITHOUT_UPDATING = 10;
    private GUIManager guiManager;


    public GUIUtils(GUIManager guiManager) {
        this.guiManager = guiManager;
    }

    public void startOfflineChecking(PluginLoader loader) {
        new BukkitRunnable() {

            @Override
            public void run() {
                guiManager.refreshOfflineStatus();
            }
        }.runTaskTimerAsynchronously(loader, 20, 20);
    }


    public static ChatColor generateRandomColor() {
        int digits = 6;
        String hex =
                String.format("%0" + digits + "x",
                        new BigInteger(digits * 4, new SecureRandom()));

        return ChatColor.of("#" + hex);

    }

    private GUIUtils() {

    }
}
