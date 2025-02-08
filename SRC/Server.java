import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Server {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(24);
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Server is running and waiting for clients...");

            while (true) {
                try (Socket socket = serverSocket.accept();
                     BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

                    System.out.println("Client connected!");
                    handleMessages(socket, in, scanner);
                } catch (IOException e) {
                    System.err.println("Error handling client connection: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Error starting server: " + e.getMessage());
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
}
