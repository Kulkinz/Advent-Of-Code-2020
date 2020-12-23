import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;

public class App {

    
    public static void main(String[] args) throws Exception {
    
        System.out.println("Hello, World!");

        ArrayList<Integer> cups = new ArrayList<>();

        HashMap<Integer, Entry> map = new HashMap<>();

        
        File file = new File("data.txt");
        Scanner sc = new Scanner(file);

        while (sc.hasNextLine()) {
            cups.add(sc.nextInt());
        }
        sc.close();

        System.out.println(cups);

        // int maxTerm = Collections.max(cups);

        // for (int i = maxTerm + 1; i <= 1000000; i++) {
        //     cups.addLast(i);
        // }

        
        Entry spot = setup(cups);

        Entry iterate = spot;

        int amountHit = 0;
        
        while (iterate.next != null) {
            // System.out.println(iterate.value);
            if (iterate == spot) {
                if (amountHit < 1) {
                    amountHit++;
                } else {
                    break;
                }
            }
            // System.out.println(iterate.value);
            map.put(iterate.value, iterate);
            iterate = iterate.next;

        }

        // System.out.println(iterate.value);

        // System.out.println("End Next");
        
        // spot = setup(cups);
        iterate = spot;

        // System.out.println(iterate.value);

        int max = 1000000;

        // while (iterate.value != max) {
        //     iterate = iterate.next;
        // }

        
        // Entry largest = iterate;

        
        // System.out.println("Largest: " + largest.value);

        
        iterate = spot;

        // System.out.println(iterate.value);

        System.out.println(iterate.value);

        // while (iterate.minusOne != null) {

        //     iterate = iterate.minusOne;
        //     System.out.println(iterate.value);
        // }

        System.out.println("-----------");

        for (int i = 0; i < 10000000; i++) {

            // System.out.println(i);
            
            // int target = iterate.value;

            // System.out.println("Target: " + target);


            Entry current = iterate;
            Entry nextThree = iterate.next;
            Entry fourth = current.next.next.next.next;
            nextThree.next.next.next = null;
            fourth.prev = current;
            current.next = fourth;

            

            ArrayList<Integer> valuesNextThree = new ArrayList<>();
            valuesNextThree.add(nextThree.value);
            valuesNextThree.add(nextThree.next.value);
            valuesNextThree.add(nextThree.next.next.value);

            // System.out.println(valuesNextThree);

            // System.out.println(current.value);
            // System.out.println(current.next.value);
            
            int localAcc = current.value - 1;

            if (localAcc == 0) {
                localAcc = max;
            }

            while (valuesNextThree.contains(localAcc)) {
                localAcc--;

                if (localAcc == 0) {
                    localAcc = max;
                }
            }

            

            Entry lower = map.get(localAcc);

            // System.out.println(localAcc);
            // System.out.println(valuesNextThree.contains(localAcc.value));
            
            // if (localAcc != null) {
            //     while (valuesNextThree.contains(localAcc.value)) {
            //         localAcc = localAcc.minusOne;
    
            //         if (localAcc == null) {
            //             break;
            //         }
            //     }
            // }

            // if (localAcc == null) {
            //     Entry localLargest = largest;
            //     while (valuesNextThree.contains(localLargest.value)) {
            //         localLargest = localLargest.minusOne;
            //     }
            //     // System.out.println(localLargest.value);
            //     lower = localLargest;
            // } else {
            //     lower = localAcc;
            // }
            

            // System.out.println("Destination: " + lower.value);

            // System.out.println(lower.value);

            Entry preserve = lower.next;
            Entry lastNextThree = nextThree.next.next;

            // System.out.println(preserve.value);
            // System.out.println(lastNextThree.value);

            preserve.prev = lastNextThree;
            lastNextThree.next = preserve;
            lower.next = nextThree;

            // Entry startIterate = iterate;

            // iterate = iterate.next;

            // System.out.println("----------");

            // while (iterate.next != null && iterate != startIterate) {
            //     // System.out.println(iterate.value);
            //     iterate = iterate.next;

            // }

            // System.out.println(iterate.value);

            iterate = iterate.next;

            // System.out.println("----------");

            // System.out.println(iterate.value);
        }

        Entry find1 = iterate;

        while (find1.value != 1) {
            find1 = find1.next;
        }

        System.out.println(find1.value);
        System.out.println(find1.next.value);
        System.out.println(find1.next.next.value);

        long acc = 1;

        acc *= find1.next.value;
        acc *= find1.next.next.value;

        // String acc = "";

        // find1 = find1.next;
        // while (find1.value != 1) {
        //     acc += find1.value;
        //     find1 = find1.next;
        // }

        System.out.println(acc);
        
    }


    public static Entry setup(ArrayList<Integer> values) {

        // int max = values.size();
        Entry starting = new Entry(values.remove(0));
        Entry current = starting;
        Entry prev = null;
        Entry next = null;

        for (int i = 0; i < values.size(); i++) {
            next = new Entry(values.get(i));
            current.next = next;
            current.prev = prev;

            current = next;
        }


        Entry additional = new Entry(10);

        current.next = additional;
        additional.prev = current;

        current = additional;


        Entry lower = null;

        for (int i = 1; i <= 10; i++) {

            // System.out.println(i);
            
            Entry iterate = starting;

            while (iterate.value != i) {
                iterate = iterate.next;
            }


            iterate.minusOne = lower;
            lower = iterate;

            // System.out.println("Value: " + lower.value);
        }

        
        for (int i = 11; i <= 1000000; i++) {
            additional = new Entry(i);

            current.next = additional;
            additional.prev = current;
            additional.minusOne = current;

            current = additional;
        }

        System.out.println(current.value);

        Entry last = current;

        // System.out.println(last.value);

        starting.prev = last;
        last.next = starting;


        
        return starting;
    }

    public static class Entry {
        int value;
        Entry minusOne;
        Entry next;
        Entry prev;


        public Entry(int value) {
            this.value = value;
        }
        
    }
}
