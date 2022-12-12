/* Advent of Code 2022
* Day 11 Part 2
*/

import java.io.File;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PiggyInTheMiddle {
    
    private static final String MONKEY_STRING="Monkey ";
    private static final String STARTING_STRING="Starting items: ";
    private static final String OPERATION_STRING="Operation: new = old ";
    private static final String TEST_STRING="Test: divisible by ";
    private static final String TRUE_STRING="If true: throw to monkey ";
    private static final String FALSE_STRING="If false: throw to monkey ";

    private static boolean PART_2=true;

    public static void main(String[] args) throws Exception
    {

        int rounds=20;
        int reliefFactor=3;
        if(PART_2)
        {
            reliefFactor=1;
            rounds=10000;
        }

        int LCM=1;

        File inputFile = new File("Day 11\\input.txt");
        Scanner input = new Scanner(inputFile);

        String line;

        Pattern monkeyRegexPattern = Pattern.compile(MONKEY_STRING);
        Pattern startingRegexPattern = Pattern.compile(STARTING_STRING);
        Pattern operationRegexPattern = Pattern.compile(OPERATION_STRING);
        Pattern testRegexPattern = Pattern.compile(TEST_STRING);
        Pattern trueRegexPattern = Pattern.compile(TRUE_STRING);
        Pattern falseRegexPattern = Pattern.compile(FALSE_STRING);        

        Monkey currentMonkey=new Monkey(-1);

        List<Monkey> monkeys=new ArrayList<>();

        while (input.hasNextLine())
        {
            line=input.nextLine();
            if(line.isEmpty())
            {
                continue;
            }

            Matcher monkeyRegexMatcher = monkeyRegexPattern.matcher(line);
            Matcher startingRegexMatcher = startingRegexPattern.matcher(line);
            Matcher operationRegexMatcher = operationRegexPattern.matcher(line);
            Matcher testRegexMatcher = testRegexPattern.matcher(line);
            Matcher trueRegexMatcher = trueRegexPattern.matcher(line);
            Matcher falseRegexMatcher = falseRegexPattern.matcher(line);

            if(monkeyRegexMatcher.find())
            {
                int monkeyNumber=getNumber(line, MONKEY_STRING)[0];
                currentMonkey=new Monkey(monkeyNumber);
                monkeys.add(currentMonkey);
            }
            else if(startingRegexMatcher.find())
            {
                int[] startingWorryLevel=getNumber(line, STARTING_STRING);
                currentMonkey.setStartingItems(startingWorryLevel);
            }
            else if(operationRegexMatcher.find())
            {
                String[] operations=line.split(OPERATION_STRING)[1].split(" ");
                currentMonkey.setOperations(operations[0].charAt(0), operations[1]);
            }
            else if(testRegexMatcher.find())
            {
                int test=getNumber(line,TEST_STRING)[0];
                currentMonkey.addTest(test);
                LCM*=test;
            }
            else if(trueRegexMatcher.find())
            {
                int monkeyTo=getNumber(line,TRUE_STRING)[0];
                currentMonkey.nextMonkey(monkeyTo,true);
            }
            else if(falseRegexMatcher.find())
            {
                int monkeyTo=getNumber(line,FALSE_STRING)[0];
                currentMonkey.nextMonkey(monkeyTo,false);
            }
            else
            {
                System.out.println("Input \'"+line+"\'' not expected");
            }
        }
        for (Monkey monkey : monkeys) {
            monkey.setLCM(LCM);
        }

        input.close();

        int o;
        for (int round=1;round<=rounds;round++)
        {
            for (Monkey monkey : monkeys) {
                int numberOfItems=monkey.getNumberOfItems();
                for(int i=0;i<numberOfItems;i++)
                {
                    int worry=monkey.inspectItem();
                    monkey.throwItem(worry/reliefFactor,monkeys);
                }
            }
            o=0;
        }

        List<Integer> inspections=new ArrayList<>();
        for (Monkey monkey : monkeys) {
            inspections.add(monkey.getNumberOfInspection());
        }

        Collections.sort(inspections,Collections.reverseOrder());

        long monkeyBusiness=(long)inspections.get(0)*(long)inspections.get(1);
        System.out.println(inspections.get(0));
        System.out.println(inspections.get(1));
        System.out.println("The monkey business is "+monkeyBusiness);
    }

    private static int[] getNumber(String input, String splitOn)
    {
        String[] inputBits= input.split(splitOn);
        String[] numberAsString=inputBits[1].split(":");
        String[] numberStrings=numberAsString[0].split(", ");
        int length=numberStrings.length;
        int[] numbers=new int[length];
        for (int i=0;i<length;i++) 
        {
            numbers[i]=Integer.parseInt(numberStrings[i]);
        }
        return numbers;
    }
}
