node {
    // Get the maven tool.
    // ** NOTE: This 'M3' maven tool must be configured
    // **       in the Jenkins global configuration.
    def mvnHome = tool 'M3'
    
    // Mark the code checkout 'stage'....
    stage 'Checkout'
    // Get some code from a GitHub repository
    checkout scm    
   
    // Mark the code build 'stage'....
    stage 'Build EELF'
    // Run the maven build
    //sh for unix bat for windows
    bat "${mvnHome}/bin/mvn -f EELF/pom.xml clean install"
    
    // Mark the code build 'stage'....
    stage 'Build EELF-Maven-Plugin'
    // Run the maven build
    //sh for unix bat for windows
    bat "${mvnHome}/bin/mvn -f EELF-Maven-Plugin/pom.xml clean package"
    
    // Mark the code build 'stage'....
    stage 'Build EELF-Samples'
    // Run the maven build
    //sh for unix bat for windows
    bat "${mvnHome}/bin/mvn -f EELF-Samples/pom.xml clean package"
    
}