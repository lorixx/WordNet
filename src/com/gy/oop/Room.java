package com.gy.oop;

/**
 * Created with IntelliJ IDEA.
 * User: Zhisheng
 * Date: 9/6/13
 * Time: 12:12 AM
 * To change this template use File | Settings | File Templates.
 */
public class Room extends Space{

    public Door door;


    public void openDoor() {
        door.open();
    }

    public void closeDoor() {
        door.close();
    }


}
