# ChatR
This is a console-based chat application that enables two users on the same network to communicate in real time. It uses socket programming to establish a connection between users and leverages multithreading to handle simultaneous sending and receiving of messages seamlessly.



## How to Run


1. Compile both files using:
   ```
   javac ChatServer.java ChatClient.java
   ```
2. Run the server first:
   ```
   java ChatServer
   ```
3. Run the client next:
   ```
   java ChatClient
   ```
4. Start chatting by typing messages into the console.

## Notes

- The server listens on port 12345. You can change the port if needed.
- Both server and client run indefinitely until terminated.
- Ensure that the server and client are running on the same machine/network. Use the server's IP address if they are on different machines.
