package mypackage.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;

public class StorageService {

    private static Storage storage; 
    private static String bucketName;

   public static void initialize() throws FileNotFoundException, IOException{
        storage = StorageOptions.newBuilder()
        .setCredentials(GoogleCredentials.fromStream(new FileInputStream("Travel Buddy App/travel_buddy_app/serviceAccountKey.json")))
        .build()
        .getService();
        bucketName =  "travelbuddyapp-35c7b.firebasestorage.app";
   }

    
    public static boolean fileExists(String storagePath) {
        Blob blob = storage.get(BlobId.of(bucketName, storagePath));
        return blob != null && blob.exists();
    }
    
    
    public static String uploadFile(String localFilePath, String storagePath) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(localFilePath));
        String contentType = Files.probeContentType(Paths.get(localFilePath));
        BlobId blobId = BlobId.of(bucketName, storagePath);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
            .setContentType(contentType)
            .build();
        storage.create(blobInfo, bytes);
        String encodedPath = URLEncoder.encode(storagePath, "UTF-8");
        return String.format(
            "https://firebasestorage.googleapis.com/v0/b/%s/o/%s?alt=media",
            bucketName,
            encodedPath
        );
    }


    public static void deleteFile(String storagePath) {
        boolean deleted = storage.delete(BlobId.of(bucketName, storagePath));
        if (!deleted) {
            System.out.println("File not found or already deleted: " + storagePath);
        }
    }
}
