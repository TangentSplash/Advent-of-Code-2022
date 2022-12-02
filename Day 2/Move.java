public enum Move {
    ROCK,
    PAPER,
    SCISSORS;

    public int toInt(Move move)
    {
        int i=0;
        for (Move moves : Move.values()) {
            if (moves==move)
            {
                return i;
            }
            i++;
        }
        return -1;
    }
}
