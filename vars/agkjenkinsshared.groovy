def call(Map agkpipeline) {
    
    pipeline {
        agent any
        stages {
            stage('Scan') {
                steps {
	            sh 'pwd'
		    sh 'ls -la'
		    sh agkpipeline.poc
		    sh 'rm -rf testresults.xml'
		    sh 'touch testresults.xml'
		    sh 'sfdx scanner:run --target agkpipeline.may  --pmdconfig ./config/pmd/apex_ruleset.xml --format junit --outfile testresults.xml'
		    sh 'cat testresults.xml'
		    echo 'agkpipeline.poc'
		    echo agkpipeline.poc
		    sh '''
                        #!/bin/bash
                        if grep -q 'type="5"' "./testresults.xml"; then
                        rm -rf testresult.xml
                        #exit 1
                        echo 'violation 5'
                        elif grep -q 'type="99"' "./testresults.xml"; then
                        rm -rf testresult.xml
                        echo 'violation 4'
                        #exit 1
		        elif grep -q 'type="3"' "./testresults.xml"; then
                        rm -rf testresult.xml
                        echo 'violation 3'
		        #exit 1
                        else
                        echo 'rule check okay'
                        rm -rf testresult.xml
                        #exit 0
                        fi
                    '''
                 }
          }
          stage('Authenticate') {
	      steps {
	          echo agkpipeline.sand
		  a=$(echo agkpipeline.sand)
	          sh '''
                      #!/bin/bash
		      $sand1
		      INSTANCE_URL="https://login.salesforce.com"
                      CLIENT_ID="3MVG9ZF4bs_.MKujkw6ZG8mm2riWYl_WPVCg6Mhj5XjZ0ioVY1heGMLB1ahrkTs9TIZwbZCR5IQMUbkfglBwp"
                      USERNAME="admin2@libertysalesforce.org"
                      JWT_KEY_FILE="$SSH_KEY_FOR_DEVHUB"
                      echo "Start Authentication"
                      #sfdx auth:jwt:grant --instanceurl $INSTANCE_URL --clientid $CLIENT_ID --username ${SF_USERNAME} --jwtkeyfile $JWT_KEY_FILE --setdefaultdevhubusername --setalias HubOrg
                      echo "Stop Authentication"
		      echo $BUILD_USER_ID
		      echo $BUILD_USER
                  '''
             }
        }
    }
    /*
    post {
        success {
            mail to: agkpipeline.poc1, subject:"SUCCESS: ${currentBuild.fullDisplayName}", body: "${currentBuild.currentResult}: Job ${env.JOB_NAME} build ${env.BUILD_NUMBER}\n More info at: ${env.BUILD_URL}"
        }
        failure {
            mail to: agkpipeline.poc1, subject:"FAILURE: ${currentBuild.fullDisplayName}", body: "${currentBuild.currentResult}: Job ${env.JOB_NAME} build ${env.BUILD_NUMBER}\n Please review info at: ${env.BUILD_URL}"
        }
    }  
   */ 
  }
}
