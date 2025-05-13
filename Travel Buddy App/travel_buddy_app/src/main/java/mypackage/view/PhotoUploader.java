package mypackage.view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.function.Consumer;

/**
 * A simple drag-and-drop / browse photo uploader.
 * Now accepts a callback to receive the selected file URL.
 */
public class PhotoUploader extends JFrame {
    private final JLabel dropArea;
    private       BufferedImage uploadedImage;
    private final JButton uploadButton;
    private       String fileURL;
    private final Consumer<String> onPhotoSelected;

    /** No-arg: no callback */
    public PhotoUploader() {
        this("Upload photo (jpg, png, gif, bmp)", null);
    }

    

    /** Title-only: no callback */
    public PhotoUploader(String title) {
        this(title, null);
    }

    /**
     * Primary constructor with a callback.
     * @param onPhotoSelected called with the selected file's absolute path
     */
    public PhotoUploader(Consumer<String> onPhotoSelected) {
        this("Upload photo (jpg, png, gif, bmp)", onPhotoSelected);
    }

    private PhotoUploader(String title, Consumer<String> onPhotoSelected) {
        super(title);
        this.onPhotoSelected = onPhotoSelected;
        dropArea    = new JLabel("Drag & drop a photo here", SwingConstants.CENTER);
        uploadButton = new JButton("Attach Photo");
        initUI();
    }

    private void initUI() {
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(173, 216, 230));

        // Drop area
        dropArea.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        dropArea.setOpaque(true);
        dropArea.setBackground(Color.WHITE);
        dropArea.setForeground(Color.LIGHT_GRAY);
        dropArea.setFont(new Font("Arial", Font.PLAIN, 18));
        new DropTarget(dropArea, new PhotoDropListener());
        add(dropArea, BorderLayout.CENTER);

        // Upload button
        uploadButton.setEnabled(false);
        uploadButton.setBackground(new Color(20, 79, 147));
        uploadButton.setForeground(Color.WHITE);
        uploadButton.setFont(new Font("Arial", Font.BOLD, 16));
        uploadButton.setFocusPainted(false);
        uploadButton.addActionListener(e -> {
            if (uploadedImage != null) {
                // notify user
                JOptionPane.showMessageDialog(
                    this,
                    "Photo attached!",
                    "Photo Uploader",
                    JOptionPane.INFORMATION_MESSAGE
                );
                // fire the callback
                if (onPhotoSelected != null) {
                    onPhotoSelected.accept(fileURL);
                }
                dispose();
            }
        });
        add(uploadButton, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private class PhotoDropListener implements DropTargetListener {
        @Override public void dragEnter(DropTargetDragEvent dtde) {
            dropArea.setBackground(new Color(200, 230, 255));
            dropArea.setText("Drop to attach");
        }
        @Override public void dragOver(DropTargetDragEvent dtde) {}
        @Override public void dropActionChanged(DropTargetDragEvent dtde) {}
        @Override public void dragExit(DropTargetEvent dte) {
            dropArea.setBackground(Color.WHITE);
            dropArea.setText("Drag & drop a photo here");
        }
        @Override public void drop(DropTargetDropEvent dtde) {
            try {
                dtde.acceptDrop(DnDConstants.ACTION_COPY);
                Transferable tr = dtde.getTransferable();
                if (tr.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                    @SuppressWarnings("unchecked")
                    java.util.List<File> files =
                        (java.util.List<File>) tr.getTransferData(DataFlavor.javaFileListFlavor);
                    File f = files.get(0);
                    if (isImageFile(f)) {
                        uploadedImage = ImageIO.read(f);
                        fileURL       = f.getAbsolutePath();
                        dropArea.setIcon(new ImageIcon(
                            uploadedImage.getScaledInstance(
                                dropArea.getWidth(),
                                dropArea.getHeight(),
                                Image.SCALE_SMOOTH
                            )
                        ));
                        dropArea.setText(f.getName());
                        uploadButton.setEnabled(true);
                    } else {
                        JOptionPane.showMessageDialog(
                            PhotoUploader.this,
                            "Select a valid image file (jpg, png, gif, bmp)"
                        );
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(
                    PhotoUploader.this,
                    "Error: " + ex.getMessage()
                );
            } finally {
                dropArea.setBackground(Color.WHITE);
            }
        }
    }

    private boolean isImageFile(File file) {
        String n = file.getName().toLowerCase();
        return n.endsWith(".jpg")
            || n.endsWith(".jpeg")
            || n.endsWith(".png")
            || n.endsWith(".gif")
            || n.endsWith(".bmp");
    }

    /** @return the selected file’s absolute path, or null if none */
    public String getFileURL() {
        return fileURL;
    }

    /** @return the loaded BufferedImage, or null if none */
    public BufferedImage getUploadedImage() {
        return uploadedImage;
    }
}
