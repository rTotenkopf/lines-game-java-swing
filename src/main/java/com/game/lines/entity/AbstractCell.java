package com.game.lines.entity;

import com.game.lines.gui.MainPanel;
import com.game.lines.logic.Clickable;
import javafx.util.Pair;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * AbstractCell - абстрактный класс, который реализует часть логики класса Cell и "выполняет контракт"
 * с интерфейсом MouseListener, реализуя методы, обязательные для реализации, но не нужные для игрового процесса.
 *
 * @author Eugene Ivanov on 11.04.18
 */

abstract class AbstractCell extends JLabel implements Clickable {

    // Конструктор класса-наследника, в котором к объекту добавляется слушатель нажатий мыши (т.е. текущий объект),
    // т.к. данный класс или его потомки должны реализовать методы MouseListener.
    AbstractCell() {
        addMouseListener(this);
    }

    /**
     * @return длину стороны сетки/игрового поля.
     */
    public static int getGridLength() {
        return MainPanel.grid.length;
    }

    /**
     * Возвращаемое значение будет использоваться для маппинга изображения по части названия файла
     * этого изображения, содержащей название цвета.
     * @return String, содержащий название цвета картинки, отображаемой в ячейке.
     */
    public String getImageColor() {
        String iconAbsolutePath = this.getIcon().toString();
        String[] pathArray = iconAbsolutePath.split("/");
        String[] fileName = pathArray[pathArray.length - 1].split("[-]");
        return fileName[0];
    }

    public abstract Pair<Integer, Integer> getCoordinates();

    public abstract List<? extends JLabel> getNeighbors();

    @Override
    public void mouseClicked(MouseEvent e) { }

    @Override
    public void mouseEntered(MouseEvent e) { }

    @Override
    public void mouseExited(MouseEvent e) { }

    @Override
    public void mouseReleased(MouseEvent e) { }
}