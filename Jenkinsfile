#!groovy
// Loads ALM Pipeline library
// see https://gitlab.alm.gsnetcloud.corp/serenity-alm/pipeline-library/wikis/home
@Library('global-alm-pipeline-library') _
def artifactUrl
def urlConfig= "-DCONFIG_SGS_SERVICE.URI=http://config-sgs-serenity-alm-dev.appls.boaw.paas.gsnetcloud.corp"
pipeline {
    agent {
        node {
            // set maven slave
            label 'maven'
        }
    }
    options {
        buildDiscarder(logRotator(numToKeepStr: '10', artifactNumToKeepStr: '10'))
    }
    stages {
        stage ('Build') {
            steps {
                // clones the repository
                almGitClone()
                // executes mvn clean compile goal
                almMaven(goal: 'clean compile')
                // stashes application.yml file because it will be used by ose3-deploy agent
                stash includes: 'application.yml', name: 'applicationyml'
            }
        }
         stage("Upload to Nexus") {
            steps {
                script {
                    // uploads the artifacts to nexus and
                    // saves project's root uploaded artifact url
                    artifactUrl = almMavenUpload()
                }
            }
        }  
/*
         stage("Deploy") {
            agent {
                // executes this stage in openshift agent
                label 'ose3-deploy'
            }
            steps {
                //restores application.yml file
                unstash('applicationyml')
                //executes the deploy
                almOpenshiftDeployYaml(
                        configurationFilePath: "application.yml",
                        environment: "development",
                        params: [ose3TemplateParams: [ARTIFACT_URL: artifactUrl]])
            }
        }
*/
         stage("JMeter") {          
                  steps{
            // Descargamos, descomprimimos apache-jmeter y lanzamos las pruebas de rendimiento
                  sh """  
                  export http_proxy=http://proxyapps.gsnet.corp:80
                  wget -c http://apache.rediris.es//jmeter/binaries/apache-jmeter-5.0.tgz
                  tar -xvzf apache-jmeter-5.0.tgz
                  \\${env.WORKSPACE}\\/apache-jmeter-5.0/bin/jmeter.sh -Jjmeter.save.saveservice.output_format=xml -n -t \\${env.WORKSPACE}\\/src/test/resources/APM-AUTH.jmx -l \\${env.WORKSPACE}\\/properties/output/APM-AUTH.xml"""
            // Archivamos los ficheros de salida en la ruta properties/output/*
               archiveArtifacts 'properties/output/APM-AUTH.xml*' 
            }
        }       
         stage("PerformancePlugin") {
         // Tratamos el xml de salida generado en la stage de Jmeter para crear informe de rendimiento en Jenkins.               
                  steps{
                  perfReport 'properties/output/APM-AUTH.xml' 
            }
        }
 /*
         stage('caapmplugin') {
                  steps{     
            //Añadimos ruta absoluta al WS en output.directory(config.properties)y ruta absoluta al fichero APM-AUTH.xml en jmeter.outputFile(loadRunner.properties)
                  sh """
                  sed -i "\$ a  output.directory=\\${env.WORKSPACE}\\/properties/output" properties/config.properties"""
            //    sed -i "\$ a  jmeter.outputFile=\\${env.WORKSPACE}\\/properties/output/APM-AUTH.xml" properties/loadRunner.properties
            // Añadimos las rutas absolutas de los ficheros de propiedades que necesita el plugin de APM          
                  caapmplugin dataSourceProperties: "${env.WORKSPACE}/properties/apm.properties", 
                  strategyProperties: "${env.WORKSPACE}/properties/strategies.properties", 
                  loadSourceProperties: "${env.WORKSPACE}/properties/loadRunner.properties",
                  configurationProperties: "${env.WORKSPACE}/properties/config.properties"
            // Archivamos los ficheros de salida de la ruta properties/output
                  archiveArtifacts 'properties/output/output.json*'
            // Archivamos el comparison-runner.log generado por el plugin APM
                  archiveArtifacts '${BUILD_NUMBER}/comparison-runner.log'
            // Archivamos el contenido  con las salidas de las estrategias en HTML
                  archiveArtifacts '${BUILD_NUMBER}/chartOutput/output/*'
                  archiveArtifacts '${BUILD_NUMBER}/chartOutput/amcharts/*'
			}
        }
*/
    }
 }