import java.io.File;
import java.util.*;

/* Advent of Code 2022
* Day 18 Part 2
*/

public class LavaDroplets 
{
    public static final int SIDE_LENGTH=1;
    private final int SEARCH_X=0;
    private final int SEARCH_Y=1;
    private final int SEARCH_Z=2;

    private final int EXTERNAL_AIR_MODE=0;
    private final int DROPLETS_MODE=1;
    private final int INTERNAL_AIR_MODE=2;

    private int xMax;
    private int yMax;
    private int zMax;
    
    private Cube[][][] dropletsInSpace;
    private List<Cube> dropletList;
    private Set<Cube> internalAir;

    public LavaDroplets() throws Exception
    {
        File inputFile = new File("Day 18/input.txt");
        Scanner input = new Scanner(inputFile);
        dropletList=new ArrayList<Cube>();
        internalAir=new HashSet<Cube>();

        interpretInput(input);
        input.close();

        int allExposedSurfaceArea=0;
        for (Cube cubeDroplet : dropletList) 
        {
            cubeDroplet.searchFaces(dropletsInSpace);
            allExposedSurfaceArea+=cubeDroplet.getAllExposedSurfaceArea();
        }
        System.out.println("The estimated total surface area is "+allExposedSurfaceArea);

        scanDirection(yMax, zMax, xMax, SEARCH_X,false);
        scanDirection(yMax, zMax, xMax, SEARCH_X,true);
        scanDirection(xMax, zMax, yMax, SEARCH_Y,false);
        scanDirection(xMax, zMax, yMax, SEARCH_Y,true);
        scanDirection(xMax, yMax, zMax, SEARCH_Z,false);
        scanDirection(xMax, yMax, zMax, SEARCH_Z,true);

        //A poor attempt to make sure that all caves with only small openings are fully labeled as external - just search it all again 
        scanDirection(yMax, zMax, xMax, SEARCH_X,false);
        scanDirection(yMax, zMax, xMax, SEARCH_X,true);
        scanDirection(xMax, zMax, yMax, SEARCH_Y,false);
        scanDirection(xMax, zMax, yMax, SEARCH_Y,true);
        scanDirection(xMax, yMax, zMax, SEARCH_Z,false);
        scanDirection(xMax, yMax, zMax, SEARCH_Z,true);

        int trapedAirSurfaceArea=0;
        for (Cube airCube : internalAir) 
        {
            airCube.searchFaces(dropletsInSpace);
            trapedAirSurfaceArea+=(6-airCube.getAllExposedSurfaceArea());
        }
        System.out.println("The estimated air pockets area is "+trapedAirSurfaceArea);
        System.out.println("Therefore the estimated external surface area is "+(allExposedSurfaceArea-trapedAirSurfaceArea));
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

        dropletsInSpace=new Cube[zMax+1][yMax+1][xMax+1];

        for (int i = 0; i < xLocations.size(); i++) 
        {
            int x=xLocations.get(i);
            int y=yLocations.get(i);
            int z=zLocations.get(i);
            Cube newDroplet=new Cube(x, y, z,Cube.DROPLETS_TYPE);
            dropletsInSpace[z][y][x]=newDroplet;
            dropletList.add(newDroplet);
        }
    }

    /*** Find all internal air pockets
     * @param aMax
     * @param bMax
     * @param cMax Dimension that is being searched along
     * @param searchDimension 0: search along x, a is y,b is z. 1: along y, a is x, b is z. 2: along z, a is x, b is y.  
     * @param searchBack search from max down to zero in search dimension
     */
    private void scanDirection(int aMax,int bMax,int cMax,int searchDimension,boolean searchBack)
    {
        for (int a=0;a<=aMax;a++)
        {
            for(int b=0;b<=bMax;b++)
            {
                int searchMode=EXTERNAL_AIR_MODE;
                for (int c=0;c<=cMax;c++)
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
                    Cube currentDroplet=dropletsInSpace[k][j][i];
                    if(currentDroplet==null)
                    {
                        if(searchMode==DROPLETS_MODE)
                        {
                            searchMode=INTERNAL_AIR_MODE;
                        }
                        Cube newCube=new Cube(i, j, k, searchMode);
                        dropletsInSpace[k][j][i]=newCube; //set to internal/external air
                        if(searchMode==INTERNAL_AIR_MODE)
                        {
                            internalAir.add(newCube);
                        }
                    }
                    else if(currentDroplet.getType()==Cube.DROPLETS_TYPE)
                    {
                        searchMode=DROPLETS_MODE;
                    }
                    else if(currentDroplet.getType()==Cube.INTERNAL_AIR_TYPE && searchMode==EXTERNAL_AIR_MODE)
                    {
                        currentDroplet.setType(Cube.EXTERNAL_AIR_TYPE);
                        internalAir.remove(currentDroplet);
                    }
                    else if (currentDroplet.getType()==Cube.EXTERNAL_AIR_TYPE)
                    {
                        searchMode=EXTERNAL_AIR_MODE;
                    }
                    else if (currentDroplet.getType()==Cube.INTERNAL_AIR_TYPE)  //Don't really care
                    {
                        searchMode=INTERNAL_AIR_MODE;
                    }
                }
            }
        }
    }
}
