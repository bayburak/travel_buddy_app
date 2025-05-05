package mypackage.view;
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;

public class RoundImage {

    public static BufferedImage makePerfectCircle(BufferedImage image, int diameter, 
            Color borderColor, int borderThickness) {
        
        // convert image to square
        BufferedImage squaredImage = squareCrop(image);
        
        // resize image
        BufferedImage resizedImage = resizeImage(squaredImage, diameter, diameter);
        
        // cut circle
        return applyCircleMask(resizedImage, borderColor, borderThickness);
    }

    private static BufferedImage squareCrop(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        int size = Math.min(width, height);
        
        int x = (width - size) / 2;
        int y = (height - size) / 2;
        
        BufferedImage squared = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = squared.createGraphics();
        g.drawImage(image, 0, 0, size, size, x, y, x + size, y + size, null);
        g.dispose();
        
        return squared;
    }

    
    private static BufferedImage applyCircleMask(BufferedImage image, Color borderColor, int borderThickness) {
        int diameter = image.getWidth(); 
        int newSize = diameter + borderThickness * 2;
        
        BufferedImage output = new BufferedImage(newSize, newSize, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = output.createGraphics();
        
        // quality
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        
        // border
        if (borderColor != null && borderThickness > 0) {
            g2.setColor(borderColor);
            g2.fillOval(0, 0, newSize, newSize);
        }
        
        // circle mask
        Ellipse2D.Double circle = new Ellipse2D.Double(borderThickness, borderThickness, diameter, diameter);
        
        g2.setClip(circle);
        g2.drawImage(image, borderThickness, borderThickness, null);
        g2.dispose();
        
        return output;
    }

    private static BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = resizedImage.createGraphics();
        
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        
        g.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
        g.dispose();
        return resizedImage;
    }
}
