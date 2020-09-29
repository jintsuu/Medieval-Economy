package economysystem.Commands;

import economysystem.Objects.Coinpurse;
import economysystem.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WithdrawCommand {

    Main main = null;

    public WithdrawCommand(Main plugin) {
        main = plugin;
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
                        player.sendMessage(ChatColor.RED + main.getConfig().getString("withdrawUsageText"));
                        return;
                    }

                    if (amount < 0) {
                        player.sendMessage(ChatColor.RED + main.getConfig().getString("withdrawPositiveText"));
                        return;
                    }

                    Coinpurse purse = main.utilities.getPlayersCoinPurse(player.getUniqueId());

                    // enough coins check
                    if (purse.containsAtLeast(amount)) {

                        // System.out.println("DEBUG: Free inventory slots: " + (36 - player.getInventory().getStorageContents().length));

                        // too many coins check
                        if (amount > (36 - player.getInventory().getStorageContents().length)) {
                            player.sendMessage(ChatColor.RED + "" + main.getConfig().getString("withdrawNotEnoughSpace"));
                            return;
                        }

                        // remove coins to coinpurse
                        purse.removeCoins(amount);

                        // add coins from inventory
                        player.getInventory().addItem(main.utilities.getCurrency(amount));

                        player.sendMessage(ChatColor.GREEN + main.getConfig().getString("withdrawTextStart") + amount + main.getConfig().getString("withdrawTextEnd"));
                    }
                    else {
                        player.sendMessage(ChatColor.RED + main.getConfig().getString("withdrawNotEnoughCoins"));
                    }

                }
                else {
                    player.sendMessage(ChatColor.RED + main.getConfig().getString("withdrawUsageText"));
                }

            }
            else {
                player.sendMessage(ChatColor.RED + main.getConfig().getString("withdrawNoPermission"));
            }

        }

    }
}
