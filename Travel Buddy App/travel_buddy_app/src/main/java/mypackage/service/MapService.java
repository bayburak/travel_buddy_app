package mypackage.service;


import java.awt.event.MouseAdapter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCursor;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactoryInfo;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LinearRing;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import mypackage.model.City;

public class MapService {

    
    public static List<City> loadProvincesFromGeoJson(GeometryFactory geometryFactory) throws IOException {

        List<City> cities = new ArrayList<>();
        
            ObjectMapper mapper = new ObjectMapper();
            InputStream is = MapService.class.getResourceAsStream("/trCities.json");
            JsonNode root = mapper.readTree(is);

            JsonNode features = root.get("features");

            for (JsonNode feature : features) {
                String name = feature.get("properties").get("name").asText();
                JsonNode geometry = feature.get("geometry");
                String number = feature.get("properties").get("number").asText();
                String type = geometry.get("type").asText();

                if (type.equals("Polygon")) {
                    JsonNode coordinates = geometry.get("coordinates").get(0);
                    City city = createProvinceFromCoordinates(coordinates, name, number,geometryFactory);
                    cities.add(city);
                } else if (type.equals("MultiPolygon")) {
                    JsonNode multiCoordinates = geometry.get("coordinates");
                    for (JsonNode polygonCoords : multiCoordinates) {
                        JsonNode coordinates = polygonCoords.get(0); // Take outer shell
                        City province = createProvinceFromCoordinates(coordinates, name, number,geometryFactory);
                        cities.add(province);
                    }
                }
            }
        
        return cities;
    }

    private static City createProvinceFromCoordinates(JsonNode coordinatesArray, String name, String ID,  GeometryFactory geometryFactory) {
        List<Coordinate> coordinatesList = new ArrayList<>();
        for (JsonNode point : coordinatesArray) {
            double lon = point.get(0).asDouble();
            double lat = point.get(1).asDouble();
            coordinatesList.add(new Coordinate(lon, lat));
        }

        Coordinate[] coordinates = coordinatesList.toArray(new Coordinate[0]);

        LinearRing shell = geometryFactory.createLinearRing(coordinates);
        Polygon polygon = geometryFactory.createPolygon(shell, null);

        return new City(name, polygon, ID);
    }

    public static JPanel getMapPanel() throws IOException{
        City.initializeCitys();

        JXMapViewer mapViewer = new JXMapViewer();

        SwingUtilities.invokeLater(() -> {
            
            TileFactoryInfo cartoLight = new TileFactoryInfo(
                1, 19, 20, 256, true, true,
                "https://basemaps.cartocdn.com/dark_all", "x", "y", "z") {

                @Override
                public String getTileUrl(int x, int y, int zoom) {
                    int z = 20 - zoom;
                    return this.baseURL + "/" + z + "/" + x + "/" + y + ".png";
                }
            };
            DefaultTileFactory tileFactory = new DefaultTileFactory(cartoLight);
            mapViewer.setTileFactory(tileFactory);
            
            tileFactory.setThreadPoolSize(8);


            //Center
            GeoPosition turkeyCenter = new GeoPosition(39.29141455990794, 35.160410871742386);
            mapViewer.setZoom(14);
            mapViewer.setAddressLocation(turkeyCenter);



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
                            System.out.println("Clicked on " + city.getName().toUpperCase());
                            return;
                        }
                    }
                    System.out.println("Clicked outside Turkey");
                }
            });

            mapViewer.addMouseWheelListener(new MouseWheelListener() {
                @Override
                public void mouseWheelMoved(MouseWheelEvent e) {
                    int currentZoom = mapViewer.getZoom();
                    int newZoom = currentZoom - e.getWheelRotation(); // Mouse wheel scroll can zoom in or out
            
                    
                    if (newZoom >= 12 && newZoom <= 14) {
                        mapViewer.setZoom(newZoom);
                    }
                    if(newZoom == 14){
                        //mapViewer.recenterToAddressLocation(); 
                    }
                }
            });


            double minLat = 35.0;
            double maxLat = 43.0;
            double minLon = 25.5;
            double maxLon = 45.0;

            mapViewer.addPropertyChangeListener("centerPosition", e -> {
                GeoPosition center = mapViewer.getCenterPosition();
            
                double lat = Math.max(minLat, Math.min(maxLat, center.getLatitude()));
                double lon = Math.max(minLon, Math.min(maxLon, center.getLongitude()));
            
                if (lat != center.getLatitude() || lon != center.getLongitude()) {
                    mapViewer.setCenterPosition(new GeoPosition(lat, lon));
                }
            });
            
            MouseInputListener mia = new PanMouseInputListener(mapViewer);
            mapViewer.addMouseListener(mia);
            mapViewer.addMouseMotionListener(mia);
            mapViewer.addMouseWheelListener(new ZoomMouseWheelListenerCursor(mapViewer));
            //mapViewer.addMouseListener(new CenterMapListener(mapViewer));
            mapViewer.setPanEnabled(true);
            
            //mapViewer.setRestrictOutsidePanning(true); 
          

           
        });

        return mapViewer;
    }
    

}
