package com.gy.oop;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Zhisheng
 * Date: 9/6/13
 * Time: 12:11 AM
 * To change this template use File | Settings | File Templates.
 */
public class House {

    public Door houseDoor;

    public Bedroom[] bedrooms;

    public Kitchen kitchen;

    public LivingRoom livingRoom;


    public void ringTheHouse() {

    }

    public void openDoor() {

    }

    public void closeDoor() {

    }

    /**
     * Public API for the number of bedrooms in this house
     * @return
     */
    public int numberOfBedrooms() {
        return bedrooms.length;
    }






}
