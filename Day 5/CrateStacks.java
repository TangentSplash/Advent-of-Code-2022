/* Advent of Code 2022
 * Day 5 Part 1
 */

import java.io.File;
//import java.nio.channels.ReadPendingException;
import java.util.*;

public class CrateStacks {
    private final static int BOX_CHAR_WIDTH=3;
    private int numberOfStacks;
    private List<char[]> InputArangment;

    private CrateStack[] Stacks;

    public CrateStacks() throws Exception
    {
        File file = new File("Day 5\\input.txt");
        Scanner input = new Scanner(file);

        String stacksString;

        InputArangment=new ArrayList<>();

        stacksString=input.nextLine();
        int length=stacksString.length();
        numberOfStacks=Math.floorDiv(length-BOX_CHAR_WIDTH,BOX_CHAR_WIDTH+1)+1;

        while (!stacksString.isEmpty())
        {
            interpretStacks(stacksString);
            stacksString=input.nextLine();
        } 
        int stackHeight=InputArangment.size()-1;
        Stacks=new CrateStack[numberOfStacks]; 

        for (int stack=0;stack<numberOfStacks;stack++)
        {
            Stacks[stack]=new CrateStack();
            for (int layer=stackHeight-1;layer>=0;layer--)
            {
                Stacks[stack].add(InputArangment.get(layer)[stack]);
            }
        }
        interpretInstructions(input);
        input.close();

        String finalPosition=getFinalPosition();
        System.out.println("The final postion of the boxes is " +finalPosition);        
    }

    private void interpretStacks(String input)
    {
        char[] characters=input.toCharArray();

        char[] boxChars=new char[numberOfStacks];

        for (int i=0;i<numberOfStacks;i++)
        { 
            boxChars[i]=characters[(i*4)+1];
        }
        InputArangment.add(boxChars);
    }

    private void interpretInstructions(Scanner input)
    {
        while (input.hasNext())
        {
            input.next();               //move
            int number=input.nextInt();
            input.next();               //from
            int from=input.nextInt()-1;
            input.next();               //to
            int to=input.nextInt()-1;

            Stacks[to].addAll(Stacks[from].pop(number));
        }
    }

    private String getFinalPosition()
    {
        String finalPosition="";
        for (int i=0;i<numberOfStacks;i++)
        {
            finalPosition+=Stacks[i].getTopChar();
        }
        return finalPosition;
    }
    
}
