package model;

import java.util.ArrayList;
import java.util.List;

//INVARIANT: Each week contains 7 days
public class Week {
    private int numOfWeek;
    private List<Day> daysInWeek;


    //MODIFIES: this
    //EFFECTS: constructs a week with 7 days
    public Week(int numOfWeek) {
        this.daysInWeek = new ArrayList<>();
        this.numOfWeek = numOfWeek;
        createDaysAndPutIntoWeek();

    }

    //getters
    public int getNumOfWeek() {
        return numOfWeek;
    }

    public List<Day> getDaysInWeek() {
        return daysInWeek;
    }

    public void createDaysAndPutIntoWeek() {
        Day monday = new Day("Monday");
        Day tuesday = new Day("Tuesday");
        Day wednesday = new Day("Wednesday");
        Day thursday = new Day("Thursday");
        Day friday = new Day("Friday");
        Day saturday = new Day("Saturday");
        Day sunday = new Day("Sunday");

        daysInWeek.add(monday);
        daysInWeek.add(tuesday);
        daysInWeek.add(wednesday);
        daysInWeek.add(thursday);
        daysInWeek.add(friday);
        daysInWeek.add(saturday);
        daysInWeek.add(sunday);
    }


    //EFFECTS: if string s is equal to a day in daysInWeek, returns that
    //day object, otherwise return null
    public Day getDay(String s) {
        for (Day d : daysInWeek) {
            if (d.getDayName().equals(s)) {
                return d;
            }
        }
        return null;
    }


}
