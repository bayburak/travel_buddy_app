package mypackage.model;


import java.io.IOException;
import java.util.List;

import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Polygon;

import mypackage.service.MapService;

public class City {
    
    public static List<City> allCities;

    private String cityID;
    private String name;
    private int entryCount;
    private Polygon polygon;

    public City(String name, Polygon polgon, String ID){
        this.name = name;
        this.polygon = polgon;
        this.cityID = ID;
        this.entryCount = 0;
    }

    public static void initializeCitys() throws IOException{
        allCities = MapService.loadProvincesFromGeoJson(new GeometryFactory());
    }

    public List<JournalEntry> getEntriesbyCity(){
        return null;
    }
    public void incrementEntryCount(){ entryCount++; }

    public String getName(){ return name;}
    public Polygon getPolygon(){ return polygon;}
    public String getCityID(){ return cityID;}
    public int getEntryCount(){ return entryCount;}


}
