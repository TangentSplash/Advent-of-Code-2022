public class CubeDroplet 
{
    private int x;
    private int y;
    private int z;

    private int surfaceArea;

    public CubeDroplet(int x,int y,int z)
    {
        this.x=x;
        this.y=y;
        this.z=z;

        this.surfaceArea=6*LavaDroplets.SIDE_LENGTH*LavaDroplets.SIDE_LENGTH;
    }

    public void searchFaces(CubeDroplet[][][] allDroplets)
    {
        searchLocation(x+1, y, z, allDroplets);
        searchLocation(x-1, y, z, allDroplets);
        searchLocation(x, y+1, z, allDroplets);
        searchLocation(x, y-1, z, allDroplets);
        searchLocation(x, y, z+1, allDroplets);
        searchLocation(x, y, z-1, allDroplets);
    }

    private void searchLocation(int x,int y,int z,CubeDroplet[][][] allDroplets)
    {
        if(x>=0 && x<allDroplets[0][0].length && y>=0 && y<allDroplets[0].length && z>=0 && z<allDroplets.length)
        {
            if(allDroplets[z][y][x]!=null)
            {
                surfaceArea--;
            }
        }
    }

    public int getExposedSurfaceArea()
    {
        return surfaceArea;
    }
    
}
