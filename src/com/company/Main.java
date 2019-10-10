package com.company;

import java.util.ArrayList;

/**
 * This program takes number of team members, the floor in which the work in the building,
 * the desired meeting time (start and end) and returns the closest available room from
 * their office
 */

public class Main {

    public static void main(String[] args) {
        int num = 5;                              // input (number of team members)
        int floor = 7;                            // input (the floor where the team work)
        int meetingStarts = toMin("09:00"); // input (beginning of the meeting)
        int meetingEnds = toMin("09:15");   // input (end of the meeting)

        ArrayList<RoomInfo> rooms = new ArrayList();
        ArrayList<Double> ans = new ArrayList();
        int[][] availTime1 = {{toMin("9:00"), toMin("9:15")}, {toMin("14:30"), toMin("15:00")}};
        int[][] availTime2 = {{toMin("10:00"), toMin("11:00")}, {toMin("14:00"), toMin("15:00")}};
        int[][] availTime3 = {{toMin("11:30"), toMin("12:30")}, {toMin("17:00"), toMin("17:30")}};
        int[][] availTime4 = {{toMin("9:30"), toMin("10:30")}, {toMin("12:00"), toMin("12:150")}, {toMin("15:15"), toMin("16:15")}};
        int[][] availTime5 = {{toMin("9:00"), toMin("11:00")}, {toMin("14:00"), toMin("16:00")}};
        int[][] availTime6 = {{toMin("10:30"), toMin("11:30")}, {toMin("13:30"), toMin("15:30")}, {toMin("16:30"), toMin("17:30")}};
        rooms.add(addRoom(7.11, 8, availTime1));
        rooms.add(addRoom(8.23, 6, availTime2));
        rooms.add(addRoom(8.43, 7, availTime3));
        rooms.add(addRoom(9.511, 9, availTime4));
        rooms.add(addRoom(9.527, 4, availTime5));
        rooms.add(addRoom(9.547, 8, availTime6));

//      Given the number of team, this for loop will select all the possible room
//      big enough to accomodate the meeting and remove the rest
        for (int i = 0; i < rooms.size(); i++) {
            if (rooms.get(i).getMaxNum() < num) {
                rooms.remove(i);
            }
        }

        if (rooms.isEmpty()){
            System.out.println("There is no room available to accomodate " + num + " people");
        }

//      Given the team's meeting time, this for loop will select all the possible room
//      that are available at the given time. Note that at the beginning of the loop,
//      ans is an empty array list.
        for (int i = 0; i < rooms.size(); i++) {
            if (checkRoom(rooms.get(i).getAvailTime(), meetingStarts, meetingEnds)) {
                ans.add(rooms.get(i).getRoomNum());
            }
        }

        System.out.println("The closest available room is room ID# " + binarySearchRecursive(ans, floor, 0, ans.size()));
    }

    /**
     * This method converts clock format (HH:MM) into number of minutes
     * for comparison purposes
     *
     * @param time this time in HH:MM format
     * @return time as a number of minutes
     */
    public static int toMin(String time) {
        String[] hourMin = time.split(":");
        int hour = Integer.parseInt(hourMin[0]);
        int min = Integer.parseInt(hourMin[1]);
        return hour * 60 + min;
    }

    /**
     * This method adds possible available rooms into rooms array list
     *
     * @param roomNum   Room number in format (roomfloor.roomnumber)
     *                  e.g. room 9.547 is located at the 9th floor and room number is 547
     * @param maxNum    Maximum allowable occupants in the room
     * @param availTime1 The time when the room becomes available
     * @return returns room object with the specified parameters
     */
    public static RoomInfo addRoom(double roomNum, int maxNum, int[][] availTime1) {
        RoomInfo room = new RoomInfo(roomNum, maxNum, availTime1);
        return room;
    }


    /**
     * Given the meeting time, this method will find the room that are available to use
     *
     * @param arr           this is two dimensional array indicating the open and close time of the room
     * @param num1 this represents the beginning of the meeting
     * @param num2 this represents the end of the meeting
     * @return boolean value. indicating if the room is available or not.
     */
    private static boolean checkRoom(int[][] arr, int num1, int num2) {
        boolean test = false;
        for (int[] i : arr) {
            if (i[0] <= num1 && i[1] >= num2) {
                test = true;
                break;
            }
        }
        return test;
    }

    /**
     * This method will find the closest floor using binary search
     *
     * @param arr       Array list indicating the room object
     * @param target    the floor where the team works
     * @param left      the lower bound of the binary search
     * @param right     the higher bound of the binary search
     *                  
     * @return          double value, the closest room from where the floor the team works
     */
    public static double binarySearchRecursive(ArrayList<Double> arr, int target, int left, int right) {
        int middle = (left + right) / 2;
        
        if (target <= arr.get(0)) return arr.get(0);
        if (target >= arr.get(right - 1)) return arr.get(right - 1);
        if (arr.get(middle) == target) return arr.get(middle);

        if (target < arr.get(middle)) {
            if (middle > 0 && target > arr.get(middle - 1)) {
                return closestFloor(arr.get(middle - 1), arr.get(middle), target);
            }
            return binarySearchRecursive(arr, target, left, middle - 1);
        } else {
            if (middle < right - 2 && target < arr.get(middle + 1)) {
                return closestFloor(arr.get(middle), arr.get(middle + 1), target);
            }
            return binarySearchRecursive(arr, target, middle + 1, right);
        }
    }

    /**
     * This function will find the closest floor from where the team works
     *
     * @param num1           this is two dimensional array indicating the open and close time of the room
     * @param num2 this represents the beginning of the meeting
     * @param target this represents the end of the meeting
     * @return boolean value. indicating if the room is available or not.
     */
    public static double closestFloor(double num1, double num2, int target) {
        if (target - num1 >= num2 - target) return num2;
        else
            return num1;
    }
}