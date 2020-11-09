package economysystem.Subsystems;

import economysystem.Commands.BalanceCommand;
import economysystem.Commands.DepositCommand;
import economysystem.Commands.EconCommand;
import economysystem.Commands.WithdrawCommand;
import economysystem.MedievalEconomy;
import org.bukkit.command.CommandSender;

public class CommandSubsystem {

    MedievalEconomy medievalEconomy = null;

    public CommandSubsystem(MedievalEconomy plugin) {
        medievalEconomy = plugin;
    }

    public boolean interpretCommand(CommandSender sender, String label, String[] args) {
        if (label.equalsIgnoreCase("econ")) {
            EconCommand command = new EconCommand(medievalEconomy);
            command.run(sender, args);
            return true;
        }
        if (label.equalsIgnoreCase("balance")) {
            BalanceCommand command = new BalanceCommand(medievalEconomy);
            command.run(sender);
            return true;
        }
        if (label.equalsIgnoreCase("deposit")) {
            DepositCommand command = new DepositCommand(medievalEconomy);
            command.depositCoins(sender, args);
            return true;
        }
        if (label.equalsIgnoreCase("withdraw")) {
            WithdrawCommand command = new WithdrawCommand(medievalEconomy);
            command.withdrawCoins(sender, args);
            return true;
        }
        return false;
    }

}
