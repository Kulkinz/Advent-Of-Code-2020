import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

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

        ArrayList<Entry> instructions = new ArrayList<>();

        for (String string : data) {
            String[] temp = string.split(" ");

            Entry entry = new Entry(temp[0], Integer.parseInt(temp[1]));

            instructions.add(entry);
        }

        System.out.println(data);

        ArrayList<Integer> jmpPos = new ArrayList<>();
        ArrayList<Integer> nopPos = new ArrayList<>();

        for (int i = 0; i < instructions.size(); i++) {
            switch (instructions.get(i).inst) {
                case "jmp":
                    jmpPos.add(i);
                    break;
                
                case "nop":
                    nopPos.add(i);
                    break;

                default:
                    break;
            }
        }

        System.out.println(jmpPos);
        System.out.println(nopPos);

        int acc = 0;

        
        for (Integer integer : jmpPos) {
            ArrayList<Entry> localInstructions = new ArrayList<>();
            Entry localEntry = new Entry("nop", instructions.get(integer).num);
            //Entry localEntry = instructions.get(integer);
            //localEntry.inst = "nop";

            for (int i = 0; i < instructions.size(); i++) {
                
                if (i == integer) {
                    localInstructions.add(localEntry);
                } else {
                    localInstructions.add(instructions.get(i));
                }
            }

            int localAcc = process(localInstructions);

            System.out.println(localAcc);

            acc = (localAcc > acc) ? localAcc : acc;
        }

        for (Integer integer : nopPos) {
            ArrayList<Entry> localInstructions = new ArrayList<>();
            Entry localEntry = new Entry("jmp", instructions.get(integer).num);
            //Entry localEntry = instructions.get(integer);
            //localEntry.inst = "nop";

            for (int i = 0; i < instructions.size(); i++) {
                
                if (i == integer) {
                    localInstructions.add(localEntry);
                } else {
                    localInstructions.add(instructions.get(i));
                }
            }

            int localAcc = process(localInstructions);

            System.out.println(localAcc);

            acc = (localAcc > acc) ? localAcc : acc;
        }

        //System.out.println(process(instructions));

        System.out.println(acc);
    }


    public static int process(ArrayList<Entry> instructions) {
        int pos = 0;
        int acc = 0;

        for (Entry entry : instructions) {
            entry.hasOccurred = false;
        }

        try {
            //System.out.println(instructions.get(pos).hasOccurred);
            while (!instructions.get(pos).hasOccurred) {

                //System.out.println(instructions.get(pos).inst);

                switch (instructions.get(pos).inst) {
                    case "acc":
                        acc += instructions.get(pos).num;
                        instructions.get(pos).hasOccurred = true;
                        pos++;
                        break;
    
                    case "jmp":
                        instructions.get(pos).hasOccurred = true;
                        pos += instructions.get(pos).num;
                        break;
                
                    default:
                        instructions.get(pos).hasOccurred = true;
                        pos++;
                        break;
                }
            }

            return 0;
        } catch (Exception e) {
            return acc;
        }
        
    }

    public static int processSaved(ArrayList<Entry> instructions) {
        int pos = 0;
        int acc = 0;
        while (!instructions.get(pos).hasOccurred) {
                switch (instructions.get(pos).inst) {
                    case "acc":
                        acc += instructions.get(pos).num;
                        instructions.get(pos).hasOccurred = true;
                        pos++;
                        break;
    
                    case "jmp":
                        instructions.get(pos).hasOccurred = true;
                        pos += instructions.get(pos).num;
                        break;
                
                    default:
                    
                        instructions.get(pos).hasOccurred = true;
                        pos++;
                        break;
                }
        }

        return acc;
    }

    public static class Entry {
        String inst;
        int num;

        boolean hasOccurred = false;

        public Entry(String inst, int num) {
            this.inst = inst;
            this.num = num;
        }
    }
}
