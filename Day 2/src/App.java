import java.io.File;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Vector;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");

        Vector<String> data = new Vector<>();
        int acc = 0;

        File file = new File("data.txt");
        Scanner sc = new Scanner(file);

        while (sc.hasNextLine()) {
            data.add(sc.nextLine());
        }
        sc.close();

        for (String string : data) {
            String[] password = string.split("\\s+");
            System.out.println(Arrays.toString(password));
            int low = Integer.parseInt(password[0].split("-")[0]);
            int high = Integer.parseInt(password[0].split("-")[1]);
            char letter = password[1].substring(0,1).charAt(0);
            //int count = password[2].length() - password[2].replace(letter, "").length();
            String word = password[2];
            char char1 = ' ';
            char char2 = ' ';


            //if (low <= count && count <= high) {
            //    acc++;
            //}
            
            if (low <= word.length()) {
                char1 = word.charAt(low-1);
            }

            if (high <= word.length()) {
                char2 = word.charAt(high-1);
            }
            
            System.out.println(low + "" + char1);
            System.out.println(high + "" + char2);

            if (char1 == letter) {
                if (char2 != letter) {
                    acc++;
                }
            } else {
                if (char2 == letter) {
                    acc++;
                }
            }
            
        }
        
        System.out.println(acc);
    }
}
