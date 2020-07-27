package economysystem.Commands;

import economysystem.Coinpurse;
import economysystem.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BalanceCommand {

    Main main = null;

    public BalanceCommand(Main plugin) {
        main = plugin;
    }

    public void run(CommandSender sender) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            // permission check
            if (player.hasPermission("medievaleconomy.balance") || player.hasPermission("medievaleconomy.default")) {

                Coinpurse purse = main.utilities.getPlayersCoinPurse(player.getName());

                if (purse != null) {

                    int num = purse.getCoins();

                    player.sendMessage(ChatColor.GREEN + "You have " + num + " coins in your coinpurse.");

                }

            }
            else {
                player.sendMessage(ChatColor.RED + "Sorry! In order to run this command, you need the following permission: " + "'medievaleconomy.default'");
            }

        }
    }

}
