package economysystem.Commands;

import economysystem.Objects.Coinpurse;
import economysystem.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DepositCommand {

    Main main = null;

    public DepositCommand(Main plugin) {
        main = plugin;
    }

    public void depositCoins(CommandSender sender, String[] args) {

        if (sender instanceof Player) {

            Player player = (Player) sender;

            // permission check
            if (player.hasPermission("medievaleconomy.deposit") || player.hasPermission("medievaleconomy.default")) {

                // args check
                if (args.length > 0) {

                    int amount = 0;

                    // get args[0]
                    try {
                        amount = Integer.parseInt(args[0]);
                    } catch(Exception e) {
                        player.sendMessage(ChatColor.RED + main.getConfig().getString("depositUsageText"));
                        return;
                    }

                    if (amount < 0) {
                        player.sendMessage(ChatColor.RED + main.getConfig().getString("depositPositiveText"));
                        return;
                    }

                    // enough coins check
                    if (player.getInventory().containsAtLeast(main.utilities.getCurrency(1), amount)) {

                        // add coins to coinpurse
                        Coinpurse purse = main.utilities.getPlayersCoinPurse(player.getName());
                        purse.addCoins(amount);

                        // delete coins from inventory
                        player.getInventory().removeItem(main.utilities.getCurrency(amount));

                        player.sendMessage(ChatColor.GREEN + main.getConfig().getString("depositTextStart") + amount + main.getConfig().getString("depositTextEnd"));
                    }
                    else {
                        player.sendMessage(ChatColor.RED + main.getConfig().getString("depositNotEnoughCoins"));
                    }

                }
                else {
                    player.sendMessage(ChatColor.RED + main.getConfig().getString("depositUsageText"));
                }

            }
            else {
                player.sendMessage(ChatColor.RED + main.getConfig().getString("depositNoPermission"));
            }

        }

    }
}
