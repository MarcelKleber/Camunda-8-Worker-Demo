package com.ConsultingChallenge.handler;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.client.api.worker.JobHandler;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class DownloadPictureHandler implements JobHandler {
	
		
	@Override
    public void handle(JobClient client, ActivatedJob job) throws Exception {
    	
        System.out.println("Download Handler startet");
        
         // Get the URL of the image based on user input
         String select_picture = job.getVariablesAsMap().get("select_picture").toString();
         System.out.println(select_picture);
        
         String imageUrl = null;

         switch (select_picture){
            case "selection_dog":
                imageUrl = "https://place.dog/300/200";
                break;
            case "selection_cat":
                imageUrl = "http://placekitten.com/200/300";
                break;
            case "selection_bears":
                imageUrl = "https://placebear.com/200/300";
                break;
            default:
                break;
         }

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
                
                //Save File in Downloadfolder of the current user with a timestamp
                String fileName = "image_downloaded_" + select_picture + "_"+ timestamp+ ".png";
                File outputFile = new File(downloadsFolder, fileName);
                FileOutputStream outputStream = new FileOutputStream(outputFile);

                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }

                // Close streams
                outputStream.close();
                inputStream.close();

                //Build the Output Process Variables
                final Map<String, Object> outputVariables = new HashMap<String, Object>();
                outputVariables.put("result", "sucessful");

                 //Complete the Job
    	        client.newCompleteCommand(job.getKey()).variables(outputVariables).send().join();
                System.out.println("Image downloaded and saved to Downloads folder.");

            } else {
                System.err.println("Failed to download image. HTTP response code: " + responseCode);
            }

          
        } catch (IOException e) {
            System.err.println("Error while downloading image: " + e.getMessage());
            // Handle any exceptions
            client.newThrowErrorCommand(job.getKey()).errorCode("API_Error").send().join();
        }

           	
    }

}