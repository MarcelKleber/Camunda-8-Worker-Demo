package com.ConsultingChallenge;
                
import java.net.URI;
import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.DeploymentEvent;
import io.camunda.zeebe.client.impl.oauth.OAuthCredentialsProvider;
import io.camunda.zeebe.client.impl.oauth.OAuthCredentialsProviderBuilder;
    
public class DeployProcess {
    
    
        //Zeebe Client Credentials
        private static final String zeebeGrpc = "grpcs://53e9327a-4777-4e0a-bb6d-1bb82a6b7f31.bru-2.zeebe.camunda.io:443";
        private static final String zeebeRest = "https://bru-2.zeebe.camunda.io/53e9327a-4777-4e0a-bb6d-1bb82a6b7f31";
        private static final String audience = "zeebe.camunda.io";
        private static final String clientId = "wWWQjpeV5zryVHPbWjgKE8jex336L91e";
        private static final String clientSecret = "qZyae8U0NT08~q7lZaeAL6x4m8cNxAHG6C09.KACMYs3DjM766I6.HGHvmsTGq4d";
        private static final String oAuthAPI = "https://login.cloud.camunda.io/oauth/token";
    
               
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

