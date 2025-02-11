
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;

public class Servertesting{

    /**
     * This is where i test stuff for the server, just ignore-Adam
     * 
     */
    public static void main(String[] args){
    HashMap<LocalDate,LocalTime> map1 = new HashMap<>();  // Date -> Time

    HashMap<HashMap,String> map2 = new HashMap<>(); // Date -> Time -> room,module


    LocalDate date = LocalDate.now();
    //Two lectures collide if lecture A starts before lecture B but ends before lecture B ends
    //lecture A starts after lecture B but ends before lecture B
    //Lecture B starts before lecture A but ends before lecture A
    //Lecture B starts after lecture A but ends befor lecture A
    //Lecture A starts during lecture B and ends during lecture B
    

    LocalTime time = LocalTime.now();

    map1.put(date,time);

    if(LocalTime.of(8,32).isAfter(LocalTime.of(8,30))){
        System.out.println("THIS WORKS");
    }


        try {
            throw new IncorrectActionException();
        }catch(Exception e){
            
        }








    }
}