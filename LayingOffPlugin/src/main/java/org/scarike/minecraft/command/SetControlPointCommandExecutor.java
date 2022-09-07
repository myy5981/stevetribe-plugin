package org.scarike.minecraft.command;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.scarike.minecraft.LayingOffPlugin;
import org.scarike.minecraft.exception.TransactionException;
import org.scarike.minecraft.service.Reactor;

public class SetControlPointCommandExecutor implements CommandExecutor {

    private final LayingOffPlugin plugin;
    private final Reactor reactor;

    public SetControlPointCommandExecutor(LayingOffPlugin plugin) {
        this.plugin = plugin;
        reactor=plugin.getReactor();
    }

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!(commandSender instanceof Player)){
            commandSender.sendMessage("Only Players can use this command!");
            return true;
        }
        Player player= (Player) commandSender;
        if(!player.isOp()){
            player.sendMessage("Only Op can use this command!");
            return true;
        }
        if (strings.length!=2) {
            return false;
        }
        try {
            double x=Double.parseDouble(strings[0]);
            double y = Double.parseDouble(strings[1]);
            Location location = player.getLocation();
            double px=location.getX();
            double py=location.getZ();
            int i = reactor.setControllerPoint(x, y, px, py, player.getUniqueId());
            if(i>0){
                player.sendMessage("取样点-"+i+":("+x+","+y+") to ("+px+","+py+");");
                return true;
            }
            player.sendMessage("取样失败：无需更多的取样点。");
            return true;
        } catch (NumberFormatException e) {
            return false;
        } catch (TransactionException e) {
            player.sendMessage(e.getMessage());
            return true;
        }
    }
}
