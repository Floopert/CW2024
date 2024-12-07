package com.example.demo.activeActors.planes.movePatternLogic;

import java.util.*;


/**
 * Represents a random vertical movement pattern for an actor.
 */
public class RandomYMovePattern {

    private final int MOVE_FREQUENCY_PER_CYCLE;
    private final int VERTICAL_VELOCITY;
    private final int MAX_FRAMES_WITH_SAME_MOVE;
    private static final int ZERO = 0;
    
    private int indexOfCurrentMove;
    private int consecutiveMovesInSameDirection;
    private final List<Integer> movePattern;



    /**
     * Constructs a new RandomYMovePattern with the specified parameters.
     *
     * @param moveFrequencyPerCycle the number of moves per cycle
     * @param maxFramesWithSameMove the maximum number of frames with the same move
     * @param verticalVelocity the vertical velocity for the moves
     */
    public RandomYMovePattern(int moveFrequencyPerCycle, int maxFramesWithSameMove, int verticalVelocity) {
        MOVE_FREQUENCY_PER_CYCLE = moveFrequencyPerCycle;
        MAX_FRAMES_WITH_SAME_MOVE = maxFramesWithSameMove;
        VERTICAL_VELOCITY = verticalVelocity;
        movePattern = new ArrayList<>();
        indexOfCurrentMove = 0;
        consecutiveMovesInSameDirection = 0;
    }


    /**
     * Initializes the move pattern by creating a list of vertical moves and shuffling them.
     */
    public void initializeMovePattern() {
        for (int i = 0; i < MOVE_FREQUENCY_PER_CYCLE; i++) {
            movePattern.add(VERTICAL_VELOCITY);
            movePattern.add(-VERTICAL_VELOCITY);
            movePattern.add(ZERO);
        }
        Collections.shuffle(movePattern);
    }


    /**
     * Gets the next move in the pattern.
     *
     * @return the next vertical move
     */
    public int getNextMove() {
        int currentMove = movePattern.get(indexOfCurrentMove);
        consecutiveMovesInSameDirection++;
        if (consecutiveMovesInSameDirection == MAX_FRAMES_WITH_SAME_MOVE) {
            Collections.shuffle(movePattern);
            consecutiveMovesInSameDirection = 0;
            indexOfCurrentMove++;
        }
        if (indexOfCurrentMove == movePattern.size()) {
            indexOfCurrentMove = 0;
        }
        return currentMove;
    }

}