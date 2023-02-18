public enum Orientations {
    RIGHT('>'),
    DOWN('v'),
    LEFT('<'),
    UP('^');

    private final char direction;

    private Orientations(char direction)
    {
        this.direction=direction;
    }

    public char getLabel()
    {
        return direction;
    }

    public Orientations rotate(int direction)
    {
        int num=Math.floorMod(ordinal()+direction, 4);
        return values()[num];
    }
}
