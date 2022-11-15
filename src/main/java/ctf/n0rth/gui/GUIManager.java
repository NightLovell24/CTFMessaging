package ctf.n0rth.gui;

import ctf.n0rth.config.CTFServersConfigHelper;
import ctf.n0rth.config.model.Server;
import ctf.n0rth.config.model.ServerStatus;
import ctf.n0rth.messaging.manager.MessagingManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Collectors;

public class GUIManager {
    public static final String INVENTORY_NAME = ChatColor.GREEN + "Выбрать сервер";
    private static final String ONLINE_TITLE =
            net.md_5.bungee.api.ChatColor.of("#B4AEA3") + "Онлайн " + ChatColor.WHITE +
                    ": ";
    private static Inventory inventory;
    private static Set<Server> servers;
    private static Map<Server, Date> serverDateMap = new HashMap<>();


    public void openInventory(Player p) {
        updateInventory();
        p.openInventory(inventory);
    }

    public synchronized void refreshOfflineStatus() {
        List<Server> changedServer = new ArrayList<>();

        for (Map.Entry<Server, Date> serverDateEntry : serverDateMap.entrySet()) {
            if (serverDateEntry.getKey().getServerStatus().equals(ServerStatus.OFFLINE)) continue;
            Date currentDate = new Date();
            Date lastServerUpdateDate = serverDateEntry.getValue();
            long timeDiff = (currentDate.getTime() - lastServerUpdateDate.getTime()) / 1000;
            if (timeDiff >= GUIUtils
                    .TIME_WITHOUT_UPDATING) {

                changedServer.add(serverDateEntry.getKey());
            }

        }

        for (Server server : changedServer) {
            server.setServerInfo(null);
            server.setServerStatus(ServerStatus.OFFLINE);
            updateServer(server);
        }
    }

    static {
        GUIManager.servers = new CopyOnWriteArraySet<>();
        initializeServers();
        updateDates();
    }

    private static void initializeServers() {
        for (Server server : CTFServersConfigHelper.getServers()) {
            servers.add(server);
        }
    }

    private static void updateDates() {
        for (Server server : servers) {
            serverDateMap.put(server, new Date());
        }
    }

    private void updateDate(Server server) {
        serverDateMap.put(server, new Date());
    }

    private synchronized void updateInventory() {
        if (GUIManager.servers == null) {
            GUIManager.servers = new CopyOnWriteArraySet<>();
            initializeServers();
            updateDates();
        }
        ///////////////////////////////////////////////////////////
        MessagingManager.sendRequest(servers);
        ///////////////////////////////////////////////////////////
        createAndUpdateInventoryContent();
    }

    public static boolean isTheServer(String serverName) {
        for (Server server : servers) {
            if (serverName.equals(server.getName())) {
                return true;
            }
        }
        return false;
    }

    private void createAndUpdateInventoryContent() {
        createIfNotExist(servers.size());
        updateInventoryContent();
    }

    public synchronized boolean updateServer(Server server) {
        Server toDelete = null;
        for (Server server1 : servers) {
            if (server1.getName().equals(server.getName())) {
                toDelete = server1;
                break;
            }
        }
        if (toDelete != null) {
            servers.remove(toDelete);
        }
        boolean updated = servers.add(server);
        if (updated) {
            updateDate(server);
        }
        createAndUpdateInventoryContent();
        return updated;
    }


    private int getIndexOfServer(String serverName) {
        String source = serverName.split("#")[1];
        return Integer.parseInt(source.split(" ")[0]);
    }

    private void updateInventoryContent() {
        int index = 0;
        Comparator<Server> comparator = Comparator.comparingInt(el -> getIndexOfServer(el.getName()));
        LinkedHashSet<Server> tempServers =
                servers.stream().
                        sorted(comparator).collect(Collectors.toCollection(LinkedHashSet::new));
        for (Server server : tempServers) {

            updateServerSlot(index, server);
            index++;
        }
    }

    private void updateServerSlot(int index, Server server) {
        ItemStack itemStack = new ItemStack(getServerMaterial(server), 1);
        ItemMeta itemMeta = itemStack.getItemMeta();
        net.md_5.bungee.api.ChatColor chatColor = net.md_5.bungee.api.ChatColor.of(server.getHexColor());
        itemMeta.setDisplayName(chatColor + server.getName());
        itemMeta.setLore(getServerLore(server));
        itemStack.setItemMeta(itemMeta);

        inventory.setItem(index, itemStack);
    }

    private Material getServerMaterial(Server server) {
        ServerStatus serverStatus = server.getServerStatus();
        switch (serverStatus) {
            case OFFLINE:
                return Material.RED_WOOL;
            case STARTED:
                return Material.YELLOW_WOOL;
            case WAITING:
                return Material.GREEN_WOOL;
            default:
                return null;
        }
    }

    private List<String> getServerLore(Server server) {
        List<String> lore = new ArrayList<>();
        ServerStatus serverStatus = server.getServerStatus();
        switch (serverStatus) {
            case STARTED:
                lore.add(ChatColor.GOLD + "Игра уже запущена!");
                lore.add(getCurrentOnline(server.getServerInfo().getCountOfPlayers()));
                lore.add(getCurrentTimer(server.getServerInfo().getTimer()));
                break;
            case OFFLINE:
                lore.add(ChatColor.RED + "Сервер отключен");
                break;
            case WAITING:
                lore.add(ChatColor.GREEN + "Присоединиться");
                lore.add(getCurrentOnline(server.getServerInfo().getCountOfPlayers()));
                break;
        }

        return lore;
    }

    private String getCurrentTimer(String timer) {
        if (timer == null) {
            return net.md_5.bungee.api.ChatColor.of("#28B88A") + "Запуск игры в процессе";
        }
        return net.md_5.bungee.api.ChatColor.of("#E1A0BD") + "Длительность партии " +
                ChatColor.WHITE + ": " + ChatColor.GREEN + timer;
    }

    private String getCurrentOnline(String countOfPlayers) {
        String[] parts = countOfPlayers.split("/");
        String info = ONLINE_TITLE +
                ChatColor.AQUA + "[" + ChatColor.GREEN + parts[0] + ChatColor.WHITE + "/" +
                ChatColor.DARK_PURPLE + parts[1] + ChatColor.AQUA + "]";

        return info;
    }

    public synchronized Server getServerByName(String serverName) {
        for (Server currentServer : servers) {
            if (currentServer.getName().equals(serverName)) {
                return currentServer;
            }
        }
        return null;
    }

    public Set<Server> getServers() {
        return servers;
    }

    private void createIfNotExist(int size) {
        int newInventorySize = getNewInventorySize(size);
        if (inventory == null) {
            inventory = Bukkit.createInventory(null, newInventorySize, INVENTORY_NAME);
        }
    }

    private int getNewInventorySize(int size) {

        return ((size / 9) + 1) * 9;
    }

    public Inventory getInventory() {
        return inventory;
    }
}
