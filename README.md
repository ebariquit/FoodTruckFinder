# FoodTruckFinder
FoodTruckFinder is a java desktop application for displaying currently open food trucks in San Francisco, CA. 

The following instructions are for setting up and running the project in a Linux terminal.

## Installation (Linux)
1. Download the java files contained in this project.
2. Download and unzip the following JAR file: http://www.java2s.com/Code/Jar/j/Downloadjsonsimple11jar.htm
3. Compile the java files using the following command:
    
        javac --class-path {relative/path/to/JARfile} *.java     


## Running (Linux)
After compiling, run the program by using the following command:

        java --class-path .:{relative/path/to/JAR} FoodTruckFinder
        