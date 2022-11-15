package ctf.n0rth.events;

import ctf.n0rth.config.CTFConfig;
import ctf.n0rth.config.CTFGUIConfigHelper;
import ctf.n0rth.config.CTFServersConfigHelper;
import ctf.n0rth.config.model.Server;
import ctf.n0rth.gui.GUIManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;



public class EventListener implements Listener {

    private GUIManager guiManager;

    public EventListener(GUIManager guiManager) {
        this.guiManager = guiManager;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    private void playerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        ItemStack gui = CTFGUIConfigHelper.getGUIItemStack();
        int slot = CTFGUIConfigHelper.getGUIItemSlot();
        p.getInventory().setItem(slot, gui);
    }

    @EventHandler
    private void openGui(PlayerInteractEvent e) {
        if (e.getAction().equals(Action.RIGHT_CLICK_AIR)
                || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            Player p = e.getPlayer();
            if (p.getInventory().getItemInMainHand().equals(CTFGUIConfigHelper.getGUIItemStack())) {
                guiManager.openInventory(p);
            }
        }
    }
}
