package com.owen;

import java.awt.*;

/**
 * @author OwenHuang
 * @since 2022/11/9 20:56
 */
public class Explosion {
    public static final int WIDTH = ResourceMgr.explosions[0].getWidth();
    public static final int HEIGHT = ResourceMgr.explosions[0].getHeight();


    int x, y;

    int step;

    public Explosion(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void paint(Graphics graphics) {
        // 每次刷新就从第一张开始画一张图
        graphics.drawImage(ResourceMgr.explosions[step++], x, y, null);
        // 如果已经画完需要将爆炸对象从集合中移除：子弹碰撞方法里面添加一个爆炸对象
        if (step >= ResourceMgr.explosions.length) {
            TankFrame.getInstance().explosions.remove(this);
        }
    }
}
