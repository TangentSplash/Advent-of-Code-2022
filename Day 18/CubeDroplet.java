public class CubeDroplet 
{
    private int x;
    private int y;
    private int z;

    private int surfaceArea;
    private int externalSurfaceArea;

    private boolean[] exposedFaces;


    public CubeDroplet(int x,int y,int z)
    {
        this.x=x;
        this.y=y;
        this.z=z;

        this.surfaceArea=6*LavaDroplets.SIDE_LENGTH*LavaDroplets.SIDE_LENGTH;
        this.externalSurfaceArea=surfaceArea;
        this.exposedFaces=new boolean[6];
    }

    public void searchFaces(CubeDroplet[][][] allDroplets)
    {
        exposedFaces[0]=searchLocation(x+1, y, z, allDroplets);
        exposedFaces[1]=searchLocation(x-1, y, z, allDroplets);
        exposedFaces[2]=searchLocation(x, y+1, z, allDroplets);
        exposedFaces[3]=searchLocation(x, y-1, z, allDroplets);
        exposedFaces[4]=searchLocation(x, y, z+1, allDroplets);
        exposedFaces[5]=searchLocation(x, y, z-1, allDroplets);
    }

    private boolean searchLocation(int x,int y,int z,CubeDroplet[][][] allDroplets)
    {
        if(x>=0 && x<allDroplets[0][0].length && y>=0 && y<allDroplets[0].length && z>=0 && z<allDroplets.length)
        {
            if(allDroplets[z][y][x]!=null)
            {
                surfaceArea--;
                return true;
            }
        }
        return false;
    }

    public int getAllExposedSurfaceArea()
    {
        externalSurfaceArea=surfaceArea;
        return surfaceArea;
    }
}
