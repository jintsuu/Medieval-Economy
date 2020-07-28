package economysystem.Commands;

import economysystem.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EconCommand {

    Main main = null;

    public EconCommand(Main plugin) {
        main = plugin;
    }

    public void run(CommandSender sender, String[] args) {
        if (args.length > 0) {

            if (args[0].equalsIgnoreCase("help")) {
                if (sender instanceof Player) {
                    main.utilities.sendHelpMessage((Player) sender);
                }
            }

            if (args[0].equalsIgnoreCase("createcurrency")) {
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    if (player.hasPermission("medievaleconomy.createcurrency") || player.hasPermission("medievaleconomy.admin")) {

                        if (args.length == 1) {
                            main.utilities.addCurrencyToInventory(player, 1);
                        }
                        else {
                            main.utilities.addCurrencyToInventory(player, Integer.parseInt(args[1]));
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
                main.utilities.sendHelpMessage((Player) sender);
            }
        }

        if (args[0].equalsIgnoreCase("reload")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (player.hasPermission("medievaleconomy.reload") || player.hasPermission("medievaleconomy.admin")) {
                    main.reloadConfig();
                    player.sendMessage(ChatColor.GREEN + "Config reloaded!");
                }
                else {
                    player.sendMessage(ChatColor.RED + "You need the following permission to use this command: 'medievaleconomy.reload'");
                }
            }
            else {
                main.reloadConfig();
                System.out.println("Config reloaded!");
            }

        }
    }

}
