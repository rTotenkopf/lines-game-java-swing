package com.game.lines.logic;

import com.game.lines.entity.Cell;

/**
 * @author Eugene Ivanov on 11.04.18
 */

public class AbstractCheckingMove implements Checkable {

    @Override
    public boolean checkMoveAbility(Cell choosedCell) {
        return true;
    }
}