package org.scarike.minecraft;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.scarike.minecraft.command.LayingOffCommandExecutor;
import org.scarike.minecraft.command.SetControlPointCommandExecutor;
import org.scarike.minecraft.command.TransactionCommandExecutor;
import org.scarike.minecraft.entity.ReplacePos;
import org.scarike.minecraft.service.Reactor;

import java.util.Set;
import java.util.logging.Logger;

public class LayingOffPlugin extends JavaPlugin {

    public Reactor getReactor() {
        return reactor;
    }

    private final Reactor reactor=new Reactor();

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @Override
    public void onEnable() {
        super.onEnable();
        Logger log = getLogger();

        saveDefaultConfig();
        FileConfiguration config = getConfig();
        String string = config.getString("default-block");
        if(string!=null){
            try {
                ReplacePos.DEFAULT_BLOCK = Material.valueOf(string);
                log.info("layer: default="+ReplacePos.DEFAULT_BLOCK);
            } catch (IllegalArgumentException ignore) {
            }
        }
        Set<String> keys = config.getKeys(true);
        for (String key : keys) {
            if(key!=null&&key.startsWith("layer.")){
                String layer = key.substring(6);
                string=config.getString(key);
                if(string!=null){
                    try {
                        Material material = Material.valueOf(string);
                        ReplacePos.setMap(layer,material);
                        log.info("layer: "+layer+"="+material);
                    } catch (IllegalArgumentException ignore) {
                        log.warning("no material called "+string+"for layer: "+layer);
                    }
                }
            }
        }

        TransactionCommandExecutor transactionCommandExecutor = new TransactionCommandExecutor(this);
        LayingOffCommandExecutor layingOffCommandExecutor = new LayingOffCommandExecutor(this);
        SetControlPointCommandExecutor setControlPointCommandExecutor = new SetControlPointCommandExecutor(this);
        this.getCommand("/begin").setExecutor(transactionCommandExecutor);
        this.getCommand("/commit").setExecutor(transactionCommandExecutor);
        this.getCommand("/rollback").setExecutor(transactionCommandExecutor);
        this.getCommand("/set-control-point").setExecutor(setControlPointCommandExecutor);
        this.getCommand("/laying-off").setExecutor(layingOffCommandExecutor);
    }
}
