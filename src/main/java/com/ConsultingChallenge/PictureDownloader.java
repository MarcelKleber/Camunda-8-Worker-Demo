package com.ConsultingChallenge;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


//Method to download a picture from a given URL

public class PictureDownloader {
    
    public static void main(String[] args) {
        
        //Test values
        String imageUrl ="https://place.dog/300/200";
        String animal ="dog";

        try {
            downloadImage(imageUrl, animal);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void downloadImage(String imageUrl, String animal) throws IOException {
            
        try {
            // Open a connection to the URL
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Check if the connection was successful
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Read the image data from the connection
                InputStream inputStream = connection.getInputStream();

                // Determine the path to the user's "Downloads" folder
                String downloadsFolderPath = System.getProperty("user.home") + File.separator + "Downloads";
                File downloadsFolder = new File(downloadsFolderPath);

                // Save the image to a file in the "Downloads" folder
                    
                    //create timestamp for file path
                    LocalDateTime now = LocalDateTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HmmssSSS");
                    String timestamp = now.format(formatter);
                
                String fileName = "image_downloaded_" + animal + "_"+ timestamp+ ".png";
                File outputFile = new File(downloadsFolder, fileName);
                FileOutputStream outputStream = new FileOutputStream(outputFile);

                //write picture to buffer; maybe there is another method to do this
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }

                // Close streams
                outputStream.close();
                inputStream.close();

                System.err.println("Picture sucessfully downloaded.");

                } else {
                System.err.println("Failed to download image. HTTP response code: " + responseCode);
            }
          
        } catch (IOException e) {
            System.err.println("Error while downloading image: " + e.getMessage());
        }

    }
}
