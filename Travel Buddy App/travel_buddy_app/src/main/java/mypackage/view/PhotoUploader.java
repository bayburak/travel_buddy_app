package mypackage.view;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class PhotoUploader extends JFrame {
    private JLabel dropArea;
    private BufferedImage uploadedImage;
    private JButton uploadButton;
    private String fileURL;

    public PhotoUploader() {
        setTitle("Upload photo (jpg, png, gif, bmp)");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        getContentPane().setBackground(new Color(173, 216, 230));

        dropArea = new JLabel("You can drag and drop photos here to add them", SwingConstants.CENTER);
        dropArea.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        dropArea.setPreferredSize(new Dimension(400, 300));
        dropArea.setOpaque(true);
        dropArea.setBackground(Color.WHITE);
        dropArea.setForeground(Color.LIGHT_GRAY);
        dropArea.setFont(new Font("Arial", Font.PLAIN, 18));

        
        uploadButton = new JButton("Attach Photo");
        uploadButton.setEnabled(false);
        uploadButton.setBackground(new Color(20, 79, 147));
        uploadButton.setForeground(Color.WHITE); 
        uploadButton.setFont(new Font("Arial", Font.BOLD, 16));
        uploadButton.setFocusPainted(false);
        uploadButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        uploadButton.addActionListener(e -> {
            if (uploadedImage != null) {
                JOptionPane.showMessageDialog(this, "You attached your photo!");
                dispose();
            }
        });


        new DropTarget(dropArea, new DropTargetListener() {
            @Override
            public void dragEnter(DropTargetDragEvent dtde) {
                dropArea.setBackground(new Color(200, 230, 255));
                dropArea.setText("You dragged photo");
            }

            @Override
            public void dragOver(DropTargetDragEvent dtde) {}

            @Override
            public void dropActionChanged(DropTargetDragEvent dtde) {}

            @Override
            public void dragExit(DropTargetEvent dte) {
                dropArea.setBackground(Color.LIGHT_GRAY);
                dropArea.setText("You can drag and drop photos here to add them");
            }

            @Override
            public void drop(DropTargetDropEvent dtde) {
                try {
                    dtde.acceptDrop(DnDConstants.ACTION_COPY);
                    Transferable transferable = dtde.getTransferable();
                    
                    if (transferable.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                        java.util.List<File> fileList = (java.util.List<File>) 
                            transferable.getTransferData(DataFlavor.javaFileListFlavor);
                        
                        if (!fileList.isEmpty()) {
                            File file = fileList.get(0);
                            if (isImageFile(file)) {
                                uploadedImage = ImageIO.read(file);
                                fileURL = file.getAbsolutePath(); 
                                dropArea.setText(file.getName());
                                uploadButton.setEnabled(true);
                                
                                ImageIcon icon = new ImageIcon(
                                uploadedImage.getScaledInstance(
                                dropArea.getWidth(), dropArea.getHeight(), Image.SCALE_SMOOTH));
                                dropArea.setIcon(icon);
                                dropArea.setHorizontalTextPosition(JLabel.CENTER);
                                dropArea.setVerticalTextPosition(JLabel.BOTTOM);
                            } else {
                                JOptionPane.showMessageDialog(PhotoUploader.this, 
                                    "Please select a valid image file (jpg, png, gif, bmp)");
                            }
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(PhotoUploader.this, 
                        "Error: " + ex.getMessage());
                } finally {
                    dropArea.setBackground(Color.LIGHT_GRAY);
                }
            }
        });

    
        add(dropArea, BorderLayout.CENTER);
        add(uploadButton, BorderLayout.SOUTH);
        this.setVisible(true);
    }

    private boolean isImageFile(File file) {
        String name = file.getName().toLowerCase();
        return name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".png") || name.endsWith(".gif") || name.endsWith(".bmp");
    }

    public BufferedImage getUploadedImage() {
        return uploadedImage;
    }

    public String getFileURL() {
        return fileURL;
    }
}

