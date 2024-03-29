import java.io.File;
import java.util.*;
import java.math.BigInteger;

/* Advent of Code 2022
* Day 20 Part 2
*/
public class FileDecryption 
{
    private List<Number> numbers;
    private final int COORD_1=1000;
    private final int COORD_2=2000;
    private final int COORD_3=3000;

    private final int DECRYPTION_KEY=811589153;

    private List<Number> decoded;

    public FileDecryption () throws Exception
    {
        numbers=new ArrayList<Number>();
        decoded=new ArrayList<Number>();

        File inputFile = new File("Day 20/input.txt");
        Scanner input = new Scanner(inputFile);

        int i=0;
        Number zero=new Number(-1, -1);
        while (input.hasNextLine())
        {
            String line=input.nextLine();
            int num=Integer.parseInt(line);
            Number number=new Number(num, i);
            numbers.add(number);
            i++;
            if (num==0)
            {
                zero=number;
            }
        }
        input.close();
        int length=i;

        decoded=new ArrayList<Number>(numbers);

        for (Number number : numbers) 
        {
            int startAt=decoded.indexOf(number);
            number.updateIndex(startAt);
            int endAt=number.reorder(length);
            decoded.remove(number);
            decoded.add(endAt, number);
        }

        int coordinate_sum=0;
        int zeroIndex=decoded.indexOf(zero);
        int coordinate1=decoded.get(Math.floorMod(zeroIndex+COORD_1, length)).getNumber();
        int coordinate2=decoded.get(Math.floorMod(zeroIndex+COORD_2, length)).getNumber();
        int coordinate3=decoded.get(Math.floorMod(zeroIndex+COORD_3, length)).getNumber();
        coordinate_sum=(coordinate1+coordinate2+coordinate3);

        System.out.println("The sum of the coordinates for part 1 is "+coordinate_sum);

        //Part 2
        decoded=new ArrayList<Number>(numbers);

        int decriptionKeyModulo=DECRYPTION_KEY%(length-1);
        for (Number number : numbers) 
        {
            int num=number.getNumber();
            int newNumber=(num*decriptionKeyModulo)%(length-1);
            number.updateNumber(newNumber);
        }

        for(int j=0;j<10;j++)
        {
            for (Number number : numbers) 
            {
                int startAt=decoded.indexOf(number);
                number.updateIndex(startAt);
                int endAt=number.reorder(length);
                decoded.remove(number);
                decoded.add(endAt, number);
            }
        }
        coordinate_sum=0;
        zeroIndex=decoded.indexOf(zero);
        coordinate1=decoded.get(Math.floorMod(zeroIndex+COORD_1, length)).getNumber();
        coordinate2=decoded.get(Math.floorMod(zeroIndex+COORD_2, length)).getNumber();
        coordinate3=decoded.get(Math.floorMod(zeroIndex+COORD_3, length)).getNumber();
        coordinate_sum=(coordinate1+coordinate2+coordinate3);

        BigInteger sum, decryptionKey, decrypted;
        sum=new BigInteger((Integer.toString(coordinate1+coordinate2+coordinate3)));
        decryptionKey=new BigInteger((Integer.toString(DECRYPTION_KEY)));
        decrypted=sum.multiply(decryptionKey);
        System.out.println("The sum of the coordinates is "+ decrypted);
    }
}
