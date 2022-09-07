package org.scarike.minecraft.service;

import org.bukkit.Material;
import org.junit.Test;
import org.scarike.minecraft.exception.LayingOffException;
import org.scarike.minecraft.entity.ReplacePos;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GeoJsonReaderTest {
//[ 402050.0, 3405000.0 ], [ 403050.0, 3405000.0 ], [ 403050.0, 3407200.0 ], [ 402050.0, 3407200.0 ]

    void draw(List<ReplacePos> points){
        JFrame frame=new JFrame();
        frame.setSize(1500,800);
        frame.setVisible(true);
        Graphics g = frame.getGraphics();
        for (ReplacePos point : points) {
            g.fillRect(point.getZ()+50,point.getX()+50,1,1);
        }
    }

    @Test
    public void analysis() throws LayingOffException {
        Material material = Material.valueOf("WHITE_WOOL");

        System.out.println(material);
//        GeoJsonReader geoJsonReader = new GeoJsonReader();
//        geoJsonReader.addCoordinatePair(402050.0, 3405000.0,10,10);
//        geoJsonReader.addCoordinatePair(403050.0, 3405000.0,710,10);
//        geoJsonReader.addCoordinatePair(403050.0, 3407200.0 ,710,1410);
//        List<ReplacePos> analysis = geoJsonReader.analysis("/home/scarike/桌面/1.geojson");
//        draw(analysis);
//        System.out.println(analysis.hashCode());
    }
}