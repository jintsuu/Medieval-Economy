package economysystem.Commands;

import economysystem.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static economysystem.Main.sendHelpMessage;

public class EconCommand {

    Main main = null;

    public EconCommand(Main plugin) {
        main = plugin;
    }

    public void run(CommandSender sender, String[] args) {
        if (args.length > 0) {

            if (args[0].equalsIgnoreCase("help")) {
                if (sender instanceof Player) {
                    sendHelpMessage((Player) sender);
                }
            }

            if (args[0].equalsIgnoreCase("createcurrency")) {
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    if (player.hasPermission("medievaleconomy.createcurrency")) {

                        if (args.length == 1) {
                            main.addCurrencyToInventory(player, 1);
                        }
                        else {
                            main.addCurrencyToInventory(player, Integer.parseInt(args[1]));
                        }

                    }
                    else {
                        player.sendMessage(ChatColor.RED + "You need the following permission to use this command: medievaleconomy.createcurrency");
                    }
                }
                else {
                    System.out.println("You can't run this command from the console!");
                }
            }
        }
        else {
            if (sender instanceof Player) {
                sendHelpMessage((Player) sender);
            }

        }
    }

}
