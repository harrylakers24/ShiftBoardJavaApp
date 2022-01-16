package persistence;

import exceptions.WeekOutOfBoundsException;
import model.Employee;
import model.Employer;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads manager from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Employer read() throws IOException, WeekOutOfBoundsException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseEmployer(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses employer from JSON object and returns it
    private Employer parseEmployer(JSONObject jsonObject) throws WeekOutOfBoundsException {
        String name = jsonObject.getString("Employer Name");
        String password = jsonObject.getString("Employer Password");
        Employer employer = new Employer(name, password);
        addEmployees(employer, jsonObject);
        findTakenShiftsWeeks(jsonObject, employer);
        return employer;
    }

    private void findTakenShiftsWeeks(JSONObject jsonObject, Employer e) throws WeekOutOfBoundsException {

        JSONArray jsonArray = jsonObject.getJSONArray("Weeks");


        for (Object json : jsonArray) {
            JSONArray nextWeek = (JSONArray) json;
            findDayAvailability(nextWeek, e);
        }

    }

    private void findDayAvailability(JSONArray jsonArray, Employer e) throws WeekOutOfBoundsException {
        for (int i = 1; i < 52; i++) {
            int dayCounter = 1;
            for (Object json : jsonArray) {
                JSONObject nextDay = (JSONObject) json;
                findTimeAvailability(nextDay, e, i, dayCounter);
                dayCounter++;
            }
        }

    }

    private void findTimeAvailability(JSONObject jsonObject, Employer e, int week, int day)
            throws WeekOutOfBoundsException {

        String morning = jsonObject.getString("Morning Availability");
        String afternoon = jsonObject.getString("Afternoon Availability");
        String night = jsonObject.getString("Night Availability");
        if (!(morning.equals("Available"))) {
            String employeeNameNotTrimmed = morning.substring(15);
            String employeeName = employeeNameNotTrimmed.substring(0, employeeNameNotTrimmed.indexOf(" "));
            Employee workingEmployee = e.getEmployee(employeeName);
            workingEmployee.signUpShift(week, intToDayStringConversion(day), "M");
        }
        if (!(afternoon.equals("Available"))) {
            String employeeNameNotTrimmed = afternoon.substring(15);
            String employeeName = employeeNameNotTrimmed.substring(0, employeeNameNotTrimmed.indexOf(" "));
            Employee workingEmployee = e.getEmployee(employeeName);
            workingEmployee.signUpShift(week, intToDayStringConversion(day), "A");
        }
        if (!(night.equals("Available"))) {
            String employeeNameNotTrimmed = night.substring(15);
            String employeeName = employeeNameNotTrimmed.substring(0, employeeNameNotTrimmed.indexOf(" "));
            Employee workingEmployee = e.getEmployee(employeeName);
            workingEmployee.signUpShift(week, intToDayStringConversion(day), "N");
        }
    }

    private String intToDayStringConversion(int day) {
        switch (day) {
            case 1:
                return "Monday";
            case 2:
                return "Tuesday";
            case 3:
                return "Wednesday";
            case 4:
                return "Thursday";
            case 5:
                return "Friday";
            case 6:
                return "Saturday";
            default:
                return "Sunday";
        }
    }


    // MODIFIES: e
    // EFFECTS: parses employees from JSON object and adds them to employer
    private void addEmployees(Employer e, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("employees");
        for (Object json : jsonArray) {
            JSONObject nextEmployee = (JSONObject) json;
            addEmployee(e, nextEmployee);
        }
    }

    // MODIFIES: e
    // EFFECTS: parses employee from JSON object and adds it to employer
    private void addEmployee(Employer e, JSONObject jsonObject) {
        String name = jsonObject.getString("Employee Name");
        String password = jsonObject.getString("Employee Password");
        String message = jsonObject.getString("Employee Recent Message");
        Employee thingy = new Employee(name, password, e);
        thingy.addMessage(message);
        e.addEmployee(thingy);
    }
}
