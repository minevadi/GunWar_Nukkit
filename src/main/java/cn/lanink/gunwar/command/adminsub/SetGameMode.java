package cn.lanink.gunwar.command.adminsub;

import cn.lanink.gunwar.command.base.BaseSubCommand;
import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.utils.Config;

/**
 * @author lt_name
 */
public class SetGameMode extends BaseSubCommand {

    public SetGameMode(String name) {
        super(name);
    }

    @Override
    public boolean canUser(CommandSender sender) {
        return sender.isPlayer() && sender.isOp();
    }

    @Override
    public String[] getAliases() {
        return new String[0];
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if (args.length == 2) {
            if (args[1].matches("[0-9]*")) {
                Player player = (Player) sender;
                int mode = Integer.parseInt(args[1]);
                Config config = this.gunWar.getRoomConfig(player.getLevel());
                config.set("gameMode", mode);
                config.save();
                if (mode == 1) {
                    sender.sendMessage(this.language.adminSetGameMode
                            .replace("%roomMode%", this.language.captureTheFlag));
                }else {
                    sender.sendMessage(this.language.adminSetGameMode
                            .replace("%roomMode%", this.language.classic));
                }
            }else {
                sender.sendMessage(this.language.adminNotNumber);
            }
        }else {
            sender.sendMessage(this.language.cmdHelp.replace("%cmdName%", this.getName()));
        }
        return true;
    }

    @Override
    public CommandParameter[] getParameters() {
        return new CommandParameter[] { new CommandParameter("mode", CommandParamType.INT, false) };
    }

}
