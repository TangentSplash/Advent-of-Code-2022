public class Cube 
{
    public final static int EXTERNAL_AIR_TYPE=0;
    public final static int DROPLETS_TYPE=1;
    public final static int INTERNAL_AIR_TYPE=2;
    private int x;
    private int y;
    private int z;

    private int surfaceArea;
    private int type;


    public Cube(int x,int y,int z,int type)
    {
        this.x=x;
        this.y=y;
        this.z=z;

        this.type=type;
        this.surfaceArea=6*LavaDroplets.SIDE_LENGTH*LavaDroplets.SIDE_LENGTH;
    }

    public void searchFaces(Cube[][][] allDroplets)
    {
        searchLocation(x+1, y, z, allDroplets);
        searchLocation(x-1, y, z, allDroplets);
        searchLocation(x, y+1, z, allDroplets);
        searchLocation(x, y-1, z, allDroplets);
        searchLocation(x, y, z+1, allDroplets);
        searchLocation(x, y, z-1, allDroplets);
    }

    private void searchLocation(int x,int y,int z,Cube[][][] allDroplets)
    {
        if(x>=0 && x<allDroplets[0][0].length && y>=0 && y<allDroplets[0].length && z>=0 && z<allDroplets.length)
        {
            Cube droplet=allDroplets[z][y][x];
            if(droplet!=null && droplet.getType()==DROPLETS_TYPE)
            {
                surfaceArea--;
            }
        }
    }

    public int getAllExposedSurfaceArea()
    {
        return surfaceArea;
    }

    public int getType()
    {
        return type;
    }

    public void setType(int type)
    {
        this.type=type;
    }
}
