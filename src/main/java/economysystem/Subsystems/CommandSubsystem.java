package economysystem.Subsystems;

import economysystem.Commands.BalanceCommand;
import economysystem.Commands.DepositCommand;
import economysystem.Commands.EconCommand;
import economysystem.Commands.WithdrawCommand;
import economysystem.Main;
import org.bukkit.command.CommandSender;

public class CommandSubsystem {

    Main main = null;

    public CommandSubsystem(Main plugin) {
        main = plugin;
    }

    public boolean interpretCommand(CommandSender sender, String label, String[] args) {
        if (label.equalsIgnoreCase("econ")) {
            EconCommand command = new EconCommand(main);
            command.run(sender, args);
            return true;
        }
        if (label.equalsIgnoreCase("balance")) {
            BalanceCommand command = new BalanceCommand(main);
            command.run(sender);
            return true;
        }
        if (label.equalsIgnoreCase("deposit")) {
            DepositCommand command = new DepositCommand(main);
            command.depositCoins(sender, args);
            return true;
        }
        if (label.equalsIgnoreCase("withdraw")) {
            WithdrawCommand command = new WithdrawCommand(main);
            command.withdrawCoins(sender, args);
            return true;
        }
        return false;
    }

}
