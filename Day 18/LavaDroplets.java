import java.io.File;
import java.util.*;

/* Advent of Code 2022
* Day 18 Part 1
*/

public class LavaDroplets 
{
    public static final int SIDE_LENGTH=1;
    private int xMax;
    private int yMax;
    private int zMax;
    
    CubeDroplet[][][] dropletsInSpace;
    List<CubeDroplet> dropletList;

    public LavaDroplets() throws Exception
    {
        File inputFile = new File("Day 18/input.txt");
        Scanner input = new Scanner(inputFile);
        dropletList=new ArrayList<CubeDroplet>();

        interpretInput(input);
        input.close();

        for (int i=0;i<=xMax;i++)
        {
            for (int j=0;j<=yMax;j++)
            {
                for (int k=0;k<=zMax;k++)
                {
                    CubeDroplet currentDroplet=dropletsInSpace[k][j][i];
                    if(currentDroplet!=null)
                    {
                        currentDroplet.searchFaces(dropletsInSpace);
                    }
                }
            }
        }
        int exposedSurfaceArea=0;
        for (CubeDroplet cubeDroplet : dropletList) 
        {
            exposedSurfaceArea+=cubeDroplet.getExposedSurfaceArea();
        }
        System.out.println("The estimated surface area is "+exposedSurfaceArea);
    }

    private void interpretInput(Scanner input)
    {
        List<Integer> xLocations=new ArrayList<Integer>();
        List<Integer> yLocations=new ArrayList<Integer>();
        List<Integer> zLocations=new ArrayList<Integer>();
        xMax=0;
        yMax=0;
        zMax=0;
        while(input.hasNextLine())
        {
            String droplet=input.nextLine();
            String[] coords=droplet.split(",");

            int x=Integer.parseInt(coords[0]);
            xLocations.add(x);
            xMax=Math.max(x, xMax);

            int y=Integer.parseInt(coords[1]);
            yLocations.add(y);
            yMax=Math.max(y, yMax);

            int z=Integer.parseInt(coords[2]);
            zLocations.add(z);
            zMax=Math.max(z, zMax);
        }

        dropletsInSpace=new CubeDroplet[zMax+1][yMax+1][xMax+1];

        for (int i = 0; i < xLocations.size(); i++) 
        {
            int x=xLocations.get(i);
            int y=yLocations.get(i);
            int z=zLocations.get(i);
            CubeDroplet newDroplet=new CubeDroplet(x, y, z);
            dropletsInSpace[z][y][x]=newDroplet;
            dropletList.add(newDroplet);
        }
    }
}
