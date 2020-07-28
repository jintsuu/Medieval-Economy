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
                        player.sendMessage(ChatColor.RED + main.getConfig().getString("createCurrencyNoPermission"));
                    }
                }
                else {
                    System.out.println(main.getConfig().getString("createCurrencyNoRunFromConsole"));
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
                    player.sendMessage(ChatColor.GREEN + main.getConfig().getString("configReloadedText"));
                }
                else {
                    player.sendMessage(ChatColor.RED + main.getConfig().getString("reloadNoPermission"));
                }
            }
            else {
                main.reloadConfig();
                System.out.println(main.getConfig().getString("configReloadedText"));
            }

        }
    }

}
