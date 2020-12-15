import java.io.File;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Vector;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");

        Vector<String> data = new Vector<>();

        File file = new File("data.txt");
        Scanner sc = new Scanner(file);

        int[] pos = {0,0};

        while (sc.hasNextLine()) {
            data.add(sc.nextLine());
        }
        sc.close();

        int length = data.get(0).length() - 1;
        int height = data.size() - 1;

        int acc1 = 0;
        int acc2 = 0;
        int acc3 = 0;
        int acc4 = 0;
        int acc5 = 0;

        //System.out.println(data.get(2).charAt(6));

        pos[0] = 0;
        pos[1] = 0;
        while (pos[1] <= height) {

            System.out.println(Arrays.toString(pos) + " - " + data.get(pos[1]).charAt(pos[0]));
            if (data.get(pos[1]).charAt(pos[0]) == '#') {
                acc1++;
            }

            pos[0] += 1;
            pos[1] += 1;

            if (pos[0] > length) {
                pos[0] -= length + 1;
            }

        }

        pos[0] = 0;
        pos[1] = 0;
        while (pos[1] <= height) {

            System.out.println(Arrays.toString(pos) + " - " + data.get(pos[1]).charAt(pos[0]));
            if (data.get(pos[1]).charAt(pos[0]) == '#') {
                acc2++;
            }

            pos[0] += 3;
            pos[1] += 1;

            if (pos[0] > length) {
                pos[0] -= length + 1;
            }

        }

        pos[0] = 0;
        pos[1] = 0;
        while (pos[1] <= height) {

            System.out.println(Arrays.toString(pos) + " - " + data.get(pos[1]).charAt(pos[0]));
            if (data.get(pos[1]).charAt(pos[0]) == '#') {
                acc3++;
            }

            pos[0] += 5;
            pos[1] += 1;

            if (pos[0] > length) {
                pos[0] -= length + 1;
            }

        }

        pos[0] = 0;
        pos[1] = 0;
        while (pos[1] <= height) {

            System.out.println(Arrays.toString(pos) + " - " + data.get(pos[1]).charAt(pos[0]));
            if (data.get(pos[1]).charAt(pos[0]) == '#') {
                acc4++;
            }

            pos[0] += 7;
            pos[1] += 1;

            if (pos[0] > length) {
                pos[0] -= length + 1;
            }

        }

        pos[0] = 0;
        pos[1] = 0;
        while (pos[1] <= height) {

            System.out.println(Arrays.toString(pos) + " - " + data.get(pos[1]).charAt(pos[0]));
            if (data.get(pos[1]).charAt(pos[0]) == '#') {
                acc5++;
            }

            pos[0] += 1;
            pos[1] += 2;

            if (pos[0] > length) {
                pos[0] -= length + 1;
            }

        }

        System.out.println(acc1);
        System.out.println(acc2);
        System.out.println(acc3);
        System.out.println(acc4);
        System.out.println(acc5);
        
        System.out.println(acc1 * acc2 * acc3 * acc4 * acc5);
    }
}
