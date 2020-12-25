import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class App {

    static HashMap<ArrayList<Integer>, Boolean> grid = new HashMap<>();
    
    public static void main(String[] args) throws Exception {
    
        System.out.println("Hello, World!");

        ArrayList<Integer> data = new ArrayList<>();
        
        File file = new File("data.txt");
        Scanner sc = new Scanner(file);

        while (sc.hasNextLine()) {
            data.add(sc.nextInt());
        }
        sc.close();

        System.out.println(data);

        int card = data.get(0);
        int door = data.get(1);

        System.out.println(card);
        System.out.println(door);

        int subjectNumber = 7;

        long value = 1;
        int loopCard = 0;

        while (value != card) {
            loopCard++;

            value *= subjectNumber;

            value %= 20201227;
        }

        System.out.println(loopCard);
        System.out.println(value);

        value = 1;
        int loopDoor = 0;

        while (value != door) {
            loopDoor++;

            value *= subjectNumber;

            value %= 20201227;
        }

        System.out.println(loopDoor);
        System.out.println(value);

        value = 1;
        subjectNumber = card;

        for (int i = 0; i < loopDoor; i++) {
            value *= subjectNumber;

            value %= 20201227;
        }

        System.out.println(value);
    }
}
