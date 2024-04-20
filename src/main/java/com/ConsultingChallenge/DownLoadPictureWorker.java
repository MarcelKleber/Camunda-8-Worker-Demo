package com.ConsultingChallenge;

import java.time.Duration;
import com.ConsultingChallenge.handler.*;
import java.net.URI;
import java.util.Scanner;
import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.worker.JobWorker;
import io.camunda.zeebe.client.impl.oauth.OAuthCredentialsProvider;
import io.camunda.zeebe.client.impl.oauth.OAuthCredentialsProviderBuilder;

public class DownLoadPictureWorker {


	//Zeebe Client Credentials
	private static final String zeebeGrpc = "grpcs://53e9327a-4777-4e0a-bb6d-1bb82a6b7f31.bru-2.zeebe.camunda.io:443";
	private static final String zeebeRest = "https://bru-2.zeebe.camunda.io/53e9327a-4777-4e0a-bb6d-1bb82a6b7f31";
	private static final String audience = "zeebe.camunda.io";
	private static final String clientId = "wWWQjpeV5zryVHPbWjgKE8jex336L91e";
	private static final String clientSecret = "qZyae8U0NT08~q7lZaeAL6x4m8cNxAHG6C09.KACMYs3DjM766I6.HGHvmsTGq4d";
	private static final String oAuthAPI = "https://login.cloud.camunda.io/oauth/token";

	   	
	//Worker used in the challenge
	private static final String JOB_TYPE = "DownloadPictureWorker";
	
    public static void main(String[] args){
    	
    	final OAuthCredentialsProvider credentialsProvider =
    			new OAuthCredentialsProviderBuilder()
			  		.authorizationServerUrl(oAuthAPI)
            		.audience(audience)
            		.clientId(clientId)
            		.clientSecret(clientSecret)	
				    .build();
	    	
			//Start Zeebe Client
			try (final ZeebeClient client =
		        ZeebeClient.newClientBuilder()
					.grpcAddress(new URI(zeebeGrpc))
            		.restAddress(new URI(zeebeRest))
           			.credentialsProvider(credentialsProvider)	
				    .build()) {
				
				client.newTopologyRequest().send().join();
				System.out.println("Connected to: " + client.newTopologyRequest().send().join());
				
							//Start a Job Worker
				System.out.println("Opening job worker.");

				final JobWorker DownloadPictureWorker =
						client.newWorker()
							.jobType(JOB_TYPE)
							.handler(new DownloadPictureHandler())
							.timeout(Duration.ofSeconds(1000).toMillis())
							.open();
			
				System.out.println("Job worker opened and receiving jobs.");
				
				// run until System.in receives exit command
				waitUntilSystemInput("exit");

						
		} catch (Exception e) {
		    e.printStackTrace();

		}
    }


//Method to scan for exit code in terminal
private static void waitUntilSystemInput(final String exitCode) {
	try (final Scanner scanner = new Scanner(System.in)) {
	  while (scanner.hasNextLine()) {
		final String nextLine = scanner.nextLine();
		if (nextLine.contains(exitCode)) {
		  return;
		}
	  }
	}
  }

}