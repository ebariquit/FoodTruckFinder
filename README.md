# FoodTruckFinder
FoodTruckFinder is a java desktop application for displaying currently open food trucks in San Francisco, CA. 

## Installation (Linux)
1. Download the java files contained in this project (FoodTruck.java and FoodTruckFinder.java, specifically).
2. Download and unzip the following JAR file: http://www.java2s.com/Code/Jar/j/Downloadjsonsimple11jar.htm
3. Compile the java files using the following command
    
        javac --class-path {relative/path/to/JARfile} *.java     
    
    For example, my folder hierarchy is as follows
        
        Documents
            My Project
                FoodTruck.java
                FoodTruckFinder.java
        Downloads
            json-simple-1.1.jar
            
    Then I compile my files using the following command:
       
        javac --class-path ../../Downloads/json-simple-1.1.jar *.java

4. After compiling, run the program by using the following command

        java --class-path .:{relative/path/to/JAR} FoodTruckFinder
        
   FoodTruckFinder needs two resources: FoodTruck and the JAR.
   The arguments we pass to "--class-path" specify where to find these resources. 