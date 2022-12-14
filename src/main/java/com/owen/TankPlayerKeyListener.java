package com.owen;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


/**
 * @author OwenHuang
 * @since 2022/11/6 12:59
 */
class TankPlayerKeyListener extends KeyAdapter {
    private Tank tank;
    private boolean isLeftPressed;
    private boolean isRightPressed;
    private boolean isUpPressed;
    private boolean isDownPressed;

    public TankPlayerKeyListener(Tank tank) {
        this.tank = tank;
    }

    @Override
    public void keyPressed(KeyEvent event) {
        int keyCode = event.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_LEFT:
                isLeftPressed = true;
                break;
            case KeyEvent.VK_RIGHT:
                isRightPressed = true;
                break;
            case KeyEvent.VK_UP:
                isUpPressed = true;
                break;
            case KeyEvent.VK_DOWN:
                isDownPressed = true;
                break;
            default:
                break;
        }
        setPlayerTankDirection();
    }

    @Override
    public void keyReleased(KeyEvent event) {
        tank.setMoving(true);
        int keyCode = event.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_LEFT:
                isLeftPressed = false;
                break;
            case KeyEvent.VK_RIGHT:
                isRightPressed = false;
                break;
            case KeyEvent.VK_UP:
                isUpPressed = false;
                break;
            case KeyEvent.VK_DOWN:
                isDownPressed = false;
                break;
            case KeyEvent.VK_CONTROL:
                tank.fire();
            default:
                break;
        }
        setPlayerTankDirection();
    }

    private void setPlayerTankDirection() {
        if (!isDownPressed && !isUpPressed && !isLeftPressed && !isRightPressed) {
            tank.setMoving(false);
        } else {
            tank.setMoving(true);
            if (isLeftPressed) {
                tank.setDirection(Direction.LEFT);
            }
            if (isRightPressed) {
                tank.setDirection(Direction.RIGHT);
            }
            if (isUpPressed) {
                tank.setDirection(Direction.UP);
            }
            if (isDownPressed) {
                tank.setDirection(Direction.DOWN);
            }
        }
    }
}