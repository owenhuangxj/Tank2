package com.owen;

import java.awt.*;

/**
 * @author OwenHuang
 * @since 2022/11/5 20:05
 */
public class Tank {
    private static final int SPEED = 2;
    public static final int WIDTH = 50;
    public static final int HEIGHT = 50;
    int x = 0;
    int y = 0;
    Group group;
    private Direction direction;

    private TankFrame tankFrame;

    private boolean alive = true;

    private boolean moving = false;


    public Tank(int x, int y, Direction direction, Group group, TankFrame tankFrame) {
        this.x = x;
        this.y = y;
        this.group = group;
        this.direction = direction;
        this.tankFrame = tankFrame;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public void paint(Graphics graphics) {
        if (!alive) {
            tankFrame.enemies.remove(this);
        }
        Color color = graphics.getColor();
        graphics.setColor(Color.yellow);
        graphics.setColor(color);
        switch (direction) {
            case LEFT:
                graphics.drawImage(this.group == Group.GOOD ? ResourceMgr.goodTankLeft : ResourceMgr.badTankLeft, x, y, null);
                break;
            case RIGHT:
                graphics.drawImage(this.group == Group.GOOD ? ResourceMgr.goodTankRight : ResourceMgr.badTankRight, x, y, null);
                break;
            case UP:
                graphics.drawImage(this.group == Group.GOOD ? ResourceMgr.goodTankUp : ResourceMgr.badTankUp, x, y, null);
                break;
            case DOWN:
                graphics.drawImage(this.group == Group.GOOD ? ResourceMgr.goodTankDown : ResourceMgr.badTankDown, x, y, null);
                break;
        }
        move();
    }

    private void move() {
        if (moving) {
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
        }
    }

    public void fire() {
        int bw = this.x + WIDTH / 2 - Bullet.WIDTH / 2;
        int bh = this.y + HEIGHT / 2 - Bullet.HEIGHT / 2;
        this.tankFrame.bullets.add(new Bullet(bw, bh, this.direction, tankFrame));
    }

    public void die() {
        this.alive = false;
    }
}