package com.company;

public class RoomInfo {
    private double roomNum;
    private int maxNum;
    private int[][] openTime;

    public RoomInfo(double roomNum, int maxNum, int[][] openTime) {
        this.roomNum = roomNum;
        this.maxNum = maxNum;
        this.openTime = openTime;
    }

    protected double getRoomNum() {
        return roomNum;
    }

    protected int getMaxNum() {
        return maxNum;
    }

    protected int[][] getAvailTime() {
        return openTime;
    }

}
