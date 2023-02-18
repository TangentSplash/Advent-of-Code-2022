import java.util.*;

public class ShoutingMonkey 
{
    private String name;
    private String otherMonkey1,otherMonkey2;
    private ShoutingMonkey monkey1,monkey2;
    private int operation;
    private long number;
    private boolean numberCalculated;
    private boolean pathToHuman;

    private final int ADD=0;
    private final int MULTIPLY=1;
    private final int SUBTRACT=2;
    private final int DIVIDE=3;

    public ShoutingMonkey(String name, String otherMonkey1,String operation, String otherMonkey2)
    {
        this.name=name;
        this.otherMonkey1=otherMonkey1;
        this.otherMonkey2=otherMonkey2;
        switch (operation) 
        {
            case "+":
                this.operation=ADD;
                break;
            case "-":
                this.operation=SUBTRACT;
                break;
            case "*":
                this.operation=MULTIPLY;
                break;
            case "/":
                this.operation=DIVIDE;
                break;
        
            default:
                System.out.println("Unknown operator " + operation);
                break;
        }
        this.numberCalculated=false;
        this.pathToHuman=false;
    }

    public ShoutingMonkey(String name,int number)
    {
        this.name=name;
        this.number=number;
        this.numberCalculated=true;
        this.pathToHuman=false;
    }

    public long getNumber(Map<String,ShoutingMonkey> monkies)
    {
        if(numberCalculated)
        {
            return number;
        }
        else
        {
            monkey1=monkies.get(otherMonkey1);
            long num1=monkey1.getNumber(monkies);
            
            monkey2=monkies.get(otherMonkey2);
            long num2=monkey2.getNumber(monkies);
            
            if(otherMonkey1.equals(NumberYelling.HUMAN) || otherMonkey2.equals(NumberYelling.HUMAN))
            {
                if(otherMonkey1.equals(NumberYelling.HUMAN) )
                {
                    monkey1.setPathToHuman(true);
                }
                else 
                {
                    monkey2.setPathToHuman(true);
                }
                pathToHuman=true;
            }

            if (monkey1.isPathToHuman() || monkey2.isPathToHuman())
            {
                pathToHuman=true;
            }

            number=operate(num1,operation,num2);
            numberCalculated=true;
            return number;
        }
    }

    private long operate(long num1,int operation,long num2)
    {
        long num3=-1;
        switch (operation) 
        {
            case ADD:
                num3=num1+num2;
                break;
            case SUBTRACT:
                num3=num1-num2;
                break;
            case MULTIPLY:
                num3=num1*num2;
                break;
            case DIVIDE:
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

    public boolean isPathToHuman()
    {
        return pathToHuman;
    }

    public long getNumberToYell(long currentTotal)
    {
        int newOperation=Math.floorMod(operation+2, 4); //Get the inverse operation

        ShoutingMonkey computedMonkey=null;
        ShoutingMonkey xMonkey=null;
        
        long num1=-1,num2=-1;
        long newNumber=-1;
        if(name.equals(NumberYelling.HUMAN))
        {
            return currentTotal;
        }
        else if(monkey1.isPathToHuman())
        {
            xMonkey=monkey1;
            computedMonkey=monkey2; 
            newNumber=computedMonkey.getNumber(null);
            num2=newNumber;
            num1=currentTotal;
        }
        else if(monkey2.isPathToHuman())
        {
            xMonkey=monkey2;
            computedMonkey=monkey1;
            newNumber=computedMonkey.getNumber(null);
            num2=newNumber;
            num1=currentTotal;
            if(operation==DIVIDE|| operation==SUBTRACT)
            {
                /* Notes on maths
                * 6=7-x  == x=7-6  != 6+7=x
                * 6=24/x == x=24/6 != 6*24=x
                */
                num1=newNumber;
                num2=currentTotal;
                newOperation=operation;
            }
        }
        else
        {
            System.out.println("No monkeys on path to human");
        }

        
        long newTotal;
        if(name.equals(NumberYelling.START))
        {
            newTotal=newNumber;
        }
        else
        {
            newTotal=operate(num1,newOperation,num2);
        }
        long numberToYell=xMonkey.getNumberToYell(newTotal);
        return numberToYell;
    }

    public void setPathToHuman(boolean pathToHuman) 
    {
        this.pathToHuman = pathToHuman;
    }
}
