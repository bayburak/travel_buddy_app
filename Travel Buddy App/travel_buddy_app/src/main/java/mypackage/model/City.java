// src/main/java/mypackage/model/City.java
package mypackage.model;

import java.io.IOException;
import java.util.List;
import org.locationtech.jts.geom.Polygon;
import mypackage.service.MapService;

public class City {
    public static List<City> allCities;

    private final String  cityID;
    private final String  name;
    private int           entryCount;
    private final Polygon polygon;

    public City(String name, Polygon polygon, String ID)
    {
        this.name = name;
        this.polygon = polygon;
        this.cityID = ID;
        this.entryCount = 0;
    }

    public static void initializeCitys() throws IOException
    {
        allCities = MapService.readCities();
    }

    public void incrementEntryCount()
    {
        entryCount++;
    }

    public void setEntryCount(int cnt)
    {
        this.entryCount = cnt;
    }

    public String getName()
    { 
        return name;
    }
    public Polygon getPolygon()
    { 
        return polygon; 
    }
    public String getCityID()
    { 
        return cityID;
    }
    public int getEntryCount()
    { 
        return entryCount;
    }
}
