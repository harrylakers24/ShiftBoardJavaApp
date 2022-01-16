package model;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Employee {
    private String name;
    private List<String> messages;
    private String password;
    private Employer employer;


    //MODIFIES: this
    //EFFECTS: constructs an employee with an empty list of messages
    public Employee(String name, String password, Employer e) {
        this.name = name;
        this.password = password;
        this.employer = e;
        this.messages = new ArrayList<>();

    }

    //getters
    public String getName() {
        return name;
    }


    public String getPassword() {
        return password;
    }

    public Employer getEmployer() {
        return employer;
    }

    public List<String> getMessages() {
        return messages;
    }

    //EFFECTS: returns the last added message from employer, if employer hasn't sent
    //anything yet, return null
    public String getNewMessage() {
        if (messages.size() > 0) {
            return (messages.get(messages.size() - 1));
        }
        return null;
    }

    //MODIFIES: this
    //EFFECTS: adds a string message to this employee's List<String> messages
    public void addMessage(String message) {
        messages.add(message);
    }


    //REQUIRES: week to be in the range from 1-52, day has to be "Monday",
    // "Tuesday", "Wednesday", etc
    //MODIFIES: week, day
    //EFFECTS: registers employee into requested time slot if available and returns
    // true, otherwise returns false
    public Boolean signUpShift(int week, String day, String time) {
        Week weekChose = employer.getWeek(week);
        Day dayChose = weekChose.getDay(day);

        switch (time) {
            case "M":
                if (dayChose.getMorningAvailability()) {
                    dayChose.takeShift(this, "M");
                    return true;
                }
                return false;
            case "A":
                if (dayChose.getAfternoonAvailability()) {
                    dayChose.takeShift(this, "A");
                    return true;
                }
                return false;
            case "N":
                if (dayChose.getNightAvailability()) {
                    dayChose.takeShift(this, "N");
                    return true;
                }
                break;
        }

        return false;
    }

    //REQUIRES: week to be in the range from 1-52, day has to be "Monday",
    // "Tuesday", "Wednesday", etc
    //MODIFIES: week, day
    //EFFECTS: drops employee's shift and returns true if employee
    // is registered at given time, otherwise return false
    public Boolean dropShift(int week, String day, String time) {
        Week weekChose = employer.getWeek(week);
        Day dayChose = weekChose.getDay(day);

        if (!(dayChose.getWorkingEmployee(time) == null)) {
            if (dayChose.getWorkingEmployee(time).equals(this)) {
                if (time.equals("M")) {
                    dayChose.removeShift("M");
                    return true;
                } else if (time.equals("A")) {
                    dayChose.removeShift("A");
                    return true;
                } else {
                    dayChose.removeShift("N");
                    return true;
                }
            }
            return false;
        }

        return false;
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Employee Name", name);
        json.put("Employee Password", password);
        return json;
    }

}


