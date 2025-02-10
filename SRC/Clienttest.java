
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;


/**
 * I'm just using this for testing random stuff out, ignore pls -Adam 2025
 * */
 
 
public class Clienttest {
    
    public static void main(String[] args) {

        addlecture();
                
        
            }
        
        
            public static void addlecture(){
        Scanner sc = new Scanner(System.in);
        System.out.println("When would you like to schedule a lecture for (dd-MM-YYYY)");
        String dateString = sc.nextLine();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate = LocalDate.parse(dateString, formatter);
        System.out.println("What room would you like to book");
        String room = sc.nextLine();
        
    }
}
