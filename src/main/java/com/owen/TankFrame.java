package com.owen;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * @author OwenHuang
 * @since 2022/11/5 20:05
 */
public class TankFrame extends Frame {
    static final int GAME_WIDTH = 1080;
    static final int GAME_HEIGHT = 960;

    private Image screenImage;
    private Tank playerTank = new Tank(300, 300, Direction.DOWN, Group.GOOD, this);

    List<Tank> enemies = new ArrayList<>();

    List<Bullet> bullets = new ArrayList<>();

    public TankFrame() {
        this.setSize(GAME_WIDTH, GAME_HEIGHT);
        this.setResizable(false);
        this.setTitle("Tank War");
        this.setVisible(true);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        this.addKeyListener(new TankPlayerKeyListener(playerTank));
        enemies.add(new Tank(100, 100, Direction.DOWN, Group.BAD, this));
        enemies.add(new Tank(200, 100, Direction.DOWN, Group.BAD, this));
        enemies.add(new Tank(300, 100, Direction.DOWN, Group.BAD, this));
        enemies.add(new Tank(400, 100, Direction.DOWN, Group.BAD, this));
        enemies.add(new Tank(500, 100, Direction.DOWN, Group.BAD, this));
    }

    @Override
    public void paint(Graphics graphics) {
        Color color = graphics.getColor();
        graphics.setColor(Color.WHITE);
        Font font = graphics.getFont();
        Font title = new Font("title", Font.BOLD, 25);
        graphics.setFont(title);
        graphics.drawString("敌人坦克数量:" + enemies.size(), 10, 50);
        // 画坦克
        playerTank.paint(graphics);
        for (int index = 0; index < enemies.size(); index++) {
            enemies.get(index).paint(graphics);
        }
        // 画子弹
        for (int index = 0; index < bullets.size(); index++) {
            bullets.get(index).paint(graphics);
        }
        graphics.setFont(font);
        graphics.setColor(color);
    }

    /**
     * 通过更新方法中在内存里面画一张和游戏桌面大小一样的画布然后整体对游戏桌面进行刷新，消除游戏桌面的元素闪烁的问题
     * @param graphics the specified Graphics window
     */
    @Override
    public void update(Graphics graphics) {
        if (screenImage == null) {
            screenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
        }
        Graphics screenImageGraphics = screenImage.getGraphics();
        Color color = screenImageGraphics.getColor();
        screenImageGraphics.setColor(Color.BLACK);
        screenImageGraphics.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
        screenImageGraphics.setColor(color);
        paint(screenImageGraphics);
        graphics.drawImage(screenImage, 0, 0, null);
    }
}