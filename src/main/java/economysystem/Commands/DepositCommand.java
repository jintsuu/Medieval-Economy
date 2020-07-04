package economysystem.Commands;

import economysystem.Coinpurse;
import economysystem.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

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

                    // get args[0]
                    int amount = Integer.parseInt(args[0]);

                    // enough coins check
                    if (player.getInventory().containsAtLeast(main.getCurrency(1), amount)) {

                        // add coins to coinpurse
                        Coinpurse purse = main.getPlayersCoinPurse(player.getName());
                        purse.addCoins(amount);

                        // delete coins from inventory
                        player.getInventory().remove(main.getCurrency(amount));

                        player.sendMessage(ChatColor.GREEN + "You open your coinpurse and deposit " + amount + " coins.");
                    }

                }

            }

        }

    }
}
