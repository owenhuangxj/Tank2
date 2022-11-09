package com.owen;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author OwenHuang
 * @since 2022/11/6 15:20
 */
public class ResourceMgr {
    public static BufferedImage goodTankLeft, goodTankRight, goodTankUp, goodTankDown;
    public static BufferedImage badTankLeft, badTankRight, badTankUp, badTankDown;
    public static BufferedImage bulletLeft, bulletRight, bulletUp, bulletDown;
    public static BufferedImage[] explosions = new BufferedImage[16];

    static {
        try {
            goodTankUp = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/GoodTank1.png"));
            goodTankRight = ImageUtil.rotateImage(goodTankUp, 90);
            goodTankDown = ImageUtil.rotateImage(goodTankUp, 180);
            goodTankLeft = ImageUtil.rotateImage(goodTankUp, -90);
            badTankUp = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/BadTank1.png"));
            badTankRight = ImageUtil.rotateImage(badTankUp, 90);
            badTankDown = ImageUtil.rotateImage(badTankUp, 180);
            badTankLeft = ImageUtil.rotateImage(badTankUp, -90);
            bulletUp = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/bulletU.png"));
            bulletRight = ImageUtil.rotateImage(bulletUp, 90);
            bulletDown = ImageUtil.rotateImage(bulletUp, 180);
            bulletLeft = ImageUtil.rotateImage(bulletUp, -90);
            for (int idx = 0; idx < explosions.length; idx++) {
                explosions[idx] = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/e" + (idx + 1) + ".gif"));
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}