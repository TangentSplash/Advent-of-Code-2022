import java.io.File;
import java.util.*;

/* Advent of Code 2022
* Day 18 Part 1
*/

public class LavaDroplets 
{
    public static final int SIDE_LENGTH=1;
    private final int SEARCH_X=0;
    private final int SEARCH_Y=1;
    private final int SEARCH_Z=2;

    private int xMax;
    private int yMax;
    private int zMax;
    
    CubeDroplet[][][] dropletsInSpace;
    List<CubeDroplet> dropletList;
    Set<CubeDroplet> externalDroplets;

    public LavaDroplets() throws Exception
    {
        File inputFile = new File("Day 18/inputtest.txt");
        Scanner input = new Scanner(inputFile);
        dropletList=new ArrayList<CubeDroplet>();
        externalDroplets=new HashSet<CubeDroplet>();

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
        int allExposedSurfaceArea=0;
        for (CubeDroplet cubeDroplet : dropletList) 
        {
            allExposedSurfaceArea+=cubeDroplet.getAllExposedSurfaceArea();
        }
        System.out.println("The estimated total surface area is "+allExposedSurfaceArea);

        scanDirection(yMax, zMax, xMax, SEARCH_X,false);
        scanDirection(yMax, zMax, xMax, SEARCH_X,true);
        scanDirection(xMax, zMax, yMax, SEARCH_Y,false);
        scanDirection(xMax, zMax, yMax, SEARCH_Y,true);
        scanDirection(xMax, yMax, zMax, SEARCH_Z,false);
        scanDirection(xMax, yMax, zMax, SEARCH_Z,true);

        int externalSurfaceArea=0;
        for (CubeDroplet cubeDroplet : externalDroplets) 
        {
            externalSurfaceArea+=cubeDroplet.getAllExposedSurfaceArea();
        }
        System.out.println("The estimated total external surface area is "+externalSurfaceArea);
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

    /***
     * @param aMax
     * @param bMax
     * @param cMax Dimension that is being searched along
     * @param searchDimension 0: search along x, a is y,b is z. 1: along y, a is x, b is z. 2: along z, a is x, b is y.  
     * @param searchBack search from max down to zero in search dimension
     */
    private void scanDirection(int aMax,int bMax,int cMax,int searchDimension,boolean searchBack)
    {
        for (int a=0;a<aMax;a++)
        {
            for(int b=0;b<bMax;b++)
            {
                for (int c=0;c<cMax;c++)
                {
                    int i=0,j=0,k=0,searchValue=c;
                    if(searchBack)
                    {
                        searchValue=cMax-c;
                    }
                    switch (searchDimension)
                    {
                        case 0:
                        {
                            i=searchValue;
                            j=a;
                            k=b;
                            break;
                        }
                        case 1:
                        {
                            j=searchValue;
                            i=a;
                            k=b;
                            break;
                        }
                        case 2:
                        {
                            k=searchValue;
                            i=a;
                            j=b;
                            break;
                        }
                    }
                    CubeDroplet currentDroplet=dropletsInSpace[k][j][i];
                    if(currentDroplet!=null)
                    {
                        externalDroplets.add(currentDroplet);
                        if(searchFurther(c,cMax,a,b,searchDimension,searchBack))    //If there is another droplet along this direction, it means there is  
                        {

                        }
                        break;  //Stop searching in this dimension, search a different space
                    }
                }
            }
        }
    }

    private boolean searchFurther(int start,int end,int a,int b,int searchDimension,boolean searchBack)
    {
        for (int d=start;d<end;d++)
        {
            int i=0,j=0,k=0,searchValue=d;
            if(searchBack)
            {
                searchValue=end-d;
            }
            switch (searchDimension)
            {
                case 0:
                {
                    i=searchValue;
                    j=a;
                    k=b;
                    break;
                }
                case 1:
                {
                    j=searchValue;
                    i=a;
                    k=b;
                    break;
                }
                case 2:
                {
                    k=searchValue;
                    i=a;
                    j=b;
                    break;
                }
            }
            CubeDroplet currentDroplet=dropletsInSpace[k][j][i];
            if(currentDroplet!=null)
            {
                return true;
            }
        }
        return false;
    }
}
