// ────────────────────────────────────────────────────────────────
//  MapService.java  (package: mypackage.service)
// ────────────────────────────────────────────────────────────────
package mypackage.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import mypackage.model.City;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.painter.Painter;
import org.jxmapviewer.viewer.*;
import org.locationtech.jts.geom.*;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Loads <code>trCities.json</code> from the runtime class‑path and returns a
 * <i>tile‑less</i> {@link JXMapViewer} that paints Turkey’s 81 provinces.
 */
public class MapService {

    /* ═════════════════════════ geojson → memory ═════════════════════════ */
    public static List<City> readCities() throws IOException {
        List<City> list = new ArrayList<>();
        InputStream in = MapService.class.getResourceAsStream("/trCities.json");
        if (in == null) throw new IOException("trCities.json missing");

        ObjectMapper m = new ObjectMapper();
        GeometryFactory gf = new GeometryFactory();
        JsonNode features = m.readTree(in).get("features");

        for (JsonNode f : features) {
            String name = f.get("properties").get("name").asText();
            String id   = f.get("properties").get("number").asText();
            JsonNode geom = f.get("geometry");
            String type = geom.get("type").asText();

            if ("Polygon".equals(type)) {
                list.add(makeCity(geom.get("coordinates").get(0), name, id, gf));
            } else { // MultiPolygon
                for (JsonNode poly : geom.get("coordinates"))
                    list.add(makeCity(poly.get(0), name, id, gf));
            }
        }
        return list;
    }

    private static City makeCity(JsonNode arr, String name, String id, GeometryFactory gf){
        List<Coordinate> coords = new ArrayList<>();
        for (JsonNode p : arr) coords.add(new Coordinate(p.get(0).asDouble(), p.get(1).asDouble()));
        return new City(name, gf.createPolygon(gf.createLinearRing(coords.toArray(Coordinate[]::new))), id);
    }

    /* ═══════════════════════ painter + viewer ═══════════════════════════ */
    private static class OutlinePainter implements Painter<JXMapViewer> {
        @Override public void paint(Graphics2D g, JXMapViewer map, int w, int h) {
            Graphics2D g2 = (Graphics2D) g;
            Rectangle vp = map.getViewportBounds();
            int zoom = map.getZoom();
            TileFactory tf = map.getTileFactory();

            g2.setColor(Color.WHITE);
            g2.fillRect(0,0,w,h);

            for(City c : City.allCities){
                Coordinate[] pts = c.getPolygon().getExteriorRing().getCoordinates();
                java.awt.Polygon poly = new java.awt.Polygon();
                for(Coordinate pt: pts){
                    Point2D px = tf.geoToPixel(new GeoPosition(pt.y, pt.x), zoom);
                    poly.addPoint((int)(px.getX()-vp.x),(int)(px.getY()-vp.y));
                }
                g2.setColor(new Color(20,90,130));
                g2.fillPolygon(poly);
                g2.setColor(Color.WHITE);
                g2.setStroke(new BasicStroke(1.5f));
                g2.drawPolygon(poly);
            }
        }
    }

    /**
     * Returns a fully initialised <b>static</b> JXMapViewer (no online tiles).
     */
    public static JXMapViewer getMapPanel() throws IOException {
        if (City.allCities == null) City.initializeCitys();

        JXMapViewer map = new JXMapViewer();

        TileFactoryInfo blank = new TileFactoryInfo(0,17,17,256,true,true,"","x","y","z"){
            @Override public String getTileUrl(int x,int y,int z){ return null; }
        };
        map.setTileFactory(new DefaultTileFactory(blank){
            @Override public Tile getTile(int x,int y,int z){
                return new Tile(x,y,z){
                    @Override public boolean isLoaded(){ return true; }
                    @Override public BufferedImage getImage(){ return null; } };
            }});

        map.setOverlayPainter(new OutlinePainter());
        map.setBackground(Color.WHITE);
        map.setPanEnabled(false);
        map.setZoom(11);
        map.setAddressLocation(new GeoPosition(39.29,35.16));
        return map;
    }
}
