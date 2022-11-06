package com.owen;

import org.junit.Assert;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author OwenHuang
 * @since 2022/11/6 14:58
 */
public class ImageLoadTest {
    @Test
    public void testLoad(){
        try {
            BufferedImage image = ImageIO.read(new File("E:\\projects\\Tank2\\src\\main\\resources\\images\\GoodTank1.png"));
            Assert.assertNotNull(image);
            ImageIO.read(ImageLoadTest.class.getClassLoader().getResourceAsStream("images/GoodTank1.png"));
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
