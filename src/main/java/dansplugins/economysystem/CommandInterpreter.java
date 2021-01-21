package dansplugins.economysystem;

import dansplugins.economysystem.Commands.BalanceCommand;
import dansplugins.economysystem.Commands.DepositCommand;
import dansplugins.economysystem.Commands.EconCommand;
import dansplugins.economysystem.Commands.WithdrawCommand;
import org.bukkit.command.CommandSender;

public class CommandInterpreter {

    MedievalEconomy medievalEconomy = null;

    public CommandInterpreter(MedievalEconomy plugin) {
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
