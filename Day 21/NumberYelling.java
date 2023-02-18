import java.io.File;
import java.util.*;

/* Advent of Code 2022
* Day 21 Part 2
*/

public class NumberYelling 
{
    private Map<String,ShoutingMonkey> monkeys;
    public static final String START="root";
    public static final String HUMAN="humn";
    private ShoutingMonkey rootMonkey;

    public NumberYelling () throws Exception
    {
        File inputFile = new File("Day 21/input.txt");
        Scanner input = new Scanner(inputFile);

        monkeys=new HashMap<String,ShoutingMonkey>();
        interpretInput(input);

        long number=rootMonkey.getNumber(monkeys);
        System.out.println("The number the root monkey shouts is "+ number);

        long numberToYell=rootMonkey.getNumberToYell(0);
        System.out.println("The number to shout is "+ numberToYell);
    }

    private void interpretInput(Scanner input)
    {
        while (input.hasNextLine())
        {
            String line=input.nextLine();
            String[] info=line.split(": ");
            String name=info[0];
            info=info[1].split(" ");
            ShoutingMonkey monkey;

            if(info.length==1)
            {
                int number=Integer.parseInt(info[0]);
                monkey=new ShoutingMonkey(name,number);
            }
            else
            {
                String othermonkey1=info[0];
                String othermonkey2=info[2];
                String operation=info[1];

                monkey=new ShoutingMonkey(name,othermonkey1,operation,othermonkey2);
            }
            monkeys.put(name,monkey);

            if(name.equals(START))
            {
                rootMonkey=monkey;
            }
        }   
    }
}
