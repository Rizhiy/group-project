package uk.ac.cam.cl.intelligentgamedesigner.simulatedplayers;

import uk.ac.cam.cl.intelligentgamedesigner.coregame.GameState;
import uk.ac.cam.cl.intelligentgamedesigner.coregame.InvalidMoveException;
import uk.ac.cam.cl.intelligentgamedesigner.coregame.Move;

public class ScorePlayerGamma extends SimulatedPlayerBase {
    
    @Override
    public void solve(GameState level) throws NoMovesFoundException {
        while (level.getMovesRemaining() > 0) {
            Move bestMove = calculateBestMove(level);
            try {
                level.makeMove(bestMove);
            } catch (InvalidMoveException e) {
                printInvalidMoveError(e.invalidMove);
                try { // TODO: this is horrible, fix it
                    level.makeMove(level.getValidMoves().get(0));
                } catch (InvalidMoveException exception) {
                    return;
                }
            }
        }
    }

    public Move calculateBestMove(GameState currentState) throws NoMovesFoundException {
        // Look through all possible moves and try to make special candies if
        // possible
        // Try to bring special candies together and combine them
        // 
        return null;
    }

    private void printInvalidMoveError(Move move) {
        System.err.println("WARNING! ScorePlayerDelta has suggested an invalidMove " + move + ".");
    }

}
