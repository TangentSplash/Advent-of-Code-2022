import java.util.*;

public class Monkey 
{
    private String name;
    private String otherMonkey1,otherMonkey2;
    private String operation;
    private long number;
    private boolean numberCalculated;

    public Monkey(String name, String otherMonkey1,String operation, String otherMonkey2)
    {
        this.name=name;
        this.otherMonkey1=otherMonkey1;
        this.otherMonkey2=otherMonkey2;
        this.operation=operation;
        this.numberCalculated=false;
    }

    public Monkey(String name,int number)
    {
        this.name=name;
        this.number=number;
        this.numberCalculated=true;
    }

    public long getNumber(Map<String,Monkey> monkies)
    {
        if(numberCalculated)
        {
            return number;
        }
        else
        {
            Monkey monkey1=monkies.get(otherMonkey1);
            long num1=monkey1.getNumber(monkies);

            Monkey monkey2=monkies.get(otherMonkey2);
            long num2=monkey2.getNumber(monkies);

            number=operate(num1,num2);
            numberCalculated=true;
            return number;
        }
    }

    private long operate(long num1,long num2)
    {
        long num3=-1;
        switch (operation) 
        {
            case "+":
                num3=num1+num2;
                break;
            case "-":
                num3=num1-num2;
                break;
            case "*":
                num3=num1*num2;
                break;
            case "/":
                num3=num1/num2;
                break;
        
            default:
                System.out.println("Unknown operator " + operation);
                break;
        }
        return num3;
    }

    public String getName()
    {
        return name;
    }
}
