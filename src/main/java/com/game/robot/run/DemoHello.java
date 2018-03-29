package com.game.robot.run;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Slick Hello-world demo
 * @author maxx
 * Apr 6, 2013
 * Copyright (c) Massimo Musante 2013
 */

public class DemoHello extends BasicGame {

    static {

    }

    private int x = 100;
    private int y = 100;
    private int dx = 1;
    private int dy = 1;

    public DemoHello()
    {
        super("Demo Hello");
    }

    /*
     * (non-Javadoc)
     * @see org.newdawn.slick.BasicGame#init(org.newdawn.slick.GameContainer)
     */
    @Override
    public void init(GameContainer gc) throws SlickException {
    }

    /*
     * (non-Javadoc)
     * @see org.newdawn.slick.Game#render(org.newdawn.slick.GameContainer, org.newdawn.slick.Graphics)
     */
    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException {
        g.drawString("Hello There!", x, y);
    }

    /*
     * (non-Javadoc)
     * @see org.newdawn.slick.BasicGame#update(org.newdawn.slick.GameContainer, int)
     */
    @Override
    public void update(GameContainer gc, int delta) throws SlickException
    {
        if(x>gc.getWidth()) dx = -1;
        if(x<0) dx = 1;
        if(y>gc.getHeight()) dy = -1;
        if(y<0) dy = 1;
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
        Path path = Paths.get(DemoHello.class.getProtectionDomain()
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

        AppGameContainer app = new AppGameContainer(new DemoHello());
        app.setDisplayMode(500, 500, false);
        app.start();
    }
}
