import java.io.File;
import java.util.*;

/* Advent of Code 2022
* Day 21 Part 1
*/

public class NumberYelling 
{
    private Map<String,Monkey> monkeys;
    private final String START="root";
    private Monkey rootMonkey;

    public NumberYelling () throws Exception
    {
        File inputFile = new File("Day 21/input.txt");
        Scanner input = new Scanner(inputFile);

        monkeys=new HashMap<String,Monkey>();
        interpretInput(input);

        long number=rootMonkey.getNumber(monkeys);
        System.out.println("The number the root monkey shouts is "+ number);
    }

    private void interpretInput(Scanner input)
    {
        while (input.hasNextLine())
        {
            String line=input.nextLine();
            String[] info=line.split(": ");
            String name=info[0];
            info=info[1].split(" ");
            Monkey monkey;
            if(info.length==1)
            {
                int number=Integer.parseInt(info[0]);
                monkey=new Monkey(name,number);
            }
            else
            {
                String othermonkey1=info[0];
                String othermonkey2=info[2];
                String operation=info[1];

                monkey=new Monkey(name,othermonkey1,operation,othermonkey2);
            }
            monkeys.put(name,monkey);

            if(name.equals(START))
            {
                rootMonkey=monkey;
            }
        }   
    }
}
