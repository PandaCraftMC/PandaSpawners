package morphie.gg.pandaspawners;

import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;

public class Events implements Listener {
    private final PandaSpawners plugin;
    public Events(PandaSpawners plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, (Plugin) plugin);
    }

    @EventHandler
    public void clickEvent(InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player))
            return;
        Player p = (Player)e.getWhoClicked();
        if (!e.getInventory().getType().equals(InventoryType.CRAFTING))
            return;
        ItemStack cursor = e.getCursor();
        if (cursor == null)
            return;
        ItemStack current = e.getCurrentItem();
        if (current == null)
            return;
        if (current.getType().equals(Material.AIR) || cursor.getType().equals(Material.AIR))
            return;
        NBTItem cursorNBT = new NBTItem(cursor);
        if (cursorNBT.hasKey("upgrader").booleanValue()){
            if (current.getType().name().equalsIgnoreCase("spawner")) {
                BlockStateMeta bsm =  (BlockStateMeta) current.getItemMeta();
                CreatureSpawner cs = (CreatureSpawner) bsm.getBlockState();
                EntityType eType = cs.getSpawnedType();
                int Number = -1;
                int Count = 0;
                while (this.plugin.getConfig().getString("SpawnerUpgraders." + Count) != null) {
                    if (this.plugin.getConfig().getString("SpawnerUpgraders." + Count + ".Spawner").equals(eType.name().toUpperCase())) {
                        if (cursorNBT.hasKey(eType.name().toUpperCase())) {
                            Number = Count;
                        }
                    }
                    Count++;
                }
                if (Number == -1) {
                    p.sendMessage(new Utils(plugin).addColor(plugin.getMessage("ErrorPrefix") + plugin.getMessage("InvalidUpgraderMessage")));
                    return;
                }
                Integer currentAmount = current.getAmount();
                Integer cursorAmount = cursor.getAmount();
                if (cursorAmount >= currentAmount) {
                    cursor.setAmount(cursorAmount - currentAmount);
                    current.setAmount(cursorAmount - currentAmount);
                    String spawnerType = this.plugin.getConfig().getString("SpawnerUpgraders." + Number + ".Type");
                    new GiveItem(plugin).giveItem(p, false,"spawner", spawnerType.toUpperCase(), "" + currentAmount);
                    p.sendMessage(new Utils(plugin).addColor(plugin.getMessage("Prefix") + plugin.getMessage("SpawnerUpgradedMessage")));
                } else {
                    p.sendMessage(new Utils(plugin).addColor(plugin.getMessage("ErrorPrefix") + plugin.getMessage("InvalidUpgraderAmountMessage").replace("%SPAWNER_AMOUNT%", "" + currentAmount)));
                }
            }
        }
    }
}
