package com.game.lines.logic;

import com.game.lines.entity.Cell;

/**
 * @author Eugene Ivanov on 11.04.18
 */

public interface Checkable {

    boolean checkMoveAbility(Cell choosedCell);
}