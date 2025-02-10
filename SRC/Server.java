import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Scanner;

public class Server {
    static HashMap<LocalDateTime,String> schedule = new HashMap<>();
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
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime start_daytime;
            LocalDateTime day;
            while ((message = in.readLine()) != null) { // Read messages until client disconnects

                


             if(message.length() > 0){
                String request = message.substring(0,1);
                String content = message.substring(1);
    
                switch(request){
                    case("A"):
                    // String message = "A" + dateString + start + "/"  + end + "/" + room + "/" + code + "\n";
                    String[] info = content.split("/");
                    date = info[0];
                    String start = info[1];
                    room = info[2];
                    code = info[3];
    
                    System.out.println("Request received to schedule a lecture on " + date + "\n" + "In room: " + room + "\n" + "For module: " + code + "\nTime: " + start);
                    String day1 = date +  " " + start;//Start day and time
                    
                    start_daytime = LocalDateTime.parse(day1, formatter);
                   
                    AttemptSchedule(start_daytime,room,code);
                    break;
                        case("R"):
                            String[] inf = content.split("/");
                            date = inf[0];
                            System.out.println(date);
                            String time = inf[1];
                            System.out.println(time);
                            System.out.println("Request received to remove lecture on " + date + " " + time);
                            String target =  date + " " + time;
                            day = LocalDateTime.parse(target, formatter);
                            removelecture(day);
                            break;
                                case("V"):
                                    System.out.println("Request received to view schedule");
                                    viewSchedule();
                                    break;
                                        case("O"):
                                            System.out.println("Request received to show options");
                                            break;
                            default:
                                System.out.println(message);
                    
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
                    
            public static void AttemptSchedule(LocalDateTime start_daytime,String room, String module){
                /*We must specify a start and end time for our lectures...
                 * WHAT IF WE HAVE SCHEDULING CONFLICTS???
                 * Scheduling conflicts occur if the start or end time of one lecture exists betwen the start and end time of another lecture
                 * We can use the is before and is after methods
                 * To check for conflicts
                 */
            if(schedule.containsKey(start_daytime)){
            System.out.println("There is already a lecture scheduled for this time and date");
        }else{
            String val = room + "/" + module;
            schedule.put(start_daytime,val);
            System.out.println("Lecture has been scheduled for " + start_daytime);
        }
    }

    public static void removelecture(LocalDateTime date){
        if(schedule.containsKey(date)){
            schedule.remove(date);
            System.out.println("Lecture has been successfully removed from schedule");
        }else{
            System.out.println("Schedule does not contain a lecture at the given date and time");
        }
    }
    public static void viewSchedule(){
        System.out.println(schedule);
        //TODO............ FIX
    }
}
