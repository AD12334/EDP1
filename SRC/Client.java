import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
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
            InetAddress ip = InetAddress.getLocalHost();
                Socket socket = new Socket(ip, 1054);
                Menu(socket);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    static void Menu(Socket socket) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        while (true) {

            System.out.println("1) Schedule a lecture" +
                    "\n2)Remove a lecture"+
                    "\n3)Display schedule" + "\n4)Others\n");


            String input = in.readLine();
            switch (input) {
                case "1":
                String message = addlecture(); //This is the output we wish to send to our server
                out.println(message);
                break;
                case "2":
                message = removelecture();
                out.println(message);
                break;
                case "3":
                message = viewSchedule();
                out.println(message);
                break;
                case "4":
                message = Option();
                out.println(message);
                break;
                                               
            }
                                               
                                               
                                               
        }
    }                        
    void handleMessages(Socket socket, BufferedReader in, Scanner scanner) throws IOException {
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
                                                




     public static String addlecture(){
        Scanner sc = new Scanner(System.in);
        System.out.println("When would you like to schedule a lecture for (dd-MM-YYYY)");
        String dateString = sc.nextLine();
        System.out.println("What room would you like to book");
        String room = sc.nextLine();
        System.out.println("What is the module code");
        String code = sc.nextLine();

        String message = "A" + dateString + "/" + room + "/" + code + "\n";
        return message;
        //Im thinking that for adding a lecture we could use A as a flag for add
        //Then the dateString could act as key in our server hashmap
        //Then the room and module code could be the value in our hashmap
        
    }
    public static String removelecture(){
        Scanner sc = new Scanner(System.in);
        System.out.println("When would you like to remove a lecture (dd-MM-YYYY)");
        String targetDate = sc.nextLine();
        String message = "R" + targetDate;
        return message;
    }
    public static String viewSchedule(){
        String message = "V";
        return message;  
    }
    public static String Option(){
        String message = "O";
        return message;
    }
}

