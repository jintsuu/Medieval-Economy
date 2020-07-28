package economysystem;

import economysystem.EventHandlers.PlayerDeathEventHandler;
import economysystem.EventHandlers.PlayerJoinEventHandler;
import economysystem.Objects.Coinpurse;
import economysystem.Subsystems.CommandSubsystem;
import economysystem.Subsystems.ConfigSubsystem;
import economysystem.Subsystems.StorageSubsystem;
import economysystem.Subsystems.UtilitySubsystem;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;

public final class Main extends JavaPlugin implements Listener {

    // version
    public String version = "v0.7";

    // subsystems
    public StorageSubsystem storage = new StorageSubsystem(this);
    public CommandSubsystem commands = new CommandSubsystem(this);
    public UtilitySubsystem utilities = new UtilitySubsystem(this);
    public ConfigSubsystem config = new ConfigSubsystem(this);

    // saved lists
    public ArrayList<Coinpurse> coinpurses = new ArrayList<>();

    @Override
    public void onEnable() {
        System.out.println("Medieval Economy is enabling...");

        // config creation/loading
        if (!(new File("./plugins/Medieval-Economy/config.yml").exists())) {
            config.saveConfigDefaults();
        }
        else {
            config.handleVersionMismatch();
            reloadConfig();
        }

        this.getServer().getPluginManager().registerEvents(this, this);
        storage.load();
        System.out.println("Medieval Economy is enabled!");
    }

    @Override
    public void onDisable() {
        System.out.println("Medieval Economy is disabling...");
        storage.save();
        System.out.println("Medieval Economy is disabled!");
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        return commands.interpretCommand(sender, label, args);
    }

    @EventHandler()
    public void onJoin(PlayerJoinEvent event) {
        PlayerJoinEventHandler handler = new PlayerJoinEventHandler(this);
        handler.handle(event);
    }

    @EventHandler()
    public void onDeath(PlayerDeathEvent event) {
        PlayerDeathEventHandler handler = new PlayerDeathEventHandler(this);
        handler.handle(event);
    }
}