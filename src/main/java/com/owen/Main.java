package com.owen;

import java.awt.*;

/**
 * @author OwenHuang
 * @since 2022/11/6 12:51
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        Frame frame = new TankFrame();
        while (true) {
            Thread.sleep(10);
            // repaint会调用所有元素的paint方法
            frame.repaint();
        }
    }
}
