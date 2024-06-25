# Camunda 8 Worker Demo
App for an Camunda 8 Worker Demo

## Module 1: Deploy Process
- IMPORTANT: Change properties for C8 local/saas if necessary
- deploys the bpmn "Process_ConsultingChallenge.bpmn"
- deploy the form "form_imageSelection.form"

## Module 2: Download Picture from a URL and saves it locally
- can download a picture from a given URL; given animal name is used for file name
- saves it in Download-Folder of current user
- in isolated execution downloads a picture from https://place.dog as test default

## Module 3: Start instance with REST Api via webhook
- starts instance of process "Process_ConsultingChallenge"

## Module 4: DownloadPictureWorker with Handler
- IMPORTANT: Change properties for C8 local/saas if necessary

- zeebe client gets startet und job worker "DownloadPictureWorker" activated
- To stop worker: type "exit"

- Picture is downloaded the the Download-Folder of the current user with timestamp


_________

## How to Run the Process "ConsultingChallenge"

1. If applicable: change credentials in credentials.properties
2. Run Module 1 to deploy the bpmn and form
3. Run Module 3 to start instance
4. Run Module 4 to start worker and handler
5. Select in Tasklist the picture to downloaded
6. Look for Picture in Download-Folder
7. profit


## Architecture Overview

![ConsultingChallenge_Architecture](https://github.com/MarcelKleber/ConsultingChallenge/assets/167547660/e87503f1-17a9-4808-afb5-f6de143b5601)






