package morphie.gg.pandaspawners;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class GiveItem {

    private PandaSpawners plugin;

    public GiveItem(PandaSpawners plugin) {
        this.plugin = plugin;
    }

    public ItemStack giveItem (Player player, String type, String SpawnerType) {
        switch (type.toLowerCase()) {
            case "spawner":
                if (hasAvaliableSlot(player)) {
                    Inventory inv = player.getInventory();
                    ArrayList<String> spawnerLore = new ArrayList();
                    for (String s : plugin.getMessageList("Menu.FilterItem.Lore")) {
                        spawnerLore.add(new Utils().addColor(s));
                    }
                    inv.addItem(new Utils().createItem("SPAWNER", 1, 0, plugin.getConfig().getString("SpawnerUpgrader.DisplayName"), spawnerLore, true ))
                }
        }
        return null;
    }

    public boolean hasAvaliableSlot(Player player){
        Inventory inv = player.getInventory();;
        for (ItemStack item: inv.getContents()) {
            if(item == null) {
                return true;
            }
        }
        return false;
    }
}
