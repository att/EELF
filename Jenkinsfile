node {
    // Get the maven tool.
    // ** NOTE: This 'M3' maven tool must be configured
    // **       in the Jenkins global configuration.
    def mvnHome = tool 'M3'
    
    // ** Provide environment variable MAVEN_GOALS with 
    // **   goals to be run (i.e. clean deploy)
    
    // ** Provide environment variable COMPONENT to build
    // **   (i.e. EELF, EELF-Maven-Plugin or EELF-Samples)
    
    // Mark the code checkout 'stage'....
    stage 'Checkout'
    // Get some code from a GitHub repository
    checkout scm    
   
    // Mark the code build 'stage'....
    stage 'Build EELF'
    // Run the maven build
    //sh for unix bat for windows
    bat "${mvnHome}/bin/mvn -f ${COMPONENT}/pom.xml ${MAVEN_GOALS}"
    
}