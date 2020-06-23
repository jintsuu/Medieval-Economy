package economysystem;

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

    @Override
    public void onEnable() {
        System.out.println("Medieval Economy is enabling...");

        this.getServer().getPluginManager().registerEvents(this, this);

        System.out.println("Medieval Economy is enabled!");
    }

    @Override
    public void onDisable() {
        System.out.println("Medieval Economy is disabling...");



        System.out.println("Medieval Economy is disabled!");
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (label.equalsIgnoreCase("econ")) {
            if (args.length > 0) {

                if (args[0].equalsIgnoreCase("help")) {
                    if (sender instanceof Player) {
                        sendHelpMessage((Player) sender);
                        return true;
                    }
                }

                if (args[0].equalsIgnoreCase("createcurrency")) {
                    if (sender instanceof Player) {
                        Player player = (Player) sender;
                        if (player.hasPermission("medievaleconomy.createcurrency")) {

                            if (args.length == 1) {
                                addCurrencyToInventory(player, 1);
                                return true;
                            }
                            else {
                                addCurrencyToInventory(player, Integer.parseInt(args[1]));
                                return true;
                            }

                        }
                        else {
                            player.sendMessage(ChatColor.RED + "You need the following permission to use this command: medievaleconomy.createcurrency");
                            return false;
                        }
                    }
                    else {
                        System.out.println("You can't run this command from the console!");
                        return false;
                    }
                }
            }
            else {
                if (sender instanceof Player) {
                    sendHelpMessage((Player) sender);
                    return true;
                }

            }

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

    public void removeCurrencyToInventory(Player player, int amount) {
        player.getInventory().removeItem(getCurrency(amount));
    }

    public ItemStack getCurrency(int amount) {
        ItemStack currencyItem = new ItemStack(Material.GOLD_INGOT, amount);
        ItemMeta meta = currencyItem.getItemMeta();

        meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "Florin");
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
            event.getPlayer().sendMessage(ChatColor.GREEN + "You wake up and find that you have some coins and an empty book on your person.");
            event.getPlayer().getInventory().addItem(getCurrency(50));
            event.getPlayer().getInventory().addItem(new ItemStack(Material.WRITABLE_BOOK));
        }
    }
}
