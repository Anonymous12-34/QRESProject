**QRES System Setup Steps**

1-Install apache tomcat Server

2- Configure it in Netbeans (https://www.youtube.com/watch?v=tz5LU8Wyo8M)

3- Create Web services project in Netbeans and test everything is working. (https://www.youtube.com/watch?v=saK-t5hStvA)

4- Install Tor Services and check working perfectly. (Install TOR services==> https://www.torproject.org/docs/tor-doc-osx.html.en)

5- Checking tor services by typing the following commands
  **$ sudo service tor start 
  $ sudo systemctl start tor.service**

6- Configure Tomcat and Tor services. (https://www.torproject.org/docs/tor-hidden-service.html.en)

7- On Tomcat default install, edit META-INF/context.xml to allow connections only from localhost:
           <Context>
                 <Valve className="org.apache.catalina.valves.RemoteAddrValve"
                allow="0:0:0:0:0:0:0:1,127\.0\.0\.1" />
       </Context>

8- On Tor default install, edit torrc file to provide access to your local service through the Tor network, for example:
      HiddenServiceDir /Library/Tor/var/lib/tor/hidden_service/
      HiddenServicePort 80 127.0.0.1:8080
     Get the address stored on the hidden_service; in our system the address was **7ludtxdcobw5l2ut.onion**

9- Open Project from the Netbeans by giving the location of CloudEncryption (the source file).

10- Create an account on amazonaws and create 2 tables in amazon dynamodb. One for storing the encrypted tokens and the other for saving
the tokens matching the keywords. You can create more tables according to the number of CSPs or store all CSPs data in one table, each CSP with different key.

11- Open Tor browser and type 
  **7ludtxdcobw5l2ut.onion/CloudEncryption/message.jsp**

12- Write the required token (by this you already sent an encrypted token as shown in figure encrypted token).

13- Open Tor browser and type.
**7ludtxdcobw5l2ut.onion/CloudEncryption/ keyword.jsp**

14- Run the keyword.jsp file and find search keyword (your keyword is encrypted and in process of searching for the matching token)

15- You can check AWS dynamoDB tables for update by amazon aws login.
