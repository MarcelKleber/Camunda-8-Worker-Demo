# Camunda Consulting Challenge
App for the Camunda Consulting Challenge

## Module 1: Deploy Process
- IMPORTANT: Change credentials for C8 local/saas if necessary
- deploys the bpmn "Process_ConsultingChallenge.bpmn"
- deploy the form "form_imageSelection.form"

## Module 2: Download Picture of a dog with API
- Downloads a picture from https://place.dog and saves it in Download-Folder of current user

## Module 3: Start instance with REST Api via webhook
- starts instance of process "Process_ConsultingChallenge"

## Module 4: DownloadPictureWorker with Handler
- IMPORTANT: Change credentials for C8 local/saas if necessary

- zeebe client gets startet und job worker "DownloadPictureWorker" activated
- To stop worker: type "exit"

- Picture is downloaded the the Download-Folder of the current user with timestamp


_________

## How to Run the Process "ConsultingChallenge"

1. Run Module 1 to deploy the bpmn and form
2. Run Module 3 to start instance
3. Run Module 4 to start worker and handler
4. Select in Tasklist the picture to downloaded
5. Look for Picture in Download-Folder
6. profit


## Architecture Overview

![ConsultingChallenge_Architecture](https://github.com/MarcelKleber/ConsultingChallenge/assets/167547660/bdf34742-7a92-4e95-92ff-3591ae3f5483)

