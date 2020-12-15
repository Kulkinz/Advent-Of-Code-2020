import java.io.File;
import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Vector;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
        
        ArrayList<String> data = new ArrayList<>();

        File file = new File("data.txt");
        Scanner sc = new Scanner(file);

        while (sc.hasNextLine()) {
            data.add(sc.nextLine());
        }
        sc.close();

        String acc = "0 0 0";
        ArrayList<Integer> foundPasses = new ArrayList<>();
        ArrayList<Integer> listofPasses = new ArrayList<>();

        for (int i = 0; i < 128; i++) {
            for (int j = 0; j < 8; j++) {
                int[] pass = {i, j, (i * 8 + j)};

                listofPasses.add(pass[2]);
            }
        }

        for (String string : data) {
            int result = process(string)[2];

            foundPasses.add(result);

            //acc = (acc[2] > result[2]) ? acc : result;
        }

        listofPasses.removeAll(foundPasses);
        
        System.out.println(listofPasses);
        //System.out.println(Arrays.toString(acc));

        // boolean trigger = false;
        
        // for (String string : listofPasses) {
        //     trigger = false;
        //     for (String string2 : foundPasses) {
        //         if (string.equalsIgnoreCase(string2)) {
        //             trigger = true;
        //         }
        //     }
        //     if (!trigger) {
        //         System.out.println(string);
        //     }
        // }

    }

    public static int[] process(String s) {
        String[] boardingPass = s.split("");
        
        int lowRow = 0;
        int highRow = 127;
        int lowSeat = 0;
        int highSeat = 7;

        for (String string : boardingPass) {
            if (string.equalsIgnoreCase("f")) {
                highRow = (int) Math.floor(nextNode(lowRow, highRow));
            } else if (string.equalsIgnoreCase("b")) {
                lowRow = (int) Math.ceil(nextNode(lowRow, highRow));
            }

            if (string.equalsIgnoreCase("l")) {
                highSeat = (int) Math.floor(nextNode(lowSeat, highSeat));
            } else if (string.equalsIgnoreCase("r")) {
                lowSeat = (int) Math.ceil(nextNode(lowSeat, highSeat));
            }
        }

        int seatID = highRow * 8 + highSeat;

        int[] rowSeat = {highRow,highSeat,seatID};

        return rowSeat;

    }

    public static double nextNode(int low, int high) {
        double math = ((high - low) / 2) + low;
        return math;
    }
}
