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
        main.getConfig().addDefault("enablingText", "Medieval Economy is enabling...");
        main.getConfig().addDefault("enabledText", "Medieval Economy is enabled!");
        main.getConfig().addDefault("disablingText", "Medieval Economy is disabling...");
        main.getConfig().addDefault("disabledText", "Medieval Economy is disabled!");
        main.getConfig().addDefault("coinpurseSaveErrorText", "An error occurred saving a Coinpurse Record.");
        main.getConfig().addDefault("coinpurseLoadErrorText", "An error occurred loading ");
        main.getConfig().addDefault("balanceTextStart", "You have ");
        main.getConfig().addDefault("balanceTextEnd", " coins in your coinpurse.");
        main.getConfig().addDefault("balanceNoPermission", "Sorry! In order to run this command, you need the following permission: 'medievaleconomy.balance'");
        main.getConfig().addDefault("depositUsageText", "Usage: /deposit (whole number)");
        main.getConfig().addDefault("depositPositiveText", "Number must be positive!");
        main.getConfig().addDefault("depositTextStart", "You open your coinpurse and deposit ");
        main.getConfig().addDefault("depositTextEnd", " coins.");
        main.getConfig().addDefault("depositNotEnoughCoins", "You don't have that many coins!");
        main.getConfig().addDefault("depositUsageText", "Usage: /deposit (number)");
        main.getConfig().addDefault("depositNoPermission", "Sorry! In order to use this command, you need the permission 'medievaleconomy.deposit'");
        main.getConfig().addDefault("createCurrencyNoPermission", "You need the following permission to use this command: 'medievaleconomy.createcurrency'");
        main.getConfig().addDefault("createCurrencyNoRunFromConsole", "You can't run this command from the console!");
        main.getConfig().addDefault("configReloadedText", "Config reloaded!");
        main.getConfig().addDefault("reloadNoPermission", "You need the following permission to use this command: 'medievaleconomy.reload'");
        main.getConfig().addDefault("withdrawUsageText", "Usage: /withdraw (whole number)");
        main.getConfig().addDefault("withdrawPositiveText", "Number must be positive!");
        main.getConfig().addDefault("withdrawTextStart", "You open your coinpurse and take out ");
        main.getConfig().addDefault("withdrawTextEnd", " coins.");
        main.getConfig().addDefault("withdrawNotEnoughCoins", "You don't have that many coins in your coinpurse!");
        main.getConfig().addDefault("withdrawNoPermission", "Sorry! In order to use this command, you need the permission " + "'medievaleconomy.withdraw'");
        main.getConfig().addDefault("deathMessage", "Your coinpurse feels lighter than it was.");
        main.getConfig().options().copyDefaults(true);
        main.saveConfig();
    }

}
