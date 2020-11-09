package economysystem.Commands;

import economysystem.Objects.Coinpurse;
import economysystem.MedievalEconomy;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class WithdrawCommand {

    MedievalEconomy medievalEconomy = null;

    public WithdrawCommand(MedievalEconomy plugin) {
        medievalEconomy = plugin;
    }

    public void withdrawCoins(CommandSender sender, String[] args) {

        if (sender instanceof Player) {

            Player player = (Player) sender;

            // permission check
            if (player.hasPermission("medievaleconomy.withdraw") || player.hasPermission("medievaleconomy.default")) {

                // args check
                if (args.length > 0) {

                    int amount = 0;

                    // get args[0]
                    try {
                        amount = Integer.parseInt(args[0]);
                    } catch(Exception e) {
                        player.sendMessage(ChatColor.RED + medievalEconomy.getConfig().getString("withdrawUsageText"));
                        return;
                    }

                    if (amount < 0) {
                        player.sendMessage(ChatColor.RED + medievalEconomy.getConfig().getString("withdrawPositiveText"));
                        return;
                    }

                    Coinpurse purse = medievalEconomy.utilities.getPlayersCoinPurse(player.getUniqueId());

                    // enough coins check
                    if (purse.containsAtLeast(amount)) {

                        // System.out.println("DEBUG: Free inventory slots: " + (36 - player.getInventory().getStorageContents().length));

                        // too many coins check
                        if (amount/64 > getEmptySpaces(player)) {
                            player.sendMessage(ChatColor.RED + "" + medievalEconomy.getConfig().getString("withdrawNotEnoughSpace"));
                            return;
                        }

                        // remove coins to coinpurse
                        purse.removeCoins(amount);

                        // add coins from inventory
                        player.getInventory().addItem(medievalEconomy.utilities.getCurrency(amount));

                        player.sendMessage(ChatColor.GREEN + medievalEconomy.getConfig().getString("withdrawTextStart") + amount + medievalEconomy.getConfig().getString("withdrawTextEnd"));
                    }
                    else {
                        player.sendMessage(ChatColor.RED + medievalEconomy.getConfig().getString("withdrawNotEnoughCoins"));
                    }

                }
                else {
                    player.sendMessage(ChatColor.RED + medievalEconomy.getConfig().getString("withdrawUsageText"));
                }

            }
            else {
                player.sendMessage(ChatColor.RED + medievalEconomy.getConfig().getString("withdrawNoPermission"));
            }

        }

    }

    private int getEmptySpaces(Player player) {
        int emptySpaces = 0;
        for (ItemStack i : player.getInventory()) {
            if (i == null) {
                emptySpaces++;
            }
        }
        return emptySpaces;
    }
}
