package org.scarike.minecraft.service;

import org.bukkit.Material;
import org.geotools.feature.FeatureCollection;
import org.geotools.feature.FeatureIterator;
import org.geotools.geojson.feature.FeatureJSON;
import org.geotools.geojson.geom.GeometryJSON;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.util.AffineTransformation;
import org.locationtech.jts.geom.util.AffineTransformationBuilder;
import org.opengis.feature.Feature;
import org.opengis.feature.type.FeatureType;
import org.opengis.feature.type.Name;
import org.scarike.minecraft.exception.LayingOffException;
import org.scarike.minecraft.entity.ReplacePos;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class GeoJsonReader {
    private final double[][] control=new double[3][4];
    private int index = 0;
    private AffineTransformation trans=null;

    public void reset(){
        index=0;
        trans=null;
    }

    public int addCoordinatePair(double x,double y,double _x,double _y){
        if(index <3){
            control[index][0]=x;
            control[index][1]=y;
            control[index][2]=_x;
            control[index][3]=_y;
            index++;
            return index;
        }
        return -1;
    }

    private FeatureCollection load(String uri) throws IOException {
        FeatureJSON featureJSON = new FeatureJSON(new GeometryJSON());
        return featureJSON.readFeatureCollection(new FileInputStream(uri));
    }

    public static void drawLine(int x0,int y0,int x1,int y1,Material material,List<ReplacePos> positions){
        int x = x0;
        int y = y0;

        int w = x1 - x0;
        int h = y1 - y0;

        int dx1 = w < 0 ? -1: (w > 0 ? 1 : 0);
        int dy1 = h < 0 ? -1: (h > 0 ? 1 : 0);

        int dx2 = w < 0 ? -1: (w > 0 ? 1 : 0);
        int dy2 = 0;

        int fastStep = Math.abs(w);
        int slowStep = Math.abs(h);
        if (fastStep <=slowStep) {
            fastStep= Math.abs(h);
            slowStep= Math.abs(w);

            dx2= 0;
            dy2= h < 0 ? -1 : (h > 0 ? 1 : 0);
        }
        int numerator = fastStep>> 1;

        numerator+= slowStep;
        if (numerator >=fastStep) {
            numerator-= fastStep;
            x+= dx1;
            y+= dy1;
        }else {
            x+= dx2;
            y+= dy2;
        }
        for (int i = 1; i <fastStep; i++) {
            positions.add(new ReplacePos().setX(x).setZ(y).setMaterial(material));
            numerator+= slowStep;
            if (numerator >=fastStep) {
                numerator-= fastStep;
                x+= dx1;
                y+= dy1;
            }else {
                x+= dx2;
                y+= dy2;
            }
        }
    }

    public LinkedList<ReplacePos> analysis(String uri) throws LayingOffException {

        if(trans==null){
            if(index!=3){
                throw new LayingOffException("控制点必须三个");
            }
            trans = new AffineTransformationBuilder(
                    new Coordinate(control[0][0],control[0][1]),
                    new Coordinate(control[1][0],control[1][1]),
                    new Coordinate(control[2][0],control[2][1]),
                    new Coordinate(control[0][2],control[0][3]),
                    new Coordinate(control[1][2],control[1][3]),
                    new Coordinate(control[2][2],control[2][3])
            ).getTransformation();
        }

        try {
            LinkedList<ReplacePos> positions=new LinkedList<>();
            FeatureCollection load = load(uri);
            FeatureType schema = load.getSchema();
            Name name = schema.getGeometryDescriptor().getName();

            if(name==null){
                throw new LayingOffException("无法找到数据中的地理信息数据");
            }
            FeatureIterator it = load.features();
            while (it.hasNext()){
                Feature feature = it.next();
                Geometry geometry = (Geometry) feature.getProperty(name).getValue();
                geometry.apply(trans);
                Coordinate[] coordinates = geometry.getCoordinates();
                if(coordinates.length<=0){
                    continue;
                }
                Material material = ReplacePos.loadMaterial(feature);
                Coordinate coordinate=coordinates[0];
                int px=(int) Math.round(coordinate.x);
                int py=(int) Math.round(coordinate.y);
                positions.add(new ReplacePos().setX(px).setZ(py).setMaterial(material));
                for (int i = 1; i < coordinates.length; i++) {
                    coordinate=coordinates[i];
                    int x=(int) Math.round(coordinate.x);
                    int y=(int) Math.round(coordinate.y);
                    // 连线
                    drawLine(x,y,px,py,material,positions);
                    positions.add(new ReplacePos().setX(x).setZ(y).setMaterial(material));
                    px=x;py=y;
                }
            }
            return positions;
        } catch (IOException|ClassCastException|NullPointerException e) {
            throw new LayingOffException("读取数据源错误",e);
        }
    }
}
