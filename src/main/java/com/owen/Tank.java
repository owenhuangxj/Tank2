package com.owen;

import java.awt.*;
import java.util.Random;
import java.util.UUID;

/**
 * @author OwenHuang 边界缓冲
 * @since 2022/11/5 20:05
 */
public class Tank {
    public static final int BOUNDARY_BUFFER = 5;
    private static final int SPEED = 2;
    public static final int WIDTH = 50;
    public static final int HEIGHT = 50;
    int x = 0;
    int y = 0;

    UUID id = UUID.randomUUID();
    Group group;
    Direction direction;

    private TankFrame tankFrame;

    private boolean alive = true;

    private boolean moving = true;

    private Random random = new Random();

    Rectangle rectangle = new Rectangle();


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
        if (!alive) {
            return;
        }
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
        // 让敌军坦克以5%的概率移动
        if (this.group == Group.BAD && random.nextInt(100) >= 95) {
            moveEnemy();
        }
        // 让敌军坦克以5%的概率发射子弹
        if (this.group == Group.BAD && random.nextInt(100) >= 95) {
            this.fire();
        }
        checkBoundary();
    }

    /**
     * 让坦克随机方向移动:设置direction为随机方向
     */
    private void moveEnemy() {
        this.direction = Direction.values()[random.nextInt(Direction.values().length)];
    }

    private void checkBoundary() {
        // 处理坦克超出边界的问题
        if (x <= BOUNDARY_BUFFER) {
            x = BOUNDARY_BUFFER;
        }
        if (x >= TankFrame.GAME_WIDTH - BOUNDARY_BUFFER - WIDTH) {
            x = TankFrame.GAME_WIDTH - BOUNDARY_BUFFER - WIDTH;
        }
        if (y <= BOUNDARY_BUFFER) {
            y = BOUNDARY_BUFFER;
        }
        if (y >= TankFrame.GAME_HEIGHT - BOUNDARY_BUFFER - HEIGHT) {
            y = TankFrame.GAME_HEIGHT - BOUNDARY_BUFFER - HEIGHT;
        }
    }

    public void fire() {
        int bw = this.x + WIDTH / 2 - Bullet.WIDTH;
        int bh = this.y + HEIGHT / 2 - Bullet.HEIGHT;
        // 坦克发射的时候将自己的id传给炮弹，炮弹在进行碰撞判断时用坦克的id进行排除玩家坦克不进行碰撞判断
        this.tankFrame.bullets.add(new Bullet(this.id, bw, bh, group, this.direction, this.tankFrame));
    }

    public void die() {
        this.alive = false;
    }
}