import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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

        System.out.println(data);

        HashMap<Long, Integer> mem = new HashMap<>();
        ArrayList<Long> locations = new ArrayList<>();

        char[] mask = setMask(data.get(0));


        for (String string : data) {

            if (string.contains("mask")) {
                mask = setMask(string);
            } else {
                String binary = setBinary(string, mask);
                int value = Integer.parseInt(string.split(" = ")[1]);

                int count = binary.replaceAll("0", "").replaceAll("1", "").length();

                ArrayList<String> permutations = createPermutations(count);

                for (String perm : permutations) {
                    String newBinary = buildNewBinary(binary, perm);
                    // System.out.println(newBinary);
                    long spot = Long.parseLong(newBinary, 2);
                                   
                    mem.put(spot, value);
                    locations.add(spot);
                }
            }
        }

        List<Long> noDups = locations.stream().distinct().collect(Collectors.toList());

        long acc = 0;

        for (Long long1 : noDups) {
            acc += mem.get(long1);
        }

        System.out.println(acc);
    }

    public static String buildNewBinary(String binary, String variant) {

        String localBinary = binary;
        
        StringBuilder sBuilder = new StringBuilder(localBinary);
        
        for (char x : variant.toCharArray()) {
            int index = localBinary.indexOf("X");
            sBuilder.setCharAt(index, x);
            localBinary = sBuilder.toString();
        }

        // System.out.println(localBinary);

        return localBinary;

    }

    public static ArrayList<String> createPermutations(int count) {
        
        // int permutations = factorial(count);

        int max = (int) Math.pow(2, count);

        ArrayList<String> permutations = new ArrayList<>();


        for (int i = 0; i < max; i++) {
            String binary = Integer.toBinaryString(i);

            if (count - binary.length() > 0) {
                binary = String.format("%0" + (count - binary.length()) + "d", 0) + binary;
            }


            permutations.add(binary);
        }

        return permutations;
    }

    public static char[] setMask(String data) {
        String mask = data.split("mask = ")[1];
        
        char[] maskArray = mask.toCharArray();

        return maskArray;
    }

    public static String setBinary(String data, char[] mask) {

        int value = Integer.parseInt(data.split("mem\\[")[1].split("]")[0]);

        String binary = Integer.toBinaryString(value);
        
        int length = mask.length;

        String s = String.format("%0" + (length - binary.length()) + "d", 0) + binary;

        // System.out.println(s);

        StringBuilder sBuilder = new StringBuilder(s);

        for (int i = 0; i < mask.length; i++) {
            if (mask[i] != '0') {

                sBuilder.setCharAt(i, mask[i]);

            }
        }

        s = sBuilder.toString();
        // System.out.println(s);

        return(s);
    }
}
