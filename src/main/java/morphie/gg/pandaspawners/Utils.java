package morphie.gg.pandaspawners;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTItem;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.bukkit.ChatColor.COLOR_CHAR;

public class Utils {

    private PandaSpawners plugin;

    public Utils(PandaSpawners plugin) {
        this.plugin = plugin;
    }

    public ItemStack createItem(String material, String type, String spawnerType,  int amount, int modeldata, String displayname, ArrayList<String> lore, boolean glow) {
        ItemStack localItemStack = new ItemStack(Material.matchMaterial(material), amount);
        ItemMeta localItemMeta = localItemStack.getItemMeta();
        localItemMeta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES });
        localItemMeta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_POTION_EFFECTS });
        localItemMeta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_DESTROYS });
        if (glow) {
            localItemMeta.addEnchant(Enchantment.DURABILITY, 1, true);
            localItemMeta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ENCHANTS });
        }
        localItemMeta.setDisplayName(addColor(displayname));
        localItemMeta.setLore(lore);
        localItemMeta.setCustomModelData(modeldata);
        if (type.equalsIgnoreCase("SPAWNER")) {
            BlockStateMeta bsm =  (BlockStateMeta) localItemMeta;
            CreatureSpawner cs = (CreatureSpawner) bsm.getBlockState();
            cs.setSpawnedType(EntityType.valueOf(spawnerType));
            bsm.setBlockState(cs);
            localItemStack.setItemMeta(bsm);
        } else if (type.equalsIgnoreCase("UPGRADER")) {
            localItemStack.setItemMeta(localItemMeta);
            NBTItem nbtItem = new NBTItem(localItemStack);
            NBTCompound comp = nbtItem.addCompound("upgrader");
            NBTCompound comp2 = nbtItem.addCompound(spawnerType);
            comp.setString("displayName", displayname);
            comp.setInteger("modelNumber", modeldata);
            comp2.setString("displayName", displayname);
            comp2.setInteger("modelNumber", modeldata);
            return nbtItem.getItem();
        }
        localItemStack.setItemMeta(localItemMeta);
        return localItemStack;
    }

    public String addColor(String message) {
        if (message == null) {
            return null;
        }
        String hexMessage = this.translateHexColorCodes("#", message);
        return ChatColor.translateAlternateColorCodes('&', hexMessage);
    }

    public String translateHexColorCodes(String startTag, String message) {
        final Pattern hexPattern = Pattern.compile(startTag + "([A-Fa-f0-9]{6})");
        Matcher matcher = hexPattern.matcher(message);
        StringBuffer buffer = new StringBuffer(message.length() + 4 * 8);
        while (matcher.find()) {
            String group = matcher.group(1);
            matcher.appendReplacement(buffer, COLOR_CHAR + "x"
                    + COLOR_CHAR + group.charAt(0) + COLOR_CHAR + group.charAt(1)
                    + COLOR_CHAR + group.charAt(2) + COLOR_CHAR + group.charAt(3)
                    + COLOR_CHAR + group.charAt(4) + COLOR_CHAR + group.charAt(5)
            );
        }
        return matcher.appendTail(buffer).toString();
    }

    public String fixCase(String string) {

        StringBuilder builder = new StringBuilder();
        for(String string2 : string.split("_"))
            builder.append(string2.substring(0,1).toUpperCase() + string2.substring(1).toLowerCase() + " ");
        String name = builder.toString();
        return removeLastChar(name);
    }

    public String removeLastChar(String s) {
        if (s == null || s.length() == 0) {
            return s;
        }
        return s.substring(0, s.length()-1);
    }

    public ItemStack createUpgradeItem(String type, Integer amount) {
        int Number = 0;
        int Count = 0;
        while (this.plugin.getConfig().getString("SpawnerUpgraders." + Count + ".Type") != null) {
            if (this.plugin.getConfig().getString("SpawnerUpgraders." + Count + ".Type").equalsIgnoreCase(type)) {
                Number = Count;
                break;
            } else {

            }
            Count++;
        }
        String material = this.plugin.getConfig().getString("SpawnerUpgraders." + Number + ".Item");
        Integer modelData = this.plugin.getConfig().getInt("SpawnerUpgraders." + Number + ".CustomModelData");
        String name = this.plugin.getConfig().getString("SpawnerUpgraders." + Number + ".Name");
        Boolean glow = this.plugin.getConfig().getBoolean("SpawnerUpgraders." + Number + ".Glow");
        String spawnerType = this.plugin.getConfig().getString("SpawnerUpgraders." + Number + ".Spawner");
        ArrayList<String> upgraderLore = new ArrayList();
        for (String s : plugin.getMessageList("Spawner.Lore")) {
            upgraderLore.add(new Utils(plugin).addColor(s.replace("%TYPE%", new Utils(plugin).fixCase(type))));
        }
        return createItem(material, "UPGRADER", spawnerType, amount, modelData, name, upgraderLore, glow);
    }
}
