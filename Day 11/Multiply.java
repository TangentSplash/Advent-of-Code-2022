public class Multiply extends Operation{
    public int operate(int a,int b,int N)
    {
        double mult;
        //a=N;
        //b=N;
        if (a==b)
        {
            mult=Math.pow(a, 2);
        }
        else
        {
            mult=Math.multiplyExact(a, b);
        }

        /*while(mult>N)
        {
            mult-=N;
        }*/
        
        int value=(int)(mult%(long)N);
        return value;
    }
}
