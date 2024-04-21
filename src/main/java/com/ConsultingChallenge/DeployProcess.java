package com.ConsultingChallenge;
                
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Properties;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.DeploymentEvent;
import io.camunda.zeebe.client.impl.oauth.OAuthCredentialsProvider;
import io.camunda.zeebe.client.impl.oauth.OAuthCredentialsProviderBuilder;
    
public class DeployProcess {
    
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

    
               
        //Process Details for the challenge
        private static final String BPMN_PROCESS_PATH = "main_AnimalPictureDownload.bpmn";
        private static final String BPMN_FORM_PATH = "form_imageSelection.form";

               
        public static void main(String[] args){
            
            final OAuthCredentialsProvider credentialsProvider =
                    new OAuthCredentialsProviderBuilder()
                          .authorizationServerUrl(oAuthAPI)
                        .audience(audience)
                        .clientId(clientId)
                        .clientSecret(clientSecret)	
                        .build();
                
            
                try (final ZeebeClient client =
                    ZeebeClient.newClientBuilder()
                        .grpcAddress(new URI(zeebeGrpc))
                        .restAddress(new URI(zeebeRest))
                           .credentialsProvider(credentialsProvider)	
                        .build()) {
                    
                    client.newTopologyRequest().send().join();
                    System.out.println("Connected to: " + client.newTopologyRequest().send().join());
                    
                        
                    //Deploy the process model
                    final DeploymentEvent deploymentEvent =
                    client.newDeployResourceCommand()
                    .addResourceFromClasspath(BPMN_PROCESS_PATH)
                    .addResourceFromClasspath(BPMN_FORM_PATH)
                    .send()
                    .join();
                      
                    System.out.println("Process deployed");
                                
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    
    }

