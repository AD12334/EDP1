import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
        BufferedReader serverinput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        while (true) {

            System.out.println("1)Schedule a lecture" +
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
                handleMessages(socket,serverinput);
                break;
                case "4":
                message = Option();
                out.println(message);
                handleMessages(socket,serverinput);
                break;
                                                               
                            }
                                                               
                                                               
                                                               
                        }
                    }                        
                    static void handleMessages(Socket socket, BufferedReader in) throws IOException {
        String message;
        while ((message = in.readLine()) != null) { // Read messages until client disconnects
            if(message.equals("END")){
            break;
                
            }     
            System.out.println(message);
        }
        }        
                                                




     public static String addlecture(){
        LocalDate dateString  = null;
        LocalDateTime start = null;
        String room = "",code = "",time = "";
        Scanner sc = new Scanner(System.in);
        
        
        
        try{
        while(dateString == null){
        System.out.println("When would you like to schedule a lecture for (dd-MM-YYYY)");
        try{
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        
        LocalDate start_daytime = LocalDate.parse(sc.nextLine(), formatter);
        dateString = start_daytime;
        }catch(DateTimeParseException e){
        System.out.println("Please enter the date in the format (DD-MM-YYYY)");
        }catch(Exception e){
            System.out.println("Something went wrong");
        }
    }
    while(start == null){
    try{
        
        System.out.println("What time will the lecture start (HH:mm)");//For sanity all lectures are one hour long
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        time = sc.nextLine();
        String info  =  dateString + " " + time;
        System.out.println(info);
        LocalDateTime start_daytime = LocalDateTime.parse(info, formatter);
        start = start_daytime;
        
        }catch(DateTimeParseException e){
        System.out.println("Please enter the time in the format HH:mm");
        }catch(Exception e){
            System.out.println("Something went wrong");

        }
    }
        while(room.equals("")){
        System.out.println("What room would you like to book");
        room = sc.nextLine();
        }
        while(code.equals("")){
            System.out.println("What is the module code");
            code = sc.nextLine();
        }
        
        
     }catch (Exception e){
        System.out.println("Something went wrong");
     }
        String message = "A" + dateString + "/" + time + "/" + room + "/" + code + "\n";
        System.out.println(message);
        return message;
        //Im thinking that for adding a lecture we could use A as a flag for add
        //Then the dateString could act as key in our server hashmap
        //Then the room and module code could be the value in our hashmap
        
    }
    public static String removelecture(){
        Scanner sc = new Scanner(System.in);
        System.out.println("When would you like to remove a lecture (yyyy-MM-dd)");
        String targetDate = sc.nextLine();
        System.out.println("What time");
        String time = sc.nextLine();
        String message = "R" + targetDate + "/" + time + "\n";
        return message;
    }
    public static String viewSchedule(){
        String message = "V\n";
        
        return message;  
    }
    public static String Option(){
        String message = "O\n";
        
        return message;
    }
}

