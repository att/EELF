
INTRODUCTION
--------------
EELF stands for Event and Error Logging Framework. It helps to implement an effective logging strategy and can 
log messages using string or using a message key from resource bundle(s) and also supports I18N (internalization).
EELF provides a maven plugin to Generate wiki page. EELF provides a wrapper to SLF4j, which internally uses 
logback framework.


REQUIREMENTS
---------------
Java 7 and above


CONFIGURATION
----------------
This project contains the main library.. Any application using EELF has to include eelf-core as the dependency in pom.xml.
< dependency > 
< groupId > </ groupId > 
< artifactId > eelf-core</ artifactId > 
< version >${eelf.version}</ version > 
</ dependency >

This project contains the maven plugin to generate wiki page. The wiki page will contain the list of error codes along with its description and resolution. .
< plugin > 
    < groupId >  </ groupId > 
    < artifactId >eelf-maven-plugin</ artifactId > 
    < version >${eelf.maven.plugin.version}</ version > 
    < executions > 
     < execution > 
     < phase >install</ phase > 
     < goals > 
            < goal >WikiMsgGenerator</ goal > 
         </ goals > 
    </ execution >  
  </ executions > 
  < dependencies > 
     <!-- We need to include the dependency of the project so that its include in classpath when running plugin --> 
     < dependency > 
        < groupId ></ groupId > 
        < artifactId > eelf-samples</ artifactId > 
        < version >${project.version}</ version > 
    </ dependency > 
  </ dependencies > 
  < configuration > < outputDirectory >target/messages</ outputDirectory > 
    < outputFile >messages.html</ outputFile > 
     < wiki > 
         < title >Messages and Codes</ title > 
 < page >APP Messages and Codes</ page > 
 < principal >user</ principal > 
 < credentials >password</ credentials > 
 < url ></ url > 
 </ wiki > 
 < resources > 
 < resource > 
 <!-- Provide enum class created for your application --> 
 < messageClass >com.att.eelf.sample.ApplicationMsgs</ messageClass > 
 < header ><![CDATA[<p> <ac:macro ac:name="toc"/> </p>
<p>
<ac:macro ac:name="anchor"> <ac:default-parameter>Application Messages</ac:default-parameter> </ac:macro> </p> <h2>Application Messages</h2>]]></ header > 
 </ resource > 
 </ resources > 
 </ configuration > 
 </ plugin >


RECOMMENDED MODULES
----------------------
EELF MavenPlugin, EELF Core, EELF Samples


MAINTAINERS
-------------
AT&T EELF Development Team
