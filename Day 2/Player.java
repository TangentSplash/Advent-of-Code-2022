public class Player {

    private static final int WIN_SCORE=6;
    private static final int DRAW_SCORE=3;
    private Move move;
    private int score;

    public Player ()
    {
        score=0;
    }

    public void moveChosen(char move)
    {
        switch (move) {
            case 'A':
            case 'X':
                this.move=Move.ROCK; 
                break;
        
            case 'B':
            case 'Y':
                this.move=Move.PAPER; 
                break;

            case 'C':
            case 'Z':
                this.move=Move.SCISSORS; 
                break;

            default:
                System.out.println("Move "+ move + "not recognised");
                break;
        }
    }

    public void compareMoves(Player opponent)
    {
        int myMove;
        int opponentMove;
        boolean iWin=false;
        boolean draw=false;

        myMove=move.toInt(move);
        opponentMove=opponent.getMoveInt();

        if (opponentMove==myMove)
        {
            draw=true;
        }
        else
        {
            
            int max=Integer.max(myMove, opponentMove);
            if ((max==myMove && Math.abs(myMove-opponentMove)==1) || (max==opponentMove && Math.abs(myMove-opponentMove)!=1))
            {
                iWin=true;
            }
        }
        addScore(iWin,draw,myMove);
        opponent.addScore(!iWin,draw,opponentMove);
    }

    public void chooseMove(char outcome,int opponentMoveNum)
    {
        boolean iWin=false;
        boolean draw=false;
        int myMove=-1;
        switch (outcome) {
            case 'X':
                myMove=opponentMoveNum-1;
                break;

            case 'Y':
                myMove=opponentMoveNum;
                draw=true;
                break;

            case 'Z':
                myMove=opponentMoveNum+1;
                iWin=true;
                break;
                
            default:
                System.out.println("Outcome "+ outcome+ " not recognised");
                break;
        }
        myMove=Math.floorMod(myMove, 3);
        addScore(iWin, draw, myMove);

    }

    private void addScore(boolean iWin, boolean draw, int myMoveScore)
    {
        int roundScore=0;
        if(iWin)
        {
            roundScore=WIN_SCORE;
        }
        else if (draw)
        {
            roundScore=DRAW_SCORE;
        }
        roundScore+=myMoveScore+1;

        score+=roundScore;
    }

    public int getScore()
    {
        return score;
    }

    public int getMoveInt()
    {
        return move.toInt(move);
    }
}
