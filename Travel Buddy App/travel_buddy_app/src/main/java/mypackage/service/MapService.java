package mypackage.service;


import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.awt.BasicStroke;
import java.awt.Color;


import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.painter.Painter;
import org.jxmapviewer.viewer.*;
import org.locationtech.jts.geom.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


import mypackage.model.City;

public class MapService {

    
    public static List<City> readCities () throws IOException {

        GeometryFactory geofactory = new GeometryFactory();
        List<City> cities = new ArrayList<>();
        
            ObjectMapper mapper = new ObjectMapper();
            InputStream inputstrm = MapService.class.getResourceAsStream("/trCities.json");
            JsonNode root = mapper.readTree(inputstrm);

            JsonNode features = root.get("features");

            for (JsonNode feature : features) {
                String name = feature.get("properties").get("name").asText();
                JsonNode geometry = feature.get("geometry");
                String number = feature.get("properties").get("number").asText();
                String type = geometry.get("type").asText();

                if (type.equals("Polygon")) {
                    JsonNode cordinates = geometry.get("coordinates").get(0);
                    City city = createCity(cordinates, name, number,geofactory);
                    cities.add(city);
                } else if (type.equals("MultiPolygon")) {
                    JsonNode multiCoordinates = geometry.get("coordinates");
                    for (JsonNode polygonCoords : multiCoordinates) {
                        JsonNode coordinates = polygonCoords.get(0); // Take outer shell
                        City province = createCity(coordinates, name, number,geofactory);
                        cities.add(province);
                    }
                }
            }
        
        return cities;
    }

    private static City createCity(JsonNode coordinatesArray, String name, String ID,  GeometryFactory geometryFactory) {
        List<Coordinate> coordinatesArrayList = new ArrayList<>();
        for (JsonNode point : coordinatesArray) {
            double lon = point.get(0).asDouble();
            double lat = point.get(1).asDouble();
            coordinatesArrayList.add(new Coordinate(lon, lat));
        }

        Coordinate[] coordinates = coordinatesArrayList.toArray(new Coordinate[0]);

        LinearRing ring = geometryFactory.createLinearRing(coordinates);
        Polygon polygon = geometryFactory.createPolygon(ring, null);

        return new City(name, polygon, ID);
    }
    
    public static class CityPainter implements Painter<JXMapViewer> {
       
    
    
        @Override
        public void paint(Graphics2D g, JXMapViewer map, int widht, int height) {
            Graphics2D g2 = (Graphics2D) g;

            TileFactory tilef = map.getTileFactory();
            Rectangle pixels = map.getViewportBounds();

            int zoom = map.getZoom();
            
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, widht, height);

            for (City city : City.allCities) {
                Polygon geoPolygon = city.getPolygon();
                Coordinate[] cords = geoPolygon.getExteriorRing().getCoordinates();
    
                java.awt.Polygon pixPoly = new java.awt.Polygon();

                for (Coordinate cord : cords) {
                    
                    GeoPosition geop = new GeoPosition(cord.y, cord.x);
                    Point2D pixelp = tilef.geoToPixel(geop, zoom);

                    int x = (int) (pixelp.getX() - pixels.getX());
                    int y = (int) (pixelp.getY() - pixels.getY());


                    pixPoly.addPoint(x, y);
                }
    
                g2.setColor(new Color(20, 90, 130)); 
                g2.fillPolygon(pixPoly);
                g2.setColor(Color.WHITE); 
                g2.setStroke(new BasicStroke(1.5f));
                g2.drawPolygon(pixPoly);
            }
    
           
        }
    }
    


    public static JPanel getMapPanel() throws IOException{
        if(City.allCities == null){
            City.initializeCitys();
        }
        

        JXMapViewer mapViewer = new JXMapViewer();

        SwingUtilities.invokeLater(() -> {

            TileFactoryInfo blankInfo = new TileFactoryInfo(0, 17, 17, 256, true, true, "", "x", "y", "z") {
                @Override
                public String getTileUrl(int x, int y, int zoom) {
                    return null; 
                    
                }
            };
            
            

            DefaultTileFactory tileFactory = new DefaultTileFactory(blankInfo) {
                @Override
                public Tile getTile(int x, int y, int zoom) {
                    return new Tile(x, y, zoom) {
                        @Override
                        public boolean isLoaded() {
                            return true;  
                        }
            
                        @Override
                        public BufferedImage getImage() {
                            return null;  
                        }
                    };
                }
            };
        
    
            
            mapViewer.setTileFactory(tileFactory);
            
            


            
            GeoPosition turkeyCenter = new GeoPosition(39.29141455990794, 35.160410871742386);
            mapViewer.setZoom(11);
            mapViewer.setAddressLocation(turkeyCenter);
            mapViewer.setBackground(Color.WHITE);
            mapViewer.setPanEnabled(false);


            CityPainter painter = new CityPainter();
            mapViewer.setOverlayPainter(painter);

           
            GeometryFactory geometryFactory = new GeometryFactory();
            
            mapViewer.addMouseListener(new MouseAdapter() {
                 @Override
                 public void mouseClicked(MouseEvent e) {
                     java.awt.Point clickPoint = e.getPoint();
                     GeoPosition clickedGeo = mapViewer.convertPointToGeoPosition(clickPoint);
                     Coordinate clickedCoord = new Coordinate(clickedGeo.getLongitude(), clickedGeo.getLatitude());
                     Point jPoint = geometryFactory.createPoint(clickedCoord);

                     for (City city : City.allCities) {
                         if (city.getPolygon().contains(jPoint)) {
                             System.out.println("Clicked on " + city.getName().toUpperCase()+ " ID: " + city.getCityID());
                             return;
                         }
                     }
                     System.out.println("Clicked outside Turkey");
                 }
            });


        });

        return mapViewer;
    }
    

}
