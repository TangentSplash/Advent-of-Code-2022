public class Group {
    private String[] IDStrings;

    private IDSection section1;
    private IDSection section2;

    public Group(String pairs)
    {
        IDStrings=pairs.split(",");
        
        section1=new IDSection(IDStrings[0]);
        section2=new IDSection(IDStrings[1]);       
    }

    public boolean fullyContains()
    {
        return section1.fullyContains(section2);
    }

    public boolean someOverlap()
    {
        return section1.someOverlap(section2);
    }
    
}
