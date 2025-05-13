package mypackage.controller;

import mypackage.model.City;
import mypackage.model.JournalEntry;
import mypackage.service.JournalDatabaseService;
import mypackage.service.MapService;
import mypackage.view.MainMap;
import mypackage.view.allJournals;
import mypackage.view.cityPanel;
import mypackage.view.explore;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.GeoPosition;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MapController {
    private final JFrame host;
    private final MainMap mainView; 
    private final JXMapViewer mapInstance; 
    private final GeometryFactory geometryFactory = new GeometryFactory();
    private cityPanel popupPanel;

    public MapController(JFrame host) throws IOException {
        this.host = host;
        this.mainView = new MainMap();
        this.mapInstance = (JXMapViewer) MapService.getMapPanel();
        mainView.add(mapInstance);

        setupTopBar();     
        setupMapClicks();  
        handleWindowResize();

        host.setContentPane(mainView);
        host.revalidate();
        host.repaint();
    }

    private void setupTopBar() throws IOException
    {
        mainView.getExploreBtn().addActionListener(e -> {
            new ExploreController().open(host);
        });
        mainView.getFindBuddyBtn().addActionListener(e -> {
            try {
                new FindBuddyController().open(host);
            } catch (ExecutionException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } catch (InterruptedException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        });

        mainView.getProfileBtn().addActionListener(e -> {
            new ProfileController().open(host);
        });
    }

    private void setupMapClicks()
    {
        mapInstance.addMouseListener(new MouseAdapter()
        {
            @Override public void mouseClicked(MouseEvent e)
            {
                GeoPosition gp = mapInstance.convertPointToGeoPosition(e.getPoint());
                var point = geometryFactory.createPoint(new Coordinate(gp.getLongitude(), gp.getLatitude()));
                if (point != null) 
                {
                    for (City c : City.allCities)
                    {
                        if (c.getPolygon().contains(point)) 
                        {
                            triggerPopupAsync(c, e.getPoint());
                            return;
                        }
                    }
                }

                closePopup();
            }
        });
    }


    private void triggerPopupAsync(City city, Point mapPt)
    {
        closePopup();
        new SwingWorker<Integer, Void>() {
            @Override protected Integer doInBackground() 
            {
                List<JournalEntry> entries;
                try 
                {
                    entries = JournalDatabaseService.getEntriesByUser(Session.getCurrentUserID());
                } 
                catch (Exception ex)
                {
                    System.out.println("Database fetch failed: " + ex.getMessage());
                    entries = List.of(); 
                }
                return (int) entries.stream()
                    .filter(en -> en.getCityID().equals(city.getCityID()))
                    .count();
            }
            @Override protected void done()
            {
                int count = 0;
                try 
                { 
                    count = get(); 
                } catch (InterruptedException | ExecutionException e) 
                {
                    e.printStackTrace();
                }
                try 
                {
                    renderPopup(city, mapPt);
                } 
                catch (InterruptedException | ExecutionException e) 
                {
                    System.out.println("Failed to render popup: " + e.getMessage());
                }
            }
        }.execute();
    }

    private void renderPopup(City city, Point mapPt) throws InterruptedException, ExecutionException {
        popupPanel = new cityPanel(city);
        popupPanel.addEntryListener(evt -> JournalController.createForm(host, city,this));
        popupPanel.addShowEntriesListener(evt -> ShowCityJournalController.createForm(host, city)); 

        Point pnl = SwingUtilities.convertPoint(mapInstance, mapPt, mainView);
        popupPanel.setBounds(pnl.x, pnl.y, 300, 100);
        mainView.add(popupPanel);
        mainView.setComponentZOrder(popupPanel, 0);
        mainView.repaint();
    }

    public void closePopup() {
        if (popupPanel != null) {
            mainView.remove(popupPanel);
            mainView.repaint();
            popupPanel = null;
        }
    }


    private void handleWindowResize() {
        host.addComponentListener(new ComponentAdapter() 
        {
            @Override public void componentResized(ComponentEvent e)
            {
                doLayoutSetup();
            }
        });
        doLayoutSetup();
    }

    private void doLayoutSetup()
    {
        int width = mainView.getWidth();
        int height = mainView.getHeight();
        int barHeight = mainView.getTopBar().getPreferredSize().height;
        mainView.getTopBar().setBounds(0, 0, width, barHeight);
        mapInstance.setBounds(0, barHeight, width, height - barHeight);
    }
}
