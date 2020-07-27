package economysystem.Commands;

import economysystem.Coinpurse;
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
                        player.sendMessage(ChatColor.RED + "Usage: /withdraw (whole number)");
                        return;
                    }

                    if (amount < 0) {
                        player.sendMessage(ChatColor.RED + "Number must be positive!");
                        return;
                    }

                    Coinpurse purse = main.utilities.getPlayersCoinPurse(player.getName());

                    // enough coins check
                    if (purse.containsAtLeast(amount)) {

                        // remove coins to coinpurse
                        purse.removeCoins(amount);

                        // add coins from inventory
                        player.getInventory().addItem(main.utilities.getCurrency(amount));

                        player.sendMessage(ChatColor.GREEN + "You open your coinpurse and take out " + amount + " coins.");
                    }
                    else {
                        player.sendMessage(ChatColor.RED + "You don't have that many coins in your coinpurse!");
                    }

                }
                else {
                    player.sendMessage(ChatColor.RED + "Usage: /deposit (number)");
                }

            }
            else {
                player.sendMessage(ChatColor.RED + "Sorry! In order to use this command, you need the permission " + "'medievaleconomy.withdraw'");
            }

        }

    }
}
