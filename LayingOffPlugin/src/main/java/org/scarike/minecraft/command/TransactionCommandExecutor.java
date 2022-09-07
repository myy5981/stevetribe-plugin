package org.scarike.minecraft.command;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.scarike.minecraft.LayingOffPlugin;
import org.scarike.minecraft.exception.TransactionException;
import org.scarike.minecraft.entity.ReplacePos;
import org.scarike.minecraft.service.Reactor;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class TransactionCommandExecutor implements CommandExecutor {

    private final LayingOffPlugin plugin;
    private final Reactor reactor;

    public TransactionCommandExecutor(LayingOffPlugin plugin) {
        this.plugin = plugin;
        reactor=plugin.getReactor();
    }

    @Override
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
        try {
            if(command.getName().equalsIgnoreCase("/begin")){
                reactor.begin(player.getUniqueId());
                player.sendMessage("开启事务");
                return true;
            }else if(command.getName().equalsIgnoreCase("/rollback")) {
                Reactor.ReplacePositions positions = reactor.rollback(player.getUniqueId());
                Material material;int i=0;
                if(positions!=null) {
                    World world = positions.getWorld();
                    int y = positions.getY();
                    LinkedList<ReplacePos> rollback = positions.getPos();
                    if (rollback != null) {
                        Iterator<ReplacePos> it = rollback.descendingIterator();
                        while (it.hasNext()) {
                            ReplacePos pos = it.next();Block block = world.getBlockAt(pos.getX(), y, pos.getZ());
                            if ((material = pos.getOrigin()) != null) {
                                block.setType(material);
                                i++;
                            }
                        }
                    }
                }
                player.sendMessage("done! total:"+i);
                return true;
            }else if(command.getName().equalsIgnoreCase("/commit")){
                reactor.commit(player.getUniqueId());
                player.sendMessage("提交事务");
                return true;
            }
        }catch (TransactionException ex){
            player.sendMessage(ex.getMessage());
            return true;
        }
        return false;
    }
}
