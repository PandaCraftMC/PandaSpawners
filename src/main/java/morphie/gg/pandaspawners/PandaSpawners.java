package morphie.gg.pandaspawners;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.List;

public class PandaSpawners extends JavaPlugin {

    public Messages messagescfg;

    public void onEnable () {
        createConfig();
        loadConfigManager();
        getCommand("pandaspawners").setExecutor(new Commands(this));
    }

    private void createConfig() {
        try {
            if (!getDataFolder().exists()) {
                getDataFolder().mkdirs();
            }
            File file = new File(getDataFolder(), "config.yml");
            if (!file.exists()) {
                getConfig().options().copyDefaults(true);
                saveDefaultConfig();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadConfigManager() {
        this.messagescfg = new Messages(this);
        this.messagescfg.setup();
    }

    public String getMessage(String string) {
        String gotString = this.messagescfg.messagesCFG.getString(string);
        if (gotString != null) return gotString;
        return "Null message";
    }

    public List<String> getMessageList(String string) {
        return this.messagescfg.messagesCFG.getStringList(string);
    }
}
