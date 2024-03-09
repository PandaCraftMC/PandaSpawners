package morphie.gg.pandaspawners;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.bukkit.ChatColor.COLOR_CHAR;

public class Utils {
    public ItemStack createItem(String material, int amount, int modeldata, String displayname, ArrayList<String> lore, boolean glow) {
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

    public String translateHexColorCodes(String startTag, String message)
    {
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

}
