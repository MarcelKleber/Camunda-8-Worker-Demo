package com.ConsultingChallenge;

import java.time.Duration;
import com.ConsultingChallenge.handler.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Properties;
import java.util.Scanner;
import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.worker.JobWorker;
import io.camunda.zeebe.client.impl.oauth.OAuthCredentialsProvider;
import io.camunda.zeebe.client.impl.oauth.OAuthCredentialsProviderBuilder;

public class DownLoadPictureWorker {

	//load credentials from credentials.properties
	private static final Properties properties = new Properties();

	static {
		try (InputStream input = DeployProcess.class.getClassLoader().getResourceAsStream("credentials.properties")) {
			properties.load(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//Zeebe Client Credentials
	static String zeebeGrpc = properties.getProperty("zeebeGrpc");
	static String zeebeRest = properties.getProperty("zeebeRest");
	static String audience = properties.getProperty("audience");
	static String clientId = properties.getProperty("clientId");
	static String clientSecret = properties.getProperty("clientSecret");
	static String oAuthAPI = properties.getProperty("oAuthAPI");

	   	
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