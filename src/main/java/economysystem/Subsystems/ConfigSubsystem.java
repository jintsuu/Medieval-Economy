package economysystem.Subsystems;

import economysystem.Main;

import java.io.File;

public class ConfigSubsystem {

    Main main = null;

    public ConfigSubsystem(Main plugin) {
        main = plugin;
    }

    public void handleVersionMismatch() {

        if (!main.getConfig().getString("version").equalsIgnoreCase(main.version)) {
            System.out.println("[ALERT] Verson mismatch! Saving old config as config.yml.old and loading in the default values.");
            renameConfigToConfigDotOldAndSaveDefaults();
        }

    }

    public void renameConfigToConfigDotOldAndSaveDefaults() {
        // save old config as config.yml.old
        File saveFile = new File("./plugins/MedievalEconomy/config.yml");
        if (saveFile.exists()) {

            // rename file
            File newSaveFile = new File("./plugins/MedievalEconomy/config.yml.old");
            saveFile.renameTo(newSaveFile);

            // save defaults
            saveConfigDefaults();

        }
    }

    public void saveConfigDefaults() {
        main.getConfig().addDefault("version", main.version);
        main.getConfig().options().copyDefaults(true);
        main.saveConfig();
    }

}
