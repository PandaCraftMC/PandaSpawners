package morphie.gg.pandaspawners;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class GiveItem {

    private PandaSpawners plugin;

    public GiveItem(PandaSpawners plugin) {
        this.plugin = plugin;
    }

    public ItemStack giveItem (Player player, boolean message, String type, String MobType, String amount) {
        if (type.equalsIgnoreCase("spawner")) {
            String mobName = MobType;
            EntityType etype = null;
            try {
                etype = EntityType.valueOf(mobName);
            } catch (IllegalArgumentException exp) {
                player.sendMessage(new Utils(plugin).addColor(plugin.getMessage("ErrorPrefix") + plugin.getMessage("InvalidSpawnerType")));
                return null;
            }
            if (convertStringToInt(amount) <= 0) {
                player.sendMessage(new Utils(plugin).addColor(plugin.getMessage("ErrorPrefix") + plugin.getMessage("InvalidAmount")));
                return null;
            }
            Inventory inv = player.getInventory();
            ArrayList<String> spawnerLore = new ArrayList();
            for (String s : plugin.getMessageList("Spawner.Lore")) {
                spawnerLore.add(new Utils(plugin).addColor(s.replace("%TYPE%", new Utils(plugin).fixCase(MobType))));
            }
            if (player.getInventory().firstEmpty() >= 0) {
                inv.addItem(new Utils(plugin).createItem("SPAWNER", "SPAWNER", MobType, Integer.parseInt(amount), 0, plugin.getMessage("Spawner.Name"), spawnerLore, true));
                if (message) {
                    player.sendMessage(new Utils(plugin).addColor(plugin.getMessage("Prefix") + plugin.getMessage("SpawnerGiveMessage").replace("%TYPE%", new Utils(plugin).fixCase(MobType))));
                }
            } else {
                player.sendMessage(new Utils(plugin).addColor(plugin.getMessage("ErrorPrefix") + plugin.getMessage("InventoryFull")));
            }
        } else if (type.equalsIgnoreCase("upgrader")) {
            String mobName2 = MobType;
            EntityType etype2 = null;
            try {
                etype2 = EntityType.valueOf(mobName2);
            } catch (IllegalArgumentException exp) {
                player.sendMessage(new Utils(plugin).addColor(plugin.getMessage("ErrorPrefix") + plugin.getMessage("InvalidSpawnerType")));
                return null;
            }
            if (convertStringToInt(amount) <= 0) {
                player.sendMessage(new Utils(plugin).addColor(plugin.getMessage("ErrorPrefix") + plugin.getMessage("InvalidAmount")));
                return null;
            }
            Inventory inv2 = player.getInventory();
            if (player.getInventory().firstEmpty() >= 0) {
                inv2.addItem(new Utils(plugin).createUpgradeItem(MobType, Integer.parseInt(amount)));
                player.sendMessage(new Utils(plugin).addColor(plugin.getMessage("Prefix") + plugin.getMessage("UpgraderGiveMessage").replace("%TYPE%", new Utils(plugin).fixCase(MobType))));
            } else {
                player.sendMessage(new Utils(plugin).addColor(plugin.getMessage("ErrorPrefix") + plugin.getMessage("InventoryFull")));
            }
        }
        return null;
    }

    private int convertStringToInt(String str){
        try {
            return Integer.parseInt(str); //return the int if parsing succeeds
        }
        catch(NullPointerException | NumberFormatException e){
            return 0; //return default value otherwise
        }
    }
}
