package cn.lanink.gunwar.command.usersub;

import cn.lanink.gunwar.command.base.BaseSubCommand;
import cn.lanink.gunwar.room.Room;
import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;

public class JoinCommand extends BaseSubCommand {

    public JoinCommand(String name) {
        super(name);
    }

    @Override
    public boolean canUser(CommandSender sender) {
        return sender.isPlayer();
    }

    @Override
    public String[] getAliases() {
        return new String[] { "加入" };
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        Player player = (Player) sender;
        if (this.gunWar.getRooms().size() > 0) {
            if (player.riding != null) {
                sender.sendMessage(this.language.joinRoomIsRiding);
                return true;
            }
            for (Room room : this.gunWar.getRooms().values()) {
                if (room.isPlaying(player)) {
                    sender.sendMessage(this.language.joinRoomIsInRoom);
                    return true;
                }
            }
            if (args.length < 2) {
                for (Room room : this.gunWar.getRooms().values()) {
                    if ((room.getMode() == 0 || room.getMode() == 1) && room.getPlayers().size() < 10) {
                        room.joinRoom(player);
                        sender.sendMessage(this.language.joinRandomRoom);
                        return true;
                    }
                }
            }else {
                String[] s = args[1].split(":");
                if (s.length == 2 && s[0].toLowerCase().trim().equals("mode")) {
                    String modeName = s[1].toLowerCase().trim();
                    for (Room room : this.gunWar.getRooms().values()) {
                        if ((room.getMode() == 0 || room.getMode() == 1) && room.getPlayers().size() < 10) {
                            if (room.getGameMode().getName().equals(modeName)) {
                                room.joinRoom(player);
                                sender.sendMessage(this.language.joinRandomRoom);
                                return true;
                            }
                        }
                    }
                    sender.sendMessage(this.language.joinRoomIsNotFound);
                    return true;
                }else if (this.gunWar.getRooms().containsKey(args[1])) {
                    Room room = this.gunWar.getRooms().get(args[1]);
                    if (room.getMode() == 2 || room.getMode() == 3) {
                        sender.sendMessage(this.language.joinRoomIsPlaying);
                    } else if (room.getPlayers().size() >= 10) {
                        sender.sendMessage(this.language.joinRoomIsFull);
                    } else {
                        room.joinRoom(player);
                    }
                    return true;
                } else {
                    sender.sendMessage(this.language.joinRoomIsNotFound);
                    return true;
                }
            }
        }
        sender.sendMessage(this.language.joinRoomNotAvailable);
        return true;
    }

    @Override
    public CommandParameter[] getParameters() {
        return new CommandParameter[] { new CommandParameter("roomName", CommandParamType.TEXT, false) };
    }


}
