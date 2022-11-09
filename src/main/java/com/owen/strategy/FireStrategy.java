package com.owen.strategy;

import com.owen.Tank;

/**
 * @author OwenHuang
 * @since 2022/11/9 22:27
 */
public interface FireStrategy {

    /**
     * 发射炮弹
     *
     * @param tank 坦克
     */
    void fire(Tank tank);
}
