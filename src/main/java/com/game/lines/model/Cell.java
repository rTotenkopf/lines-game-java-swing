package com.game.lines.model;

import com.game.lines.gui.Grid;
import com.game.lines.gui.GuiManager;
import com.game.lines.logic.Play;
import javafx.util.Pair;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import static com.game.lines.logic.State.RELEASED;
import static com.game.lines.logic.State.SELECTED;

/**
 * Класс Cell абстрагирует отдельную ячейку игрового поля, её координаты, игровое состояние и т.д., а также
 * предоставляет необходимые методы для работы с ячейкой.
 * Поля класса {@link #cellMap} и {@link #emptyCells} хранят информация о всех ячейках в игре.
 * Класс наследует {@link AbstractCell}, который реализует интерфейс {@link com.game.lines.logic.Clickable}.
 * Действия над ячейками выполняются с помощью кликов мыши.
 */
public class Cell extends AbstractCell {

    public Cell(int x, int y) {
        setXx(x);
        setYy(y);
    }

    /**
     * @return true или false в зависимости от того, есть ли изображение в ячейке.
     */
    public boolean containsImage() {
        return this.getIcon() != null;
    }

    /**
     * Установка выделения границ ячейки и статус "ячейка выбрана".
     */
    @Override
    public void select() {
        if ( containsImage() ) {
            setBorder(BorderFactory.createLineBorder(Color.RED, 5));
            setState(SELECTED);
            // ячейке, нажатой в прошлый раз, присваивается состояние текущей нажатой ячейки
            previousCell = this;
        }
    }

    // метод устанавливает стандартные границы ячейки и статус "ячейка освобождена"
    @Override
    public void release() {
        if ( containsImage() ) {
            setState(RELEASED);
        }
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
    }

    // TODO: очень длинный метод
    /**
     * Реализация абстрактного метода {@link AbstractCell#getNeighbors()}.
     * В методе выполняется поиск "соседей" для любой ячейки. "Соседями" считаются ячейки, находящиеся возле данной
     * ячейки, за исключением тех, что находятся рядом по-диагонали.
     *
     * @return список, содержащий ячейки, находящиеся по соседству от данной ячейки.
     */
    public List<Cell> getNeighbors() {
        List<Cell> neighborsList = new LinkedList<>();
        int gridLength = Grid.getGridLength();
        // поиск соседей для ячеек, располагающихся не у края поля
        if ( (getXx() > 1 && getXx() < gridLength) && (getYy() > 1 && getYy() < gridLength) ) {
            neighborsList.add(getCellMap().get(new Pair<>(getXx(), getYy() - 1) ));
            neighborsList.add(getCellMap().get(new Pair<>(getXx(), getYy() + 1) ));
            neighborsList.add(getCellMap().get(new Pair<>(getXx() - 1, getYy()) ));
            neighborsList.add(getCellMap().get(new Pair<>(getXx() + 1, getYy()) ));
        }
        // поиск соседей для ячеек, занимающих крайний нижний или крайний верхний ряд,
        // (за исключением крайних правой и левой ячеек)
        else if ( getXx() > 1 && getXx() < gridLength ) {
            neighborsList.add(getCellMap().get(new Pair<>(getXx() - 1, getYy()) ));
            neighborsList.add(getCellMap().get(new Pair<>(getXx() + 1, getYy()) ));
            if ( getYy() == 1 ) {
                neighborsList.add(getCellMap().get(new Pair<>(getXx(), getYy() + 1)));
            } else if ( getYy() == gridLength ) {
                neighborsList.add(getCellMap().get(new Pair<>(getXx(), getYy() - 1)));
            }
        }
        // поиск соседей для ячеек, занимающих крайний левый и крайний правый ряд,
        // (за исключением крайних нижней и верхней ячеек)
        else if ( getYy() > 1 && getYy() < gridLength ) {
            neighborsList.add(getCellMap().get(new Pair<>(getXx(), getYy() + 1) ));
            neighborsList.add(getCellMap().get(new Pair<>(getXx(), getYy() - 1) ));
            if ( getXx() == 1 ) {
                neighborsList.add(getCellMap().get(new Pair<>(getXx() + 1, getYy()) ));
            } else if ( getXx() == gridLength ) {
                neighborsList.add(getCellMap().get(new Pair<>(getXx() - 1, getYy()) ));
            }
        }
        // поиск соседей для ячеек, находящихся "в углах" игрового поля
        else if ( getXx() == 1 ) {
            neighborsList.add(getCellMap().get(new Pair<>(getXx() + 1, getYy()) ));
            if ( getYy() == 1 ) {
                neighborsList.add(getCellMap().get(new Pair<>(getXx(), getYy() + 1) ));
            } else if ( getYy() == gridLength ) {
                neighborsList.add(getCellMap().get(new Pair<>(getXx(), getYy() - 1) ));
            }
        } else if ( getXx() == gridLength ) {
            neighborsList.add(getCellMap().get(new Pair<>(getXx() - 1, getYy()) ));
            if ( getYy() == 1 ) {
                neighborsList.add(getCellMap().get(new Pair<>(getXx(), getYy() + 1) ));
            } else if (getYy() == gridLength) {
                neighborsList.add(getCellMap().get(new Pair<>(getXx(), getYy() - 1) ));
            }
        }

        return neighborsList;
    }

    /**
     * Метод обрабатывает клик мыши по ячейке.
     * В зависимости от состояния конкретной ячейки, выполняется выделение или снятие выделения с ячейки,
     * а также инициируется игровой ход (перемещение изображения из одной ячейки в другую).
     *
     * @param e событие нажатия на ячейку.
     */
    @Override
    public void mousePressed(MouseEvent e) {
        Cell currentCell = this;
        switch ( currentCell.getState() ) {
            // если ячейка уже выбрана (выделена цветом), то при нажатии на неё - выделение снимается
            case SELECTED:
                currentCell.release();
                cellLogger.info("Cell released");
                GuiManager.getInfoLabel().setText("Ячейка освобождена.");
                break;
            // если ячейка не была выделена, то она выбирается (выделяется цветом), с предыдущей выбранной ячейки,
            // выделение снимается
            case RELEASED:
                if ( !Objects.isNull(previousCell) ) {
                    previousCell.release();
                }
                currentCell.select();
                cellLogger.info("Cell selected");
                GuiManager.getInfoLabel().setText("Шар выбран.");
                break;
            // если ячейка пуста, то проверяется состояние предыдущей ячейки
            // если предыдущая ячейка была выбрана, то изображение из неё переносится в текущую (пустую) ячейку
            // таким образом, осуществляется игровой ход (перемещение изображения)
            case EMPTY:
                GuiManager.getInfoLabel().setText("Выберите шар!");
                // выполнение игрового хода. Метод moveInit возвращает true, если ход выполнен успешно
                if ( !Objects.isNull(previousCell) && (previousCell.getState() == SELECTED) ) {
                    boolean moveComplete = Play.moveInit(previousCell, currentCell);
                    if ( moveComplete ) {
                        previousCell.release();
                        previousCell = null;
                    }
                }
//                else {
//                    cellLogger.info("emptyCells.size() = " + emptyCells.size());
//                }
                break;
        }
//        if ( this.state == State.SELECTED) { cellLogger.info("Cell selected"); }
//        if ( this.state == State.RELEASED) { cellLogger.info("Cell released"); }
//        if ( this.state == State.EMPTY )   { cellLogger.info("Cell is empty"); }
    }
}