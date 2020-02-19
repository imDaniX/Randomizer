package me.imdanix.randomizer;

import java.io.File;
import java.util.concurrent.ThreadLocalRandom;

public class Main {

    public static void main(String[] args) {
        if(args.length < 1) {
            System.out.println("Set your directory with files to randomize.");
        }
        boolean ok = args.length > 1 && args[1].equalsIgnoreCase("confirm");
        File dir = new File(args[0]);
        char[] charsArr = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        if(dir.isDirectory()) {
            int i = 0;
            ThreadLocalRandom rnd = ThreadLocalRandom.current();
            for(File file : dir.listFiles()) {
                String name = file.getName();
                if(!file.isDirectory() && startsWrong(name)) {
                    System.out.print("(" + i++ + ") " + name);
                    StringBuilder bld = new StringBuilder();
                    for(int j = 0; j <= 6; j++)
                        bld.append(charsArr[rnd.nextInt(charsArr.length)]);
                    name = bld.append(" ").append(name).toString();
                    System.out.println(" | " + name);
                    if(ok) file.renameTo(new File(dir.getAbsolutePath() + File.separator + name));
                }
            }
            if(!ok) {
                System.out.println();
                System.out.println("If everything seems ok add \"confirm\" to randomize files.");
                System.out.println("Like \"java -jar Randomizer.jar " + args[0] + " confirm\".");
            }
        } else System.out.println(args[0] + " is not a directory.");
    }

    private static boolean startsWrong(String str) {
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if((c >= '0' && c <= '9') || (c >= 'a' && c <= 'f')) {
                continue;
            }
            return i < 7 || c != ' ';
        }
        return true;
    }
}
