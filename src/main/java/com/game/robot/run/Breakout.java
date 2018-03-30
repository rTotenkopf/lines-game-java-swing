package com.game.robot.run;

import org.newdawn.slick.*;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.game.robot.logic.Constants;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

/**
 * Slick Hello-world demo
 * @author maxx
 * Apr 6, 2013
 * Copyright (c) Massimo Musante 2013
 */

public class Breakout extends BasicGame {

    private int speed = 5;

    private int x = 100;
    private int y = 100;
    private int dx = speed;
    private int dy = speed;

    private Shape rectangle;
    private Shape circle;
    private Image image;

    public Breakout()
    {
        super(Constants.SCREEN_TITLE);
    }

    /*
     * (non-Javadoc)
     * @see org.newdawn.slick.BasicGame#init(org.newdawn.slick.GameContainer)
     */
    @Override
    public void init(GameContainer gc) throws SlickException {
         rectangle = new Rectangle(1, 1, 100, 100);
         circle = new Circle(gc.getWidth() / 2, gc.getHeight() / 2, 10);
         gc.setVSync(true);
         //image = new Image("/src/main/resources/bananas.png");
    }

    /*
     * (non-Javadoc)
     * @see org.newdawn.slick.Game#render(org.newdawn.slick.GameContainer, org.newdawn.slick.Graphics)
     */
    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException {
//        g.drawString("Hello!", x, y);
//        rectangle.setLocation(x, y);
//        rectangle.getTriangles();
//        g.draw(rectangle);
        g.draw(circle);
        circle.setLocation(x, y);
//        g.drawImage(image, x, y);
    }

    /*
     * (non-Javadoc)
     * @see org.newdawn.slick.BasicGame#update(org.newdawn.slick.GameContainer, int)
     */
    @Override
    public void update(GameContainer gc, int delta) throws SlickException
    {
        if (x > gc.getWidth() - circle.getWidth()) {
            dx = -speed;
        }
        if (x < 0) {
            dx = speed;
        }
        if (y > gc.getHeight() - circle.getHeight()) {
            dy = -speed;
        }
        if (y < 0) {
            dy = speed;
        }
        x = x+dx;
        y = y+dy;
    }

    /**
     * Start the demo, no parameters
     * @param args
     * @throws SlickException
     */
    public static void main(String[] args) throws SlickException, URISyntaxException {
        // Получили jar либо папку target/classes
        Path path = Paths.get(Breakout.class.getProtectionDomain()
                .getCodeSource()
                .getLocation()
                .toURI());
        // Переходим к родителю, то есть в папку где лежит jar
        Path parent = path.getParent();

        // Тут у нас распакованные зависимости
        Path unpackPath = parent.resolve("unpack");
        String unpackPathString = unpackPath.toAbsolutePath().normalize().toString();
        
        System.out.println("Unpack Libs: " + unpackPathString);
        
        // org.lwjgl.Sys#run
        System.setProperty("org.lwjgl.librarypath", unpackPathString);
        // net.java.games.input.DirectInputEnvironmentPlugin#run
        System.setProperty("net.java.games.input.librarypath", unpackPathString);

        AppGameContainer app = new AppGameContainer(new Breakout());
        app.setDisplayMode(600, 400, false);
        app.setTargetFrameRate(70);
        app.start();
    }
}
