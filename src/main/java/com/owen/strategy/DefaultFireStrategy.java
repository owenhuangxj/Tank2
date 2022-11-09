package com.owen.strategy;

import com.owen.Bullet;
import com.owen.Tank;
import com.owen.TankFrame;

/**
 * @author OwenHuang
 * @since 2022/11/9 22:28
 */
public class DefaultFireStrategy implements FireStrategy {

    @Override
    public void fire(Tank tank) {
        int bx = tank.getX() + Tank.WIDTH / 2 - Bullet.WIDTH / 2;
        int by = tank.getY() + Tank.HEIGHT / 2 - Bullet.HEIGHT / 2;
        // 坦克发射的时候将自己的id传给炮弹，炮弹在进行碰撞判断时用坦克的id进行排除玩家坦克不进行碰撞判断
        TankFrame.getInstance().getBullets().add(new Bullet(tank.getId(), bx, by, tank.getGroup(), tank.getDirection()));
    }
}
