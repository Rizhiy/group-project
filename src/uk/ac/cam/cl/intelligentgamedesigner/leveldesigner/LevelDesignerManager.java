package uk.ac.cam.cl.intelligentgamedesigner.leveldesigner;

import uk.ac.cam.cl.intelligentgamedesigner.coregame.Design;
import uk.ac.cam.cl.intelligentgamedesigner.coregame.GameMode;
import uk.ac.cam.cl.intelligentgamedesigner.coregame.GameState;
import uk.ac.cam.cl.intelligentgamedesigner.simulatedplayers.NoMovesFoundException;
import uk.ac.cam.cl.intelligentgamedesigner.simulatedplayers.ScorePlayerAlpha;
import uk.ac.cam.cl.intelligentgamedesigner.simulatedplayers.SimulatedPlayerBase;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LevelDesignerManager {
    private long seed = 1;
    private Specification specification;
    private Random originalRandom;
    protected LevelDesigner levelDesigner;

    public LevelDesignerManager (Specification specification) {
        this.specification = specification;

        this.originalRandom = new Random(seed);
        this.levelDesigner = new LevelDesigner(this, this.originalRandom);
    }

    public void run() {
        this.levelDesigner.run();
    }

    public List<LevelRepresentation> getPopulation(int size) {
        List<LevelRepresentation> population = new ArrayList<>();

        switch (specification.getGameMode()) {
            case HIGHSCORE:
                for (int i = 0; i < size; i++) {
                    population.add(new ArrayLevelRepresentationScore(originalRandom));
                }
                break;
            case JELLY:
                for (int i = 0; i < size; i++) {
                    population.add(new ArrayLevelRepresentationJelly(originalRandom));
                }
                break;
            default:
                for (int i = 0; i < size; i++) {
                    population.add(new ArrayLevelRepresentationIngredients(originalRandom));
                }
        }

        return population;
    }

    /**
     * This method will run appropriate simulated players on the given design and evaluate how difficult the level is
     * based on their performance. The difficulty fitness will be a value between 0 and 1.
     *
     * @param design    The design of the level
     * @return          The difficult fitness, 0 <= d <= 1
     */
    public double getDifficultyFitness (Design design) {

        // Create a new GameState with the given design
        GameState gameState = new GameState(design);

        // Select the appropriate simulated player(s) to tackle the level
        SimulatedPlayerBase simulatedPlayer = null;
        switch (design.getMode()) {
            case HIGHSCORE:
                simulatedPlayer = new ScorePlayerAlpha(gameState);
                break;
            case JELLY:
                System.err.println("Jelly level players are not yet supported.");
                break;
            default:
                System.err.println("Ingredients level players are not yet supported.");
                break;
        }

        if (simulatedPlayer != null) {
            try {
                // Run the player(s) on the level design
                simulatedPlayer.solve();

                double difficulty;
                switch (design.getMode()) {
                    case HIGHSCORE:
                        difficulty = evaluateScoreLevelPerformance(gameState, design);
                        break;
                    case JELLY:
                        difficulty = evaluateJellyLevelPerformance(gameState, design);
                        break;
                    default:
                        difficulty = evaluateIngredientsLevelPerformance(gameState, design);
                        break;
                }

                return difficulty;
            } catch (NoMovesFoundException e) {
                // This shouldn't ever occur
                e.printStackTrace();
            }
        }

        return 0;
    }

    /**
     * Currently, this will return a fitness between 0 (trivial) and 1 (impossible).
     *
     * The simulated players play until they have no more moves remaining, thus the only values that can be used to
     * judge performance are the score and the target score. The values will be returned following this scale:
     *
     * 0 -------------------------------------- 0.5 -------------------------------------- 1
     * EASY                                     OK                                      HARD
     * score - target score >> 0         score - target = 0              score - target << 0
     *
     * For this I will use the sigmoid function centered on the target score and scaled appropriately.
     *
     * @param gameState     The game on which the simulated player has played
     * @param design        The design of the game level
     * @return
     */
    private double evaluateScoreLevelPerformance (GameState gameState, Design design) {
        double center = design.getObjectiveTarget();
        double x = gameState.getScore();

        return 1 - (1 / (1 + (Math.exp(-(x - center) / center))));
    }

    private double evaluateJellyLevelPerformance (GameState gameState, Design design) {
        // TODO: Complete this
        return 0;
    }

    private double evaluateIngredientsLevelPerformance (GameState gameState, Design design) {
        // TODO: Complete this
        return 0;
    }
}
