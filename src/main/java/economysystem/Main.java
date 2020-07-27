package economysystem;

import economysystem.Commands.BalanceCommand;
import economysystem.Commands.DepositCommand;
import economysystem.Commands.EconCommand;
import economysystem.Commands.WithdrawCommand;
import economysystem.Subsystems.CommandSubsystem;
import economysystem.Subsystems.StorageSubsystem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public final class Main extends JavaPlugin implements Listener {

    // subsystems
    StorageSubsystem storage = new StorageSubsystem(this);

    // saved lists
    public ArrayList<Coinpurse> coinpurses = new ArrayList<>();

    @Override
    public void onEnable() {
        System.out.println("Medieval Economy is enabling...");

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
        CommandSubsystem commands = new CommandSubsystem(this);
        return commands.interpretCommand(sender, label, args);
    }

    public void addCurrencyToInventory(Player player, int amount) {
        // if player's inventory has space
        if (!(player.getInventory().firstEmpty() == -1)) {
            player.getInventory().addItem(getCurrency(amount));
            player.sendMessage(ChatColor.GREEN + "" + amount + " currency created.");
        }
        else { // player's inventory is full
            player.sendMessage(ChatColor.RED + "Inventory full.");
        }
    }

    public void removeCurrencyFromInventory(Player player, int amount) {
        player.getInventory().removeItem(getCurrency(amount));
    }

    public ItemStack getCurrency(int amount) {
        ItemStack currencyItem = new ItemStack(Material.GOLD_NUGGET, amount);
        ItemMeta meta = currencyItem.getItemMeta();

        meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "Gold Coin");
        List<String> lore = new ArrayList<String>();
        lore.add("");
        lore.add(ChatColor.GOLD + "" + ChatColor.ITALIC + "The currency of the Continent.");
        lore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "Best kept in a coinpurse.");
        lore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "useful commands: /balance /deposit /withdraw");

        meta.setLore(lore);
        currencyItem.setItemMeta(meta);

        return currencyItem;
    }

    public static void sendHelpMessage(Player player) {
        player.sendMessage(ChatColor.AQUA + "/econ help - Show a helpful list of commands.");
        if (player.hasPermission("medievaleconomy.createcurrency")) {
            player.sendMessage(ChatColor.AQUA + "/econ createcurrency # - Bring more currency into the world.");
        }
    }

    @EventHandler()
    public void onJoin(PlayerJoinEvent event) {
        if (!event.getPlayer().hasPlayedBefore()) {
            event.getPlayer().sendMessage(ChatColor.GREEN + "You wake up and find that you have some gold coins, some food and an empty book on your person.");
            event.getPlayer().getInventory().addItem(getCurrency(50));
            event.getPlayer().getInventory().addItem(new ItemStack(Material.BREAD, 10));
            event.getPlayer().getInventory().addItem(new ItemStack(Material.WRITABLE_BOOK));
        }

        if (!hasCoinpurse(event.getPlayer().getName())) {
            // assign coinpurse
            Coinpurse purse = new Coinpurse();
            purse.setPlayerName(event.getPlayer().getName());
            coinpurses.add(purse);
            event.getPlayer().sendMessage(ChatColor.GREEN + "You lay a hand at your side to reassure yourself your coinpurse is still there. (commands: /balance, /deposit, /withdraw)");
        }
    }

    public boolean hasCoinpurse(String playerName) {
        for (Coinpurse purse : coinpurses) {
            if (purse.getPlayerName().equalsIgnoreCase(playerName)) {
                return true;
            }
        }
        return false;
    }

    public Coinpurse getPlayersCoinPurse(String playerName) {
        for (Coinpurse purse : coinpurses) {
            if (purse.getPlayerName().equalsIgnoreCase(playerName)) {
                return purse;
            }
        }
        return null;
    }

    @EventHandler()
    public void onDeath(PlayerDeathEvent event) {
        Coinpurse purse = getPlayersCoinPurse(event.getEntity().getName());
        int initialCoins = purse.getCoins();

        int amount = 0;

        // check if purse has at least 10 coins
        if (purse.containsAtLeast(10)) {
            amount = (int) (purse.getCoins() * 0.10);
        }
        else {
            amount = 1;
        }
        // remove coins from purse
        purse.removeCoins(amount);

        // drop coins on ground
        event.getDrops().add(getCurrency(amount));

        // inform player
        if (initialCoins != 0) {
            event.getEntity().sendMessage(ChatColor.RED + "Your coinpurse feels lighter than it was.");
        }
    }
}
