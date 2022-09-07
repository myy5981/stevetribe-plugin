package org.scarike.minecraft.command;

import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.scarike.minecraft.exception.LayingOffException;
import org.scarike.minecraft.LayingOffPlugin;
import org.scarike.minecraft.exception.TransactionException;
import org.scarike.minecraft.entity.ReplacePos;
import org.scarike.minecraft.service.Reactor;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class LayingOffCommandExecutor implements CommandExecutor {

    private final LayingOffPlugin plugin;
    private final Reactor reactor;

    public LayingOffCommandExecutor(LayingOffPlugin plugin) {
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
        if (strings.length!=1) {
            return false;
        }
        try {
            player.sendMessage("computing...");
            Reactor.ReplacePositions positions = reactor.draw(strings[0], player.getUniqueId());
            player.sendMessage("done!");
            player.sendMessage("placing blocks...");
            int y = player.getLocation().getBlockY();
            World world=player.getWorld();
            positions.setWorld(world).setY(y);
            LinkedList<ReplacePos> draw = positions.getPos();
            for (ReplacePos pos : draw) {
                Block block = player.getWorld().getBlockAt(pos.getX(), y, pos.getZ());
                pos.setOrigin(block.getType());
                block.setType(pos.getMaterial());
            }
            player.sendMessage("done! total:"+draw.size());
            return true;
        } catch (LayingOffException | TransactionException e) {
            player.sendMessage(e.getMessage());
            return true;
        }
    }
}
