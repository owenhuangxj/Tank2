package com.owen;

import java.awt.*;
import java.util.List;
import java.util.UUID;

/**
 * @author OwenHuang
 * @since 2022/11/5 20:05
 */
public class Bullet {
    public static final int WIDTH = 10;
    public static final int HEIGHT = 10;
    private static final int SPEED = 10;
    final Group group;
    private int x, y;
    private Direction direction;

    private TankFrame tankFrame;

    boolean alive = true;
    Rectangle rectangle = new Rectangle();

    UUID playerId;

    public Bullet(UUID playerId, int x, int y, Group group, Direction direction, TankFrame tankFrame) {
        this.x = x;
        this.y = y;
        this.group = group;
        this.playerId = playerId;
        this.direction = direction;
        this.tankFrame = tankFrame;
    }

    public void die() {
        this.alive = false;
    }

    public void collideWithTank(Tank tank) {
        // 如果玩家并且是玩家发射的子弹不用进行碰撞处理
        if (tank.id == this.playerId) {
            return;
        }
        // 如果是同一战线发射的子弹不伤害战友
        if (this.group == tank.group) {
            return;
        }
        rectangle.x = tank.x;
        rectangle.y = tank.y;
        rectangle.width = Tank.WIDTH;
        rectangle.height = Tank.HEIGHT;
        tank.rectangle.x = this.x;
        tank.rectangle.y = this.y;
        tank.rectangle.width = WIDTH;
        tank.rectangle.height = HEIGHT;
        if (rectangle.intersects(tank.rectangle)) {
            this.die();
            tank.die();
        }
    }

    private void move() {
        switch (direction) {
            case UP:
                y -= SPEED;
                break;
            case DOWN:
                y += SPEED;
                break;
            case LEFT:
                x -= SPEED;
                break;
            case RIGHT:
                x += SPEED;
                break;
            default:
                break;
        }
        if (x <= 0 || y <= 0 || x >= TankFrame.GAME_WIDTH || y >= TankFrame.GAME_HEIGHT) {
            alive = false;
            return;
        }
    }

    /**
     * 游戏桌面的paint方法最终会调用桌面里面的所有元素的paint方法，所以每个元素的处理逻辑放在元素的paint方法里面
     *
     * @param graphics 图形的上下文引用，所有画图操作都通过这个对象进行实现
     */
    public void paint(Graphics graphics) {
        /**
         * 此处需要将子弹从游戏桌面移除，所以需要持有游戏桌面的引用
         */
        if (!alive) {
            tankFrame.bullets.remove(this);
        }
        Color color = graphics.getColor();
        graphics.setColor(Color.RED);
        graphics.setColor(color);
        switch (direction) {
            case LEFT:
                graphics.drawImage(ResourceMgr.bulletLeft, x, y, null);
                break;
            case RIGHT:
                graphics.drawImage(ResourceMgr.bulletRight, x, y, null);
                break;
            case UP:
                graphics.drawImage(ResourceMgr.bulletUp, x, y, null);
                break;
            case DOWN:
                graphics.drawImage(ResourceMgr.bulletDown, x, y, null);
                break;
        }
        move();
        List<Tank> tanks = tankFrame.enemies;
        for (int idx = 0; idx < tanks.size(); idx++) {
            if (tanks.get(idx).group == Group.BAD) {
                collideWithTank(tanks.get(idx));
            }
        }
    }
}