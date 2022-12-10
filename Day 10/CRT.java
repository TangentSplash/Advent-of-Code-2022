/* Advent of Code 2022
* Day 10 Part 1
*/

public class CRT {
    public String CRTString="";
    public CRT()
    {

    }  

    public void checkCRTPixel(int cycle,int X)
    {
        if (cycle==0)
        {
            cycle=40;
        }
        if((cycle-1)>=(X-1) && (cycle-1)<=(X+1))
            {
                CRTString+="#";
            }
            else
            {
                CRTString+=".";
            }
            if(cycle==40)
            {
                CRTString+="\n";
            }
    }

    public String getCRT()
    {
        return CRTString;
    }
}
