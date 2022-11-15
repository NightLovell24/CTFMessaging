package ctf.n0rth.config;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CTFGUIConfigHelper {
    private static final String ITEM_MATERIAL = "material";
    private static final String ITEM_NAME = "name";
    private static final String ITEM_SLOT = "slot";

    private static final String DEFAULT_ITEM_MATERIAL_VALUE = Material.COMPASS.name();
    private static final String DEFAULT_ITEM_MATERIAL_NAME = ChatColor.AQUA + "Список серверов";
    private static final int DEFAULT_ITEM_SLOT_VALUE = 4;

    public static void configure() {
        CTFConfig.setDefaultParameterGUI(ITEM_MATERIAL, DEFAULT_ITEM_MATERIAL_VALUE);
        CTFConfig.setDefaultParameterGUI(ITEM_NAME, DEFAULT_ITEM_MATERIAL_NAME);
        CTFConfig.setDefaultParameterGUI(ITEM_SLOT, DEFAULT_ITEM_SLOT_VALUE);

    }

    public static int getGUIItemSlot() {
        return Integer.parseInt(CTFConfig.getParameterGUI(ITEM_SLOT));
    }

    public static ItemStack getGUIItemStack() throws IllegalArgumentException {
        String materialName = CTFConfig.getParameterGUI(ITEM_MATERIAL);
        String itemName = CTFConfig.getParameterGUI(ITEM_NAME);
        ItemStack itemStack = new ItemStack(Material.valueOf(materialName));
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(itemName);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    private CTFGUIConfigHelper() {

    }

}
