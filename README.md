# Java Socket Programming Project

## Technical Documentation

The project is seperated in two main packages : the client and the server.

**Server-side**

- The server starts on port 45000, interface "wlan0"
- The server always listen to connections, while the server socket remains open
- The server manages multi-threading with our "ClientHandler" Class
- The different clients are identified by an IP Address
- The server is able to serialise into a JSON file in order to save all the connected clients 
- The server save logs for the connections and disconnections, and also severe logs when crashing

**Client-side**

- The client can connect from any operating system
- The client exchange information about the connection and his name with the server in our "ClientForThread" Class
- A client is defined by an IP Address, a name, and a boolean status if he is connected
- The "AudioPlayer" Class allows the client to listen to music, using an AudioInputStream, and using the clip Class
- The client can interact with songs and different options, such as playing/pausing, move forward/backward or increase/decrease volume


## User Guide 

**Create your own .jar file**

You can clone the project from the Git repository. Then you need too open the root directory in IntelliJ to see the two modules (Client and Server). Go to the Maven panel (on the right) and click to Lifecycle/package to build the .jar. 

The file will be created in the target directory (Client/target and Server/target) you need to do this process for both
module. If you want to change the setting you can go to each pom.xml file and adapte as you want (not below Compiler 7).


**Use the .jar provided in the Git repository** 

You have two .jar (one for the Client and one for Server). The server is made to run on a Linux machine, the client can be run from any OS you want. On the linux machine where you put the server you need to create a directory "/VSfy" and an other one "/VSfy/MyMusic". The second directory is the one where you will add your song. The server will create a JSON file on the source directory ("VSfy") with all client stored inside. 

Your Linux need to have a Java JDK greater or equal to 11 to be able to run it (if it's not the case run the following command "sudo apt install default-jdk") 

Now the server is ready to run, you can put the .jar file where you want on the linux system and run "java -jar FILE.jar" the server will start. You can start a client from whatever devices you want.

The application first page shows you the list of the clients existing on the server (identified by an IP Address). A refresh button is at the bottom of the page to refresh state of connexion with other users. The music button brings you to the page where all song inside /MyMusic are stored. You can click on them to play the song. The server create a LOG file on the "/VSfy" directory, a new file is created each month.
