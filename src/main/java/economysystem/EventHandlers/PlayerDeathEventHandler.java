package economysystem.EventHandlers;

import economysystem.Objects.Coinpurse;
import economysystem.Main;
import org.bukkit.ChatColor;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathEventHandler {

    Main main = null;

    public PlayerDeathEventHandler(Main plugin) {
        main = plugin;
    }

    public void handle(PlayerDeathEvent event) {
        Coinpurse purse = main.utilities.getPlayersCoinPurse(event.getEntity().getUniqueId());
        int initialCoins = purse.getCoins();

        int amount = 0;

        // check if purse has at least 10 coins
        if (purse.containsAtLeast(10)) {
            amount = (int) (purse.getCoins() * 0.10);
        }
        else {
            amount = 1;
        }
        // remove coins from purse
        purse.removeCoins(amount);

        if (initialCoins != 0) {
            // drop coins on ground
            event.getDrops().add(main.utilities.getCurrency(amount));

            // inform player
            event.getEntity().sendMessage(ChatColor.RED + main.getConfig().getString("deathMessage"));
        }
    }

}
