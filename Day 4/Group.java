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

    public boolean compare()
    {
        return section1.compare(section2);
    }
    
}
