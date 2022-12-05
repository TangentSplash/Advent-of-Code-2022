import java.util.*;

public class CrateStack{
    private Stack<Crate> stack;

    public CrateStack()
    {
        stack=new Stack<Crate>();
    }

    public void add(char name)
    {
        if (name!=' ')
        {
            stack.add(new Crate(name));
        }
    }

    public Stack<Crate> pop(int number)
    {
        Stack<Crate> popped=new Stack<Crate>();
        for (int i=0;i<number;i++)
        {
            popped.add(stack.pop());
        }
        return popped;
    }

    public void addAll(Stack<Crate> newCrates)
    {
        for (Crate crate : newCrates) {
            stack.push(crate);
            //stack.addFirst(crate);
        }
        //stack.addAll(newCrates);
    }

    public char getTopChar()
    {
        return stack.peek().getName();
    }
    
}
