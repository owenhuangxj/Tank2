package com.owen;

import com.owen.strategy.DefaultFireStrategy;
import com.owen.strategy.FireStrategy;
import com.owen.util.PropertyMgr;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Random;
import java.util.UUID;

/**
 * @author OwenHuang 边界缓冲
 * @since 2022/11/5 20:05
 */
@Getter
@Setter
public class Tank {
    public static final int BOUNDARY_BUFFER = 5;
    public static final int SPEED = 2;
    public static final int WIDTH = ResourceMgr.goodTankDown.getWidth();
    public static final int HEIGHT = ResourceMgr.goodTankDown.getHeight();
    int x = 0;
    int y = 0;

    UUID id = UUID.randomUUID();
    Group group;
    Direction direction;

    boolean alive = true;

    boolean moving = true;

    private Random random = new Random();

    Rectangle rectangle = new Rectangle();

    private FireStrategy fireStrategy;

    {
        try {
            fireStrategy = (FireStrategy) Class.forName(PropertyMgr.getString("fireStrategy")).newInstance();
        } catch (InstantiationException exception) {
            exception.printStackTrace();
        } catch (IllegalAccessException exception) {
            exception.printStackTrace();
        } catch (ClassNotFoundException exception) {
            exception.printStackTrace();
        }
    }


    public Tank(int x, int y, Direction direction, Group group) {
        this.x = x;
        this.y = y;
        this.group = group;
        this.direction = direction;

        rectangle.x = this.x;
        rectangle.y = this.y;
        rectangle.width = Bullet.WIDTH;
        rectangle.height = Bullet.HEIGHT;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public void paint(Graphics graphics) {
        if (!alive) {
            TankFrame.getInstance().enemies.remove(this);
            return;
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
        if (!moving) {
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

        rectangle.x = this.x;
        rectangle.y = this.y;
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
        fireStrategy.fire(this);
    }

    public void die() {
        this.alive = false;
        int ex = this.x + WIDTH / 2 - Explosion.WIDTH / 2;
        int ey = this.y + HEIGHT / 2 - Explosion.HEIGHT / 2;
        // 在子弹和坦克碰撞的位置添加一个爆炸对象
        TankFrame.getInstance().explosions.add(new Explosion(ex, ey));
    }
}