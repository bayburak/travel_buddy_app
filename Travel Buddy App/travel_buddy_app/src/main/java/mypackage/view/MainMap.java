    // src/main/java/mypackage/view/MainMap.java
    package mypackage.view;

    import javax.swing.*;
    import java.awt.*;

    /**
     * Container holding a fixed top‐bar and a map underneath, using absolute layout
     * so popups can be overlaid freely.
     */
    public class MainMap extends JPanel {
        public final JPanel topBar;
        public final JButton exploreBtn;
        public final JButton findBuddyBtn;
        public final JButton ProfileBtn;

        private static final Color BAR_COLOR = new Color(34,  86, 153);
        private static final Color BUTTON_COLOR = new Color(19,  49,  88);
        private static final Font BUTTON_FONT  = new Font("Arial", Font.BOLD, 12);
        private static final int BAR_HEIGHT = 70;

        public MainMap()
        {
            super(null);
            setBackground(Color.WHITE);

            topBar = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 10));
            topBar.setBackground(BAR_COLOR);

            exploreBtn   = new RoundedButton("Explore", 20);
            findBuddyBtn = new RoundedButton("Find Travel Buddies", 20);
            ProfileBtn = new RoundedButton("Profile", 20);

            styleButton(exploreBtn);
            styleButton(findBuddyBtn);
            styleButton(ProfileBtn);
            topBar.add(exploreBtn);
            topBar.add(findBuddyBtn);
            topBar.add(ProfileBtn);

            add(topBar);
        }

        private void styleButton(JButton b) {
            b.setFont(BUTTON_FONT);
            b.setBackground(BUTTON_COLOR);
            b.setForeground(Color.WHITE);
            b.setFocusPainted(false);
            b.setPreferredSize(new Dimension(180, 40));
        }

        public JPanel getTopBar()
        {
            return topBar;
        }
        public JButton getExploreBtn()
        {
            return exploreBtn;
        }
        public JButton getFindBuddyBtn()
        {
            return findBuddyBtn;
        }

        public JButton getProfileBtn()
        {
            return ProfileBtn;
        }

    }
