package economysystem.Commands;

import economysystem.Objects.Coinpurse;
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

                    player.sendMessage(ChatColor.GREEN + main.getConfig().getString("balanceTextStart") + num + main.getConfig().getString("balanceTextEnd"));

                }

            }
            else {
                player.sendMessage(ChatColor.RED + main.getConfig().getString("balanceNoPermission"));
            }

        }
    }

}
