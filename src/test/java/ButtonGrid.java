import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.net.URL;

public class ButtonGrid {

    private JFrame frame; //создаем фрейм
    private JLabel[][] grid2; //создаем грид (по-русски сетку из ячеек)
    private Border border;

    public void createGui(int width, int height, int cellX, int cellY){ //конструктор
        frame = new JFrame("Simple Animation"); //создает фрейм
        frame.setLayout(new GridLayout(width,height)); //устанавливаем размещение

        grid2 = new JLabel[width][height];

        border = BorderFactory.createLineBorder(Color.BLACK, 1);

        URL url = getClass().getResource("/bananas.png");
        if (url == null)
            System.out.println( "Could not find image!" );
        else
            frame.setIconImage(new ImageIcon(url).getImage());
        
        cellX--;
        cellY--;
        for (int y = height - 1; y >= 0; y--) {
            for (int x = 0; x < width; x++) {
                grid2[x][y] = new JLabel("(" + (x + 1) + "," + (y + 1) + ")"); //создает новый кнопку/элемент
                grid2[x][y].setBorder(border);
                if (x == cellX && y == cellY) {
                    grid2[cellX][cellY].setBackground(Color.YELLOW);

                    grid2[cellX][cellY].setIcon(new ImageIcon(getClass().getResource("/cherries.png")));

                    grid2[cellX][cellY].setOpaque(true);
                }
                frame.add(grid2[x][y]); //добавляет кнопки на сетку
            }
        }
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack(); //устанавливает соответствующий размер фрейма
        frame.setLocation(500, 100);
        frame.setSize(500, 500);
        frame.setVisible(true); //делает фрейм видимым
    }

    public static void main(String[] args) {
        ButtonGrid buttonGrid = new ButtonGrid();
        buttonGrid.createGui(   5,5, 5, 5);
    }
}