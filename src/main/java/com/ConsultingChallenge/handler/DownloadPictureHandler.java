package com.ConsultingChallenge.handler;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.client.api.worker.JobHandler;

import java.util.HashMap;
import java.util.Map;

import com.ConsultingChallenge.PictureDownloader;

public class DownloadPictureHandler implements JobHandler {
	
		
	@Override
    public void handle(JobClient client, ActivatedJob job) throws Exception {
    	
        System.out.println("Download Handler startet");
        
         // Get the URL of the image based on user input
         String select_picture = job.getVariablesAsMap().get("select_picture").toString();
         System.out.println(select_picture);
        
         String imageUrl = null;
         String animal = null;

         switch (select_picture){
            case "selection_dog":
                imageUrl = "https://place.dog/300/200";
                animal = "dog";
                break;
            case "selection_cat":
                imageUrl = "http://placekitten.com/200/300";
                animal = "cat";
                break;
            case "selection_bears":
                imageUrl = "https://placebear.com/200/300";
                animal = "bears";
                break;
            default:
                break;
         }

        try {
       
        // Call the downloadImage method thats downloads picture from the URL with API
        PictureDownloader.downloadImage(imageUrl, animal);

        //Build the Output Process Variables
        final Map<String, Object> outputVariables = new HashMap<String, Object>();
        outputVariables.put("result", "sucessful");

        //Complete the Job in zeebe
    	client.newCompleteCommand(job.getKey()).variables(outputVariables).send().join();
        System.out.println("Image downloaded and saved to Downloads folder.");

                  
        } catch (Exception e) {
            System.err.println("Error while downloading image: " + e.getMessage());
            // Handle any exceptions
            client.newThrowErrorCommand(job.getKey()).errorCode("API_Error").send().join();
        }

           	
    }

}