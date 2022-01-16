package model;


import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Day {
    //INVARIANT: listOfTimeAvailability is the same size as listOfEmployee
    //3 shift times everyday for now
    private String dayOfWeek;
    private Boolean morningAvailability;
    private Boolean afternoonAvailability;
    private Boolean nightAvailability;
    private List<Boolean> listOfTimeAvailability;
    private List<Employee> listOfEmployee;


    //MODIFIES: this
    //EFFECTS: constructs a day with each time slot (M, A, N) set to available
    public Day(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
        this.morningAvailability = true;
        this.afternoonAvailability = true;
        this.nightAvailability = true;
        placeEmployeesIntoDay();
        placeTimesIntoDay();
    }

    //getters
    public String getDayName() {
        return dayOfWeek;
    }

    public Boolean getMorningAvailability() {
        return morningAvailability;
    }

    public Boolean getAfternoonAvailability() {
        return afternoonAvailability;
    }

    public Boolean getNightAvailability() {
        return nightAvailability;
    }

    public List<Boolean> getListOfTimeAvailability() {
        return listOfTimeAvailability;
    }

    public List<Employee> getListOfEmployee() {
        return listOfEmployee;
    }

    public void placeTimesIntoDay() {
        this.listOfTimeAvailability = new ArrayList<>();
        this.listOfTimeAvailability.add(morningAvailability);
        this.listOfTimeAvailability.add(afternoonAvailability);
        this.listOfTimeAvailability.add(nightAvailability);
    }

    public void placeEmployeesIntoDay() {
        this.listOfEmployee = new ArrayList<>();
        this.listOfEmployee.add(null);
        this.listOfEmployee.add(null);
        this.listOfEmployee.add(null);

    }


    //EFFECTS: returns the employee working at parameter time, returns null if none
    //is working at that time, also returns null if time is invalid input
    public Employee getWorkingEmployee(String time) {
        switch (time) {
            case "M":
                return (listOfEmployee.get(0));
            case "A":
                return (listOfEmployee.get(1));
            case "N":
                return (listOfEmployee.get(2));
            default:
                return null;

        }

    }

    //REQUIRES: String time to be one of: "M", "A", "N"
    //MODIFIES: this
    //EFFECTS: sets employee e to the time slot, availability at the time slot
    // is set to false
    public void takeShift(Employee e, String time) {
        switch (time) {
            case "M":
                listOfEmployee.set(0, e);
                this.morningAvailability = false;
                break;
            case "A":
                listOfEmployee.set(1, e);
                this.afternoonAvailability = false;
                break;
            default:
                listOfEmployee.set(2, e);
                this.nightAvailability = false;
        }
    }


    //REQUIRES: String time to be one of: "M", "A", "N"
    //MODIFIES: this
    //EFFECTS: removes employee e from the time slot, availability at the time slot
    // is set to true
    public void removeShift(String time) {
        switch (time) {
            case "M":
                listOfEmployee.set(0, null);
                this.morningAvailability = true;
                break;
            case "A":
                listOfEmployee.set(1, null);
                this.afternoonAvailability = true;
                break;
            default:
                listOfEmployee.set(2, null);
                this.nightAvailability = true;
        }
    }


    //EFFECTS: returns a list of string that holds availability of this day at each time
    public List<String> returnAvailability() {
        List<String> availability = new ArrayList<>();
        availability.add("Morning:" + booleanToString(morningAvailability, 0));
        availability.add("Afternoon:" + booleanToString(afternoonAvailability, 1));
        availability.add("Night:" + booleanToString(nightAvailability, 2));
        return availability;
    }


    //REQUIRES: int index to be one of: 0, 1, 2
    //EFFECTS: converts a boolean into a string representation, and if
    //shift is taken at index, returns the name of the employee working
    public String booleanToString(Boolean b, int index) {
        if (b) {
            return "Available";
        } else {
            return ("Not Available, " + listOfEmployee.get(index).getName() + " Is working");
        }
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Morning Availability", this.booleanToString(this.morningAvailability, 0));
        json.put("Afternoon Availability", this.booleanToString(this.afternoonAvailability, 1));
        json.put("Night Availability", this.booleanToString(this.nightAvailability, 2));
        return json;
    }
}
