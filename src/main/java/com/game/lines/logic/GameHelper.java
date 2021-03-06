package com.game.lines.logic;

import com.game.lines.gui.GuiManager;
import com.game.lines.model.Cell;
import com.game.lines.gui.EndingModal;

import javax.swing.*;

import java.util.List;
import java.util.logging.Logger;

import static com.game.lines.util.ResourceManager.BALLS;
import static com.game.lines.model.AbstractCell.getCellMap;
import static com.game.lines.model.AbstractCell.getEmptyCells;
import static com.game.lines.logic.State.EMPTY;
import static com.game.lines.logic.State.RELEASED;
import static com.game.lines.logic.Play.*;

public class GameHelper {
    /**
     * Старт новой игры при нажатии на кнопку "Новая игра" в модальном диалоге, за который отвечает класс
     * {@link EndingModal}.
     * Происходит сброс всех игровых параметров и коллекций. Инициируются новый игровой процесс.
     */
    public static void startNewGame() {
        setBallsCounter(0);
        setPointsCounter(0);
        getEmptyCells().clear();
        GuiManager.setDefaultLabelsInfo();

        getCellMap().values().forEach( cell -> {
            cell.release();
            cell.setState(EMPTY);
            cell.setIcon(null);
            getEmptyCells().add(cell);
        });
        initGameProcess();
    }

    /**
     * Инициализация игрового поцесса в начале игры.
     * В отдельном потоке, с задержкой в 1 секунду, в случайные ячейки генерируется 5 изображений случайного цвета.
     */
    public static void initGameProcess() {
        new Thread( () -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // вставка рандомных изображений в сетку
            GuiManager.getInfoLabel().setText("Начата новая игра.");
            generateRandomImages(GuiManager.getInfoLabel().getText(), false, 5);
        }).start();
    }

    /**
     * Вставка изображений в ячейки. Происходит только в начале игры и когда линия не была удалена в процессе игры.
     *
     * @param textInfo       сообщение, переданное в метод, для отображения на экране состояния хода игры
     * @param lineWasDeleted флаг события удаления линии
     * @param amount         количество ячеек для рандомного заполнения изображениями (зависит от настроек игры)
     */
    static void generateRandomImages(String textInfo, boolean lineWasDeleted, int amount) {
        if ( !lineWasDeleted ) {
            GuiManager.getInfoLabel().setText( textInfo );
            for (int i = 0; i < amount; i++) {
                Cell cell = getRandomCell( getEmptyCells() ); // Получаем рандомную ячейку из массива пустых ячеек.
                int index = (int) (Math.random() * BALLS.length); // Подбираем случайный индекс.
                cell.setIcon((ImageIcon) BALLS[index]); // Устанавливаем случайное изображение в ячейку.
                cell.setState(RELEASED); // Устанавливаем состояние "ячейка освобождена".
                getEmptyCells().remove(cell); // Удаляем ячейку из списка пустых ячеек.
            }
        }
    }

    /**
     * Получение случайной пустой ячейки.
     *
     * @param freeCells список пустых ячеек.
     * @return случайная пустая ячейка.
     */
    private static Cell getRandomCell(List<Cell> freeCells) {
        int index = (int) (Math.random() * freeCells.size() );
        return freeCells.get(index);
    }

    /**
     * Начисление очков за удаленную линию, исходя из количества шаров в ней.
     * Чем больше шаров, тем выше коэффициент начисления очков.
     * @param lineSize количество шаров.
     */
    static void accuralPoints(int lineSize) {
        double ratio = 2.1 + (double) (lineSize - 5) / 10;
        int pointsValue = getPointsCounter() + (int) (lineSize * ratio);
        int ballsValue =  getBallsCounter() + lineSize;
        setPointsCounter(pointsValue);
        setBallsCounter(ballsValue);
        GuiManager.getPointsLabel().setText("Очки: " + String.valueOf(getPointsCounter()));
        GuiManager.getBallsLabel().setText(String.valueOf(getBallsCounter()) + ": Шары");
    }

    /**
     *  Проверка условия, при выполнении которого игра должна завершиться.
     */
    static void checkGameEndingCondition() {
        if ( getEmptyCells().size() <= 3 ) {
            Logger.getGlobal().warning("End of the game!");
            EndingModal.init();
            GuiManager.getInfoLabel().setText("Игра окончена!");
        }
    }
}