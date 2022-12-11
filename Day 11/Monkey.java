import java.util.ArrayList;
import java.util.List;

public class Monkey {
    private int number;

    private int test;
    private int nextMonkeyTrue;
    private int nextMonkeyFalse;

    private Variable operand;

    private int numberOfInspections;

    private Operation operation;

    private List<Integer> worryLevels;

    public Monkey(int number)
    {
        this.number=number;
        worryLevels=new ArrayList<>();
        numberOfInspections=0;
    }

    public int getNumber()
    {
        return number;
    }

    public void addTest(int test)
    {
        this.test=test;
    }

    public void nextMonkey(int monkeyNumber, boolean outcome)
    {
        if (outcome)
        {
            nextMonkeyTrue=monkeyNumber;
        }
        else
        {
            nextMonkeyFalse=monkeyNumber;
        }
    }

    public void setStartingItems(int[] worryLevels)
    {
        for (int worry : worryLevels) 
        {
            this.worryLevels.add(worry);
        }
    }

    public void setOperations(char operator, String number)
    {
        if(number.equals("old"))
        {
            operand=new Variable();
        }
        else 
        {
            operand=new Variable(Integer.parseInt(number));
        }

        switch (operator)
        {
            case '+':
                operation=new Add();
                break;
            case '*':
            operation=new Multiply();
                break;
            case '-':
            case '/':
                System.out.println("Implement "+operator+" operation");
                break;
            default:
                System.out.println("Unknown operation "+operator);
        }
    }

    public int getNumberOfItems()
    {
        return worryLevels.size();
    }

    public int inspectItem()
    {
        int worry=worryLevels.remove(0);
        numberOfInspections++;
        return operation.operate(worry, operand.getValue(worry));
    }

    public void throwItem(int worry,List<Monkey> monkeys)
    {
        if( Math.floorMod(worry, test)==0)
        {
            monkeys.get(nextMonkeyTrue).catchItem(worry);
        }
        else
        {
            monkeys.get(nextMonkeyFalse).catchItem(worry);
        }
    }

    public void catchItem(int item)
    {
        worryLevels.add(item);
    }

    public int getNumberOfInspection()
    {
        return numberOfInspections;
    }
    
}
