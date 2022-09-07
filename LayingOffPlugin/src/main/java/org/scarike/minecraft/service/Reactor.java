package org.scarike.minecraft.service;

import lombok.Data;
import lombok.experimental.Accessors;
import org.bukkit.World;
import org.scarike.minecraft.exception.LayingOffException;
import org.scarike.minecraft.exception.TransactionException;
import org.scarike.minecraft.entity.ReplacePos;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class Reactor {
    private UUID currentPlayerId=null;
    private final GeoJsonReader reader=new GeoJsonReader();
    private ReplacePositions pos=null;

    private void verify(UUID player) throws TransactionException {
        if(currentPlayerId==null){
            throw new TransactionException("未开启任何事务");
        }
        if(!currentPlayerId.equals(player)){
            throw new TransactionException("已有进行中的事务");
        }
    }

    public void begin(UUID player) throws TransactionException {
        if(currentPlayerId==null){
            currentPlayerId=player;
            return;
        }
        throw new TransactionException("已有进行中的事务");
    }

    public ReplacePositions rollback(UUID player) throws TransactionException {
        ReplacePositions replacePos = this.pos;
        commit(player);
        return replacePos;
    }

    public void commit(UUID player) throws TransactionException {
        verify(player);
        currentPlayerId=null;
        pos=null;
        reader.reset();
    }

    public int setControllerPoint(double x,double y,double _x,double _y,UUID player) throws TransactionException {
        verify(player);
        return reader.addCoordinatePair(x, y, _x, _y);
    }

    public ReplacePositions draw(String geojson,UUID player) throws LayingOffException, TransactionException {
        verify(player);
        LinkedList<ReplacePos> replacePos = reader.analysis("geo-data/" + geojson);
        pos=new ReplacePositions().setPos(replacePos);
        return pos;
    }

    @Data
    @Accessors(chain = true)
    public static class ReplacePositions{
        private World world;
        private int y;
        private LinkedList<ReplacePos> pos;
    }
}
