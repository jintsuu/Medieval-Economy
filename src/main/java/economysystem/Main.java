package economysystem;

import economysystem.Commands.BalanceCommand;
import economysystem.Commands.DepositCommand;
import economysystem.Commands.EconCommand;
import economysystem.Commands.WithdrawCommand;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class Main extends JavaPlugin implements Listener {

    ArrayList<Coinpurse> coinpurses = new ArrayList<>();

    @Override
    public void onEnable() {
        System.out.println("Medieval Economy is enabling...");

        this.getServer().getPluginManager().registerEvents(this, this);

        load();

        System.out.println("Medieval Economy is enabled!");
    }

    @Override
    public void onDisable() {
        System.out.println("Medieval Economy is disabling...");

        save();

        System.out.println("Medieval Economy is disabled!");
    }

    public void save() {
        saveCoinpurseFilenames();
        saveCoinpurses();
    }

    public void load() {
        loadCoinpurses();
    }

    public void saveCoinpurseFilenames() {

    }

    public void saveCoinpurses() {
        for (Coinpurse purse : coinpurses) {
            purse.save();
        }
    }

    public void loadCoinpurses() {

    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (label.equalsIgnoreCase("econ")) {
            EconCommand command = new EconCommand(this);
            command.run(sender, args);
        }
        if (label.equalsIgnoreCase("balance")) {
            BalanceCommand command = new BalanceCommand(this);
            command.run(sender);
        }
        if (label.equalsIgnoreCase("deposit")) {
            DepositCommand command = new DepositCommand(this);
            command.depositCoins(sender, args);
        }
        if (label.equalsIgnoreCase("deposit")) {
            WithdrawCommand command = new WithdrawCommand(this);
            command.withdrawCoins(sender, args);
        }
        return false;
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
            event.getPlayer().sendMessage(ChatColor.GREEN + "You lay a hand at your side to reassure yourself your coinpurse is still there. (hint: use /balance)");
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
}
