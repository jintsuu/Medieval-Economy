package economysystem;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {



    }

    @Override
    public void onDisable() {



    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (label.equalsIgnoreCase("createflorin")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (player.hasPermission("medievaleconomy.getflorin")) {

                    // if player's inventory has space
                    if (!(player.getInventory().firstEmpty() == -1)) {
                        addCurrencyToInventory(player);
                        player.sendMessage(ChatColor.GREEN + "Currency received.");
                        return true;
                    }
                    else { // player's inventory is full
                        player.sendMessage(ChatColor.RED + "Inventory full.");
                        return false;
                    }
                }
            }
        }

        return false;
    }

    public void addCurrencyToInventory(Player player) {
        player.getInventory().addItem(getCurrency());
    }

    public void removeCurrencyToInventory(Player player) {
        player.getInventory().removeItem(getCurrency());
    }

    public ItemStack getCurrency() {
        ItemStack currencyItem = new ItemStack(Material.GOLD_INGOT);
        ItemMeta meta = currencyItem.getItemMeta();

        meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "Florin");
        List<String> lore = new ArrayList<String>();
        lore.add("");
        lore.add(ChatColor.GOLD + "" + ChatColor.ITALIC + "The currency of the Continent.");

        meta.setLore(lore);
        currencyItem.setItemMeta(meta);

        return currencyItem;
    }
}
