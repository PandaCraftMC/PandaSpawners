package morphie.gg.pandaspawners;

import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
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
        if (cursorNBT.hasKey("upgrader")) {
            p.sendMessage("WORKS");
        }
    }
}
