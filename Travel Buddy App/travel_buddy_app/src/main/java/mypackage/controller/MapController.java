package mypackage.controller;

import mypackage.model.City;
import mypackage.model.JournalEntry;
import mypackage.service.JournalDatabaseService;
import mypackage.service.MapService;
import mypackage.view.MainMap;
import mypackage.view.allJournals;
import mypackage.view.cityPanel;

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

/**
 * Hosts the map and overlays a popup when a city is clicked.
 */
public class MapController {
    private final JFrame host;
    private final MainMap view;
    private final JXMapViewer map;
    private final GeometryFactory gf = new GeometryFactory();
    private cityPanel popup;

    public MapController(JFrame host) throws IOException {
        this.host = host;
        this.view = new MainMap();
        this.map  = (JXMapViewer) MapService.getMapPanel();
        view.add(map);

        wireTopBar();
        wireClicks();
        syncLayout();

        host.setContentPane(view);
        host.revalidate();
        host.repaint();
    }

    /** Set up Explore and Find buttons on the top bar */
    private void wireTopBar() {
        view.getExploreBtn().addActionListener(e -> {
            // TODO: implement explore
        });
        view.getFindBuddyBtn().addActionListener(e -> {
            // TODO: implement find buddies
        });
    }

    /** Listen for clicks on the map itself */
    private void wireClicks() {
        map.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                GeoPosition gp = map.convertPointToGeoPosition(e.getPoint());
                var pt = gf.createPoint(new Coordinate(gp.getLongitude(), gp.getLatitude()));
                for (City c : City.allCities) {
                    if (c.getPolygon().contains(pt)) {
                        showPopupAsync(c, e.getPoint());
                        return;
                    }
                }
                hidePopup();
            }
        });
    }

    private void showPopupAsync(City city, Point mapPt) {
        hidePopup();
        new SwingWorker<Integer,Void>() {
            @Override protected Integer doInBackground() throws Exception {
                List<JournalEntry> entries = JournalDatabaseService.getEntriesByUser(Session.getCurrentUserID());
                return (int) entries.stream()
                    .filter(en -> en.getCityID().equals(city.getCityID()))
                    .count();
            }
            @Override protected void done() {
                int count = 0;
                try { count = get(); } catch (InterruptedException|ExecutionException ignored) {}
                city.setEntryCount(count);
                try {
                    showPopup(city, mapPt);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }.execute();
    }

    private void showPopup(City city, Point mapPt) throws InterruptedException, ExecutionException {
        popup = new cityPanel(city);
        popup.addEntryListener(evt -> JournalController.open(host, city));
        Point pnl = SwingUtilities.convertPoint(map, mapPt, view);
        popup.setBounds(pnl.x, pnl.y, 300, 100);
        view.add(popup);
        view.setComponentZOrder(popup, 0);
        view.repaint();
    }

    private void hidePopup() {
        if (popup != null) {
            view.remove(popup);
            view.repaint();
            popup = null;
        }
    }

    /** Keep map and bar sized to the window */
    private void syncLayout() {
        host.addComponentListener(new ComponentAdapter() {
            @Override public void componentResized(ComponentEvent e) {
                doLayout();
            }
        });
        doLayout();
    }

    private void doLayout()
    {
        int w = view.getWidth();
        int h = view.getHeight();
        int barH = view.getTopBar().getPreferredSize().height;
        view.getTopBar().setBounds(0, 0, w, barH);
        map.setBounds(0, barH, w, h - barH);
        if (popup != null)
        {

        }
    }
}