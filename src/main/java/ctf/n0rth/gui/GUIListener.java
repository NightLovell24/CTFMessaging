package ctf.n0rth.gui;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import ctf.n0rth.main.PluginLoader;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class GUIListener implements Listener {

    private PluginLoader plugin;
    private GUIManager guiManager;

    public GUIListener(PluginLoader plugin, GUIManager guiManager) {
        this.plugin = plugin;
        this.guiManager = guiManager;
    }

    @EventHandler
    private void chooseTeam(InventoryClickEvent e) {
        if (guiManager.getInventory() == null) return;
        Inventory inventory = guiManager.getInventory();
        if (e.getView().getTitle().equals(GUIManager.INVENTORY_NAME)) {

            if (e.getRawSlot() >= inventory.getSize() + 1) {
                e.setCancelled(true);
            }
            if (e.getCurrentItem() != null) {
                if (e.getCurrentItem().getItemMeta() != null) {
                    if (e.getCurrentItem().getItemMeta().getDisplayName() != null) {
                        String serverName = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());
                        if (GUIManager.isTheServer(serverName)) {

                            ByteArrayDataOutput out = ByteStreams.newDataOutput();
                            out.writeUTF("Connect");
                            out.writeUTF(serverName);
                            Player p = (Player) e.getWhoClicked();
                            p.sendPluginMessage(plugin, "BungeeCord", out.toByteArray());
                        }

                    }
                }
            }
            e.setCancelled(true);
        }

    }
}
