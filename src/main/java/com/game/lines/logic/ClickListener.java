package com.game.lines.logic;

import com.game.lines.RunLines;
import com.game.lines.common.Common;
import com.game.lines.entity.Cell;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Eugene Ivanov on 31.03.18
 */

public class ClickListener implements MouseListener {
    // Переменная хранит информацию о предыдущей выбранной/нажатой ячейке.
    private Cell previousCell;
    // Логический флаг, устанавливаемый в положение false при выборе новой ячейки
    // и в положение true, если изображение ячейки было перемещено в другую ячейку игрового поля.
    private boolean pictureWasMoved;

    private Logger clickLogger = Logger.getLogger(ClickListener.class.getName());
    private Logger moveLogger = Logger.getLogger(ClickListener.class.getName());

    @Override
    public void mouseClicked(MouseEvent e) {}

    /**
     * В этом методе обрабатываются события нажатий мышью (клики) на ячейки игрового поля.
     * @param e - объект MouseEvent, который сохраняет информацию события.
     */
    @Override
    public void mousePressed(MouseEvent e) {
        // Происходит событие нажатия, которое сохраняется в объекте MouseEvent.
        // Инициализируется выбранная ячейка игрового поля (по которой было нажатие мышью),
        // путём приведения типов Component к Сell (класс - наследник JLabel).
        Cell choosedCell = (Cell) e.getComponent();
        // Инкрементируем (clickCount++) счетчик кликов по ячейке
        // (у пустых ячеек игрового поля счетчик кликов равен 0).
        choosedCell.setClickCount(1);
        // Устанавливается значение флага false (т.к. это новое нажатие на ячейку).
        pictureWasMoved = false;

        // Если мы нажали какую-либо ячейку содержащую изображение, затем нажали пустую ячейку, то
        // изображение "перемещается" в пустую ячейку.
        if (previousCell != null && previousCell.containsImage() && !choosedCell.containsImage() &&
                previousCell.getClickCount() == 1) {
            // Вызов метода проверки возможности совершения хода.
            if (checkMoveAbility()) {
                // Вызов метода в котором происходит перемещение изображения.
                moveImageCell(choosedCell);
                moveLogger.log(Level.INFO, "Cell move completed");
            } else {
                moveLogger.log(Level.INFO, "Cell move impossible");
            }
        }
        // При нажатии на ячейку, границы её выделяются красным цветом.
        // Если мы повторно нажимаем на ту же самую ячейку, то устанавливается стандартное выделение границ
        // (ячейка не активна) и счетчик кликов инкрементируется (значение счетчика становится 2).
        if (previousCell != null && !(previousCell.equals(choosedCell) )) {
            previousCell.setDefaultBorder();
            previousCell.setClickCount(1);
        }
        // Если у ячейки содержащей изображение, счетчик кликов случайно сбрасыватся в 0, то устанавливается на 1.
        // И если, у этой же ячейки, счетчик кликов равен 1, границы ячейки выделяются красным цветом (ячейка активна),
        // иначе, при счетчике не равном 1, выделение границ задается по умолчанию.
        if (choosedCell.containsImage()) {
            if (choosedCell.getClickCount() == 0) {
                choosedCell.setClickCount(1);
            }
            if (choosedCell.getClickCount() == 1) {
                choosedCell.setRedBorder();
            } else {
                choosedCell.setDefaultBorder();
                clickLogger.log(Level.INFO, "Cell released"
                        + " { clickCount = " + choosedCell.getClickCount() + " }");
            }
        }
        if (choosedCell.getClickCount() == 0)
            clickLogger.info("Empty cell" + " { clickCount = " + choosedCell.getClickCount() + " }");
        if (choosedCell.getClickCount() == 1 && !pictureWasMoved)
            clickLogger.info("Cell choosed" + " { clickCount = " + choosedCell.getClickCount() + " }");

        // Если изображение ячейки было успешно перемещено, то границы той ячейки, куда оно было перемещено
        // выделяются по умолчанию, счетчик кликов инкрементируется на 2, т.е. ячейка становится не активной.
        // Таким образом, от игрока ожидается выбор новой ячейки для перемещения, а не перемещение той же самой
        // ячейки.
        if (pictureWasMoved) {
            choosedCell.setDefaultBorder();
            choosedCell.setClickCount(2);
            // Иначе, процесс выбора ячейки с изображением продолжается.
        } else {
            previousCell = choosedCell;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    /**
     * В методе описана логика "перемещения" изображения по игровому полю из одной ячейки в другую.
     * @param choosed - это ссылка на переменную экземпляра choosedCell - "ячейку", которая была нажата последней.
     */
    private void moveImageCell(Cell choosed) {
        // Получаем изображение из "предыдущей" ячейки.
        String pictureColor = previousCell.getPictureColor();
        // Устанавливаем изображение в последнюю нажатую ячейку.
        choosed.setIcon(Common.imageIconMap().get(pictureColor) );
        // Удаляем изображение из предыдущей ячейки.
        previousCell.setIcon(null);
        // Добавляем предыдущую ячейку в список свободных ячеек и удаляем из этого списка последнюю ячейку.
        Common.FREE_CELLS.add(previousCell);
        Common.FREE_CELLS.remove(choosed);
        // Устанавливаем значение флага true (означает, что изображение было перемещено).
        pictureWasMoved = true;
        // Вызываем метод go() класса RunLines, который заполняет 3 свободных ячейки поля изображениями.
        RunLines.go();
    }

    private boolean checkMoveAbility() {
        return true;
    }
}