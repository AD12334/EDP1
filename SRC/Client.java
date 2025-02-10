import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.Scanner;



/**
 * ServerApp:The server application will receive 4  types  of  requests ((1).Add a Lecture,(2).Remove a Lecture,(3).Display Schedule, (4). Others) 
 * from client and will process those requests. Server  app  will
 * have a memory‐based data
 * collection(e.g.,ArrayList,HashMap,etc.) that stores one course(e.g.,LM051-2026)schedule by adding/removing 
 * lectures for modules based on the requests from client
 */
public class Client {
    String host;
    int port;
    public static void main(String[] args) throws IOException {

        try {
                Socket socket = new Socket("", 24);
                sendMessage(socket);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    static void sendMessage(Socket socket) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        while (true) {

            System.out.println("1) View Connection information" +
                    "\n2)Send message to server"+
                    "\n3)Exit");


            String input = in.readLine();
            switch (input) {
                case "1":
                    System.out.println("Connected to server " + socket.getLocalAddress() + ":" + socket.getPort());

                    break;
                    case "2":
                        System.out.println("Please enter the message to send");
                        String message = in.readLine();
                        out.println(message);
                        break;
                        case "3":
                            System.out.println("Disconnected from server");
                            socket.close();
                            in.close();
                            out.close();
                            System.exit(0);

            }



        }
    }

    static void handleMessages(Socket socket, BufferedReader in, Scanner scanner) throws IOException {
        String message;
        while ((message = in.readLine()) != null) { // Read messages until client disconnects
            LocalDateTime timeReceived = LocalDateTime.now();
            System.out.println("New message from client received at " + timeReceived);
            System.out.println("Enter 'Y' to view the message or 'N' to ignore:");

            String response = scanner.nextLine();
            if (response.equalsIgnoreCase("y")) {
                System.out.println("Client says: " + message);
            } else {
                System.out.println("Message ignored.");
            }
        }
        System.out.println("Client disconnected.");
    }
}
