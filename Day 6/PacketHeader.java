/* Advent of Code 2022
 * Day 6 Part 1
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class PacketHeader {
    private final static int MARKER_LENGTH=4;
    public static void main(String[] args) throws Exception
    {

        FileReader file = new FileReader("Day 6\\input.txt");
        BufferedReader input = new BufferedReader(file);

        LimitedQueue<Character> currentlyChecking=new LimitedQueue<Character>(MARKER_LENGTH);

        int in;
        char character;
        int marker=1;
        while((in=input.read())!=-1)
        {
            character=(char)in;
            currentlyChecking.add(character);
            Set<Character> uniqueCharacters=new TreeSet<Character>(currentlyChecking);
            if (uniqueCharacters.size()==MARKER_LENGTH)
            {
                break;
            }
            marker++;

        }
        input.close();

        System.out.println("The first start-of-packet marker is detected after " + marker + " characters");

    }
    
}
