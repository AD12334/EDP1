import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Scanner;

public class Server {
    static HashMap<LocalDate,String> schedule = new HashMap<>();
        public static void main(String[] args) {
            try (ServerSocket serverSocket = new ServerSocket(1054);
                 Scanner scanner = new Scanner(System.in)) {
    
                System.out.println("Server is running and waiting for clients...");
    
                while (true) {
                    try (Socket socket = serverSocket.accept();
                         BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
    
                        System.out.println("Client connected!");
                        handleMessages(socket, in, scanner); // Having this here is causing an error
                        //A message is processed upon reception, however once processed if no new message is available an error is thrown
                        //Be careful if edititng here -Adam 2025 library floor 2 
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
            String date;
            String room;
            String code;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate day;
            while ((message = in.readLine()) != null) { // Read messages until client disconnects


             if(message.length() > 1){
                String request = message.substring(0,1);
                String content = message.substring(1);
    
                switch(request){
                    case("A"):
                    //String message = "A" + dateString + "-" + room + "-" + code + "\n";
                    String[] info = content.split("/");
                    date = info[0];
                    room = info[1];
                    code = info[2];
    
                    System.out.println("Request received to schedule a lecture on " + date + "\n" + "In room: " + room + "\n" + "For module: " + code);
                    
                    day = LocalDate.parse(date, formatter);
                    AttemptSchedule(day,room,code);
                                    break;
                                    case("R"):
                                    date = content;
                                    System.out.println("Request received to remove lecture on " + date);
                                    day = LocalDate.parse(date, formatter);
                                    removelecture(day);
                                    break;
                                    case("V"):
                                    System.out.println("Request received to view schedule");
                                    viewSchedule();
                                    break;
                                    case("O"):
                                    System.out.println("Request received to show options");
                                    break;
                    
                                }
                            }
                            
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
                    
            public static void AttemptSchedule(LocalDate date,String room, String module){
            if(schedule.containsKey(date)){
            System.out.println("There is already a lecture scheduled for this time and date");
        }else{
            String val = room + "/" + module;
            schedule.put(date,val);
            System.out.println("Lecture has been scheduled for " + date);
        }
    }

    public static void removelecture(LocalDate date){
        if(schedule.containsKey(date)){
            schedule.remove(date);
            System.out.println("Lecture has been successfully removed from schedule");
        }else{
            System.out.println("Schedule does not contain a lecture at the given date and time");
        }
    }
    public static void viewSchedule(){
        System.out.println(schedule);
    }
}
