import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * @author Eugene Ivanov on 26.03.2018
 */

public class ButtonGrid {

    private JFrame frame;
    private JLabel[][] grid;
    private Border border;
    private ImageIcon targetImage;
    private Map<Integer, JLabel> cellMap;
    private Function<Integer, Integer> keyMapFunction;

    public void createGui(int width, int height, int cellX, int cellY){ // constructor of class
        frame = new JFrame("Simple Animation"); // create a frame
        frame.setLayout(new GridLayout(width,height)); // set layout of frame

        grid = new JLabel[width][height]; // create a grid from many cells (size in choose)

        border = BorderFactory.createLineBorder(Color.BLACK, 1);

        URL appIconURL = getClass().getResource("/bananas.png");
        URL targetImageURL = getClass().getResource("/cherries.png");

        // checking for existing image files
        if (appIconURL == null) {
            System.out.println("Could not find image!");
        } else {
            frame.setIconImage(new ImageIcon(appIconURL).getImage());
        }

        if (targetImageURL == null) {
            System.out.println("Could not find image!");
        } else {
            targetImage = new ImageIcon(getClass().getResource("/cherries.png"));
        }

        cellX--;
        cellY--;

        cellMap = new HashMap<>();
        Integer key;

        for (int y = height - 1; y >= 0; y--) {
            for (int x = 0; x < width; x++) {
                // create a new button/other gui-element
                grid[x][y] = new JLabel("(" + (x + 1) + "," + (y + 1) + ")");
                grid[x][y].setBorder(border);

                key = Integer.parseInt(String.valueOf(x + 1) + String.valueOf(y + 1));
                cellMap.put(key, grid[x][y]);

                System.out.println("key = " + key);

                if (x == cellX && y == cellY) {
                    grid[cellX][cellY].setBackground(Color.YELLOW);
                    grid[cellX][cellY].setIcon(targetImage);
                    grid[cellX][cellY].setOpaque(true);
                }
                frame.add(grid[x][y]); // add element at grid
            }
        }
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // default close operation
        frame.pack(); // set appropriate frame size
        frame.setLocation(500, 100); // location of fram at user screen
        frame.setSize(500, 500); // standard size of frame
        frame.setVisible(true); // set option "visible" of frame
    }

    public static void main(String[] args) {
        ButtonGrid buttonGrid = new ButtonGrid();
        buttonGrid.createGui(   5,5, 1, 2);

        buttonGrid.cellMap.get(55).setBackground(Color.GREEN);
        buttonGrid.cellMap.get(55).setOpaque(true);

        //buttonGrid.frame.repaint();
    }
}