package mypackage.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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

    

    
    public static String uploadFile(String localFilePath, String storagePath) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(localFilePath));
        String contentType = Files.probeContentType(Paths.get(localFilePath));
        BlobId blobId = BlobId.of(bucketName, storagePath);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
            .setContentType(contentType)
            .build();
        storage.create(blobInfo, bytes);
        return String.format("https://storage.googleapis.com/%s/%s", bucketName, storagePath);
    }


    public static void deleteFile(String storagePath) {
        boolean deleted = storage.delete(BlobId.of(bucketName, storagePath));
        if (!deleted) {
            System.out.println("File not found or already deleted: " + storagePath);
        }
    }
}
