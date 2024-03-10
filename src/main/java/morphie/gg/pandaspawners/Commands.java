package morphie.gg.pandaspawners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class Commands implements CommandExecutor {

    private PandaSpawners plugin;

    public Commands(PandaSpawners plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("pandaspawners") || cmd.getName().equalsIgnoreCase("pspawners") || cmd.getName().equalsIgnoreCase("ps")) {
            if (args.length == 0) {
                if (sender.hasPermission("pandaspawners.help")) {
                    sender.sendMessage("");
                    sender.sendMessage(new Utils(plugin).addColor(this.plugin.getMessage("Commands.Header")));
                    sender.sendMessage("");
                    sender.sendMessage(new Utils(plugin).addColor(this.plugin.getMessage("Commands.Help")));
                    sender.sendMessage(new Utils(plugin).addColor(this.plugin.getMessage("Commands.Levels")));
                    sender.sendMessage("");
                    if (sender.hasPermission("pandaspawners.admin") || sender.hasPermission("pandaspawners.reload")) {
                        sender.sendMessage(new Utils(plugin).addColor(this.plugin.getMessage("Commands.Spawner")));
                        sender.sendMessage(new Utils(plugin).addColor(this.plugin.getMessage("Commands.Give")));
                        sender.sendMessage(new Utils(plugin).addColor(this.plugin.getMessage("Commands.Reload")));
                    }
                    sender.sendMessage("");
                    sender.sendMessage(new Utils(plugin).addColor(this.plugin.getMessage("Commands.Footer")));
                    sender.sendMessage("");
                    return true;
                } else {
                    sender.sendMessage(new Utils(plugin).addColor(this.plugin.getMessage("ErrorPrefix") + this.plugin.getMessage("NoPermsMessage")));
                    return true;
                }
            } else if (args[0].equalsIgnoreCase("spawner")) {
                if (Bukkit.getPlayer(args[1]) != null) {
                    Player player = Bukkit.getPlayer(args[1]);
                    if (args[2] != null) {
                        if (args[3] != null) {
                            new GiveItem(plugin).giveItem(player, "spawner", args[2].toUpperCase(), args[3]);
                        } else {
                            sender.sendMessage(new Utils(plugin).addColor(plugin.getMessage("ErrorPrefix") + plugin.getMessage("InvalidArgsMessage")));
                        }
                    } else {
                        sender.sendMessage(new Utils(plugin).addColor(plugin.getMessage("ErrorPrefix") + plugin.getMessage("InvalidArgsMessage")));
                    }
                } else {
                    sender.sendMessage(new Utils(plugin).addColor(plugin.getMessage("ErrorPrefix") + plugin.getMessage("InvalidArgsMessage")));
                }
                return true;
            } else if (args[0].equalsIgnoreCase("give")) {
                if (Bukkit.getPlayer(args[1]) != null) {
                    Player player = Bukkit.getPlayer(args[1]);
                    if (args[2] != null) {
                        if (args[3] != null) {
                            new GiveItem(plugin).giveItem(player, "upgrader", args[2].toUpperCase(), args[3]);
                        } else {
                            sender.sendMessage(new Utils(plugin).addColor(plugin.getMessage("ErrorPrefix") + plugin.getMessage("InvalidArgsMessage")));
                        }
                    } else {
                        sender.sendMessage(new Utils(plugin).addColor(plugin.getMessage("ErrorPrefix") + plugin.getMessage("InvalidArgsMessage")));
                    }
                } else {
                    sender.sendMessage(new Utils(plugin).addColor(plugin.getMessage("ErrorPrefix") + plugin.getMessage("InvalidArgsMessage")));
                }
                return true;
            } else if (args[0].equalsIgnoreCase("reload")) {
                if (sender.hasPermission("pandaspawners.admin") || sender.hasPermission("pandaspawners.reload")) {
                    Plugin plugin2 = Bukkit.getServer().getPluginManager().getPlugin("PandaSpawners");
                    if (this.plugin != null) {
                        this.plugin.reloadConfig();
                        this.plugin.getServer().getPluginManager().disablePlugin(plugin2);
                        this.plugin.getServer().getPluginManager().enablePlugin(plugin2);
                        sender.sendMessage(new Utils(plugin).addColor(this.plugin.getMessage("Prefix") + this.plugin.getMessage("ReloadMessage")));
                        return true;
                    }
                } else {
                    sender.sendMessage(new Utils(plugin).addColor(this.plugin.getMessage("ErrorPrefix") + this.plugin.getMessage("NoPermsMessage")));
                    return true;
                }
            } else {
                sender.sendMessage(new Utils(plugin).addColor(plugin.getMessage("ErrorPrefix") + plugin.getMessage("InvalidArgsMessage")));
                return true;
            }
        }
        return false;
    }
}
