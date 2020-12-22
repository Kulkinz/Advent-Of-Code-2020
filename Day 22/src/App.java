import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class App {
    
    public static void main(String[] args) throws Exception {
    
        System.out.println("Hello, World!");

        ArrayList<Integer> player1 = new ArrayList<>();
        ArrayList<Integer> player2 = new ArrayList<>();

        
        File file = new File("player1.txt");
        Scanner sc = new Scanner(file);

        while (sc.hasNextLine()) {
            player1.add(sc.nextInt());
        }
        sc.close();

        file = new File("player2.txt");
        sc = new Scanner(file);

        while (sc.hasNextLine()) {
            player2.add(sc.nextInt());
        }
        sc.close();

        System.out.println(player1);
        System.out.println(player2);

        ArrayList<Integer> winner = primary(player1, player2);
        
        long acc = 0;

        System.out.println(winner);

        for (int i = 0; i < winner.size(); i++) {
            int multiplier = winner.size() - i;

            acc += winner.get(i) * multiplier;
        }

        System.out.println(acc);
    }

    // static int abc = 0;
    // static int check = 2;

    public static ArrayList<Integer> primary(ArrayList<Integer> p1, ArrayList<Integer> p2) {

        while (!p1.isEmpty() && !p2.isEmpty()) {

            // if (abc == check) {
            //     System.out.println(p1);
            //     System.out.println(p1.size());
            //     System.out.println(p2);
            //     System.out.println(p2.size());
            // }
            // abc++;

            int player1card = p1.remove(0);
            int player2card = p2.remove(0);

            // System.out.println("Player1: " + player1card + " Size: " + p1.size());
            // System.out.println("Player2: " + player2card + " Size: " + p2.size());

            int x = -1;

            if (p1.size() >= player1card && p2.size() >= player2card) {

                // System.out.println("Begin Recursion");
                x = secondary(new ArrayList<Integer>(p1.subList(0, player1card)), 
                new ArrayList<Integer>(p2.subList(0, player2card)));
            }

            if (x == -1) {
                if (player1card > player2card) {
                    p1.add(player1card);
                    p1.add(player2card);
                } else {
                    p2.add(player2card);
                    p2.add(player1card);
                }
            } else {
                if (x == 1) {
                    p1.add(player1card);
                    p1.add(player2card);
                } else {
                    p2.add(player2card);
                    p2.add(player1card);
                }
            }
        }

        if (p1.isEmpty()) {
            return p2;
        } else {
            return p1;
        }
    }

    public static int secondary(ArrayList<Integer> p1, ArrayList<Integer> p2) {
        
        ArrayList<ArrayList<ArrayList<Integer>>> combinations = new ArrayList<>();

        while (!p1.isEmpty() && !p2.isEmpty()) {

            ArrayList<ArrayList<Integer>> combination = new ArrayList<>();

            // if (abc == check) {
            //     System.out.println(p1);
            //     System.out.println(p2);
            // }
            // abc++;

            combination.add((ArrayList<Integer>) p1.clone());
            combination.add((ArrayList<Integer>) p2.clone());

            if (combinations.contains(combination)) {

                // System.out.println(combinations.get(combinations.indexOf(combination)).get(0));
                // System.out.println(combination.get(0));
                // System.out.println(combinations.get(combinations.indexOf(combination)).get(1));
                // System.out.println(combination.get(1));
                return 1;
            } else {
                combinations.add(combination);
            }

            int player1card = p1.remove(0);
            int player2card = p2.remove(0);

            // System.out.println("Player1: " + player1card + " Size: " + p1.size());
            // System.out.println("Player2: " + player2card + " Size: " + p2.size());

            int x = -1;


            if (p1.size() >= player1card && p2.size() >= player2card) {
                // System.out.println("Begin Recursion");
                x = secondary(new ArrayList<Integer>(p1.subList(0, player1card)), 
                new ArrayList<Integer>(p2.subList(0, player2card)));
            }

            if (x == -1) {
                if (player1card > player2card) {
                    p1.add(player1card);
                    p1.add(player2card);
                } else {
                    p2.add(player2card);
                    p2.add(player1card);
                }
            } else {
                if (x == 1) {
                    p1.add(player1card);
                    p1.add(player2card);
                } else {
                    p2.add(player2card);
                    p2.add(player1card);
                }
            }

        }

        if (p1.isEmpty()) {
            return 2;
        } else {
            return 1;
        }

    }
}
