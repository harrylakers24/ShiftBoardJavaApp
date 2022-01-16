package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Employer {
    private String name;
    private List<Employee> workForce;
    private String password;
    private List<Week> weeksInWorkingYear;
    private static final int NUM_OF_WEEKS_IN_A_YEAR = 52;


    //MODIFIES: this
    //EFFECTS: constructs a new Employer
    public Employer(String name, String password) {
        this.name = name;
        this.workForce = new ArrayList<>();
        this.weeksInWorkingYear = fillWeeks();
        this.password = password;

    }

    //getters
    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public List<Employee> getListOfEmployees() {
        return workForce;
    }

    public List<Week> getListOfWeeks() {
        return weeksInWorkingYear;
    }


    //REQUIRES: 1 <= i <= 52
    //EFFECTS: returns the week object in index position [i - 1] for this manager
    public Week getWeek(int i) {
        return weeksInWorkingYear.get(i - 1);
    }

    //MODIFIES: this
    //EFFECTS: instantiates NUM_OF_WEEKS_IN_A_YEAR of weeks and adds each into
    //weeksInWorkingYear
    public List<Week> fillWeeks() {
        this.weeksInWorkingYear = new ArrayList<>();
        for (int i = 1; i <= NUM_OF_WEEKS_IN_A_YEAR; i++) {
            Week thisWeek = new Week(i);
            weeksInWorkingYear.add(thisWeek);
        }
        return weeksInWorkingYear;
    }

    //MODIFIES: this
    //EFFECTS: instantiates a new employee of String name, String password, Employer e
    //and adds the employee into this manager's workForce
    public void addEmployee(Employee newEmployee) {
        this.workForce.add(newEmployee);
    }

    //MODIFIES: this
    //EFFECTS: removes given employee from this workForce
    public void removeEmployee(Employee newEmployee) {
        this.workForce.remove(newEmployee);
    }




    //EFFECTS: returns the first employee in this workForce that has the same name as the parameter n
    // (case-sensitive), if no employee has the same name as the parameter name, return null
    public Employee getEmployee(String n) {
        for (Employee employee : workForce) {
            if (n.equals(employee.getName())) {
                return employee;
            }
        }
        return null;
    }


    //EFFECTS: returns true if parameter password equals this password, otherwise return
    //false
    public Boolean checkPassword(String p) {
        return password.equals(p);
    }

    //MODIFIES: workForce
    //EFFECTS: returns true and sends a message to each employee in this workForce if
    //workForce.size()>0, otherwise returns false
    public Boolean notifyAllEmployees(String message) {
        if (workForce.size() > 0) {
            for (Employee e : workForce) {
                e.addMessage(message);
            }
            return true;
        }
        return false;
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Employer Name", name);
        json.put("Employer Password", password);
        json.put("employees", employeeToJson());
        return json;
    }

    // EFFECTS: returns things in this employer as a JSON array
    private JSONArray employeeToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Employee e : workForce) {
            jsonArray.put(e.toJson());
        }

        return jsonArray;
    }


}
