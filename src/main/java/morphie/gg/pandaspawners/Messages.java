package morphie.gg.pandaspawners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;

import java.io.File;
import java.io.IOException;

public class Messages implements Listener {
    private PandaSpawners plugin;
    public FileConfiguration messagesCFG;
    public File messagesFile;

    public Messages(PandaSpawners plugin) {
        this.plugin = plugin;
    }

    public void setup() {
        if (!this.plugin.getDataFolder().exists()) {
            this.plugin.getDataFolder().mkdir();
        }
        this.messagesFile = new File(this.plugin.getDataFolder(), "messages.yml");
        if (!this.messagesFile.exists()) {
            try {
                this.messagesFile.createNewFile();

                this.messagesCFG = YamlConfiguration.loadConfiguration(this.messagesFile);

                this.addDefaults(this.messagesCFG);

                this.messagesCFG.options().copyDefaults(true);
                saveMessages();
            } catch (IOException e) {
                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Could not create the messages.yml file");
            }
        }
        this.messagesCFG = YamlConfiguration.loadConfiguration(this.messagesFile);

        this.addDefaults(this.messagesCFG);
    }

    public void saveMessages() {
        try {
            this.messagesCFG.save(this.messagesFile);
        } catch (IOException e) {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Could not save the messages.yml file");
        }
    }

    public void reloadMessages() {
        this.messagesCFG = YamlConfiguration.loadConfiguration(this.messagesFile);
        this.addDefaults(this.messagesCFG);
    }

    private void addDefaults(FileConfiguration cfg) {
        cfg.addDefault("Commands.Header", "&7&m_______________&7[&2&lPandaSpawners&7]&m_______________");
        cfg.addDefault("Commands.Footer", "&7&m____________________&r &8[&2&l!&8] &7&m____________________");
        cfg.addDefault("Commands.Help", "&a/pspawners &8- &7Shows this text menu.");
        cfg.addDefault("Commands.Levels", "&a/pspawners levels &8- &7Shows spawner upgrade progression.");
        cfg.addDefault("Commands.GiveItem", "&c&l[Admin] &a/pspawners giveitem <itemname> <player> <amount>");
        cfg.addDefault("Commands.Reload", "&c&l[Admin] &a/pspawners reload");
        cfg.addDefault("Prefix", "&8[&2&lPS&8] &r");
        cfg.addDefault("ErrorPrefix", "&4[&cX&4] &r");
        cfg.addDefault("NoPermsMessage", "&7You do not have permission to use this command.");
        cfg.addDefault("InvalidArgsMessage", "&7Invalid arguments! &a/ps &7to view all commands.");
        cfg.addDefault("ReloadMessage", "&7Plugin has successfully been reloaded!");
    }
}
