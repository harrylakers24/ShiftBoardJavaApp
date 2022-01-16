package ui;

//Note: this user input template is used from the TellerApp application from class
//Note: for phase 1, there is only one manager object, I will implement a way to sign up as a manager later on. For
//now to access the manager functions type in password: 12345 when prompted
//Note: persistence package template is used from JsonSerializationDemo from class

import model.Day;
import model.Employee;
import model.Employer;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;


public class ShiftBoardApp {
    private static final String JSON_STORE = "./data/workForce.json";
    private Employer manager;
    private Employee thisEmployee;
    private Scanner input;
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;



    //EFFECTS: runs the shiftboard application

    public ShiftBoardApp() {
        runShiftBoardApp();
    }


    // MODIFIES: this
    // EFFECTS: processes user input
    private void runShiftBoardApp() {
        boolean exitProgram = false;
        String command;

        init();

        while (!(exitProgram)) {
            displaySelectWorker();
            command = input.next();

            if (command.equals("q")) {
                exitProgram = true;
            } else {
                processEntry(command);
            }

        }

        System.out.println("Goodbye");

    }

    // MODIFIES: this
    // EFFECTS: initializes the employer
    private void init() {
        manager = new Employer("Bob", "12345");
        input = new Scanner(System.in).useDelimiter("\\n");
        jsonReader = new JsonReader(JSON_STORE);
        jsonWriter = new JsonWriter(JSON_STORE);
    }

    //EFFECTS: displays main screen input options
    private void displaySelectWorker() {
        System.out.println("\nChoose type of worker:");
        System.out.println("\t1 -> Employee");
        System.out.println("\t2 -> Employer");
        System.out.println("\tq -> Close Application");

    }

    // MODIFIES: this
    // EFFECTS: checks if employee or employer log in credentials are correct, if so logs them into their account
    private void processEntry(String command) {
        if (command.equals("1")) {
            System.out.println("Type in your name:");
            String name = input.next();
            System.out.println("Enter your password");
            String password = input.next();
            Employee e = manager.getEmployee(name);
            if ((!(e == null)) && (e.getPassword().equals(password))) {
                thisEmployee = manager.getEmployee(name);
                processEmployeeEntry();
            } else {
                System.out.println("You are not registered on shiftboard or wrong password");
            }

        } else if (command.equals("2")) {
            System.out.println("Password:");
            String password = input.next();
            if (manager.checkPassword(password)) {
                processEmployerEntry();
            } else {
                System.out.println("Wrong password");
            }
        } else {
            System.out.println("Selection not valid...");
        }
    }


    //EFFECTS: displays employee main screen input options
    private void displayEmployeeOptions() {
        System.out.println("\nChoose option:");
        System.out.println("\t1 -> Check Shift Times Available");
        System.out.println("\t2 -> Register for a shift");
        System.out.println("\t3 -> Drop a shift");
        System.out.println("\t4 -> Check Recent Message");
        System.out.println("\tq -> Go Back To Home Menu");
    }

    //EFFECTS: displays employer main screen input options
    private void displayEmployerOptions() {
        System.out.println("\nChoose option:");
        System.out.println("\t1 -> Add New Employees");
        System.out.println("\t2 -> Remove Employee");
        System.out.println("\t3 -> Notify All Employees");
        System.out.println("\t4 -> Show List of Names of All Employees");
        System.out.println("\t5 -> Save current work force to file");
        System.out.println("\t6 -> Load work force from file");
        System.out.println("\tq -> Go Back To Home Menu");
    }

    //MODIFIES: this
    //EFFECTS: processes employee's user input
    private void processEmployeeEntry() {
        boolean stayInThisMenu = true;
        do {
            displayEmployeeOptions();
            String command2 = input.next();
            stayInThisMenu = processEmployeeCases(stayInThisMenu, command2);
        } while (stayInThisMenu);
    }

    //Note: helper function for processEmployeeEntry();
    //EFFECTS: processes employee's user input, returns state of stayInThisMenu
    private boolean processEmployeeCases(boolean stayInThisMenu, String command2) {
        switch (command2) {
            case "1":
                employeeDisplayAvailability();
                break;
            case "2":
                employeeRegisterShift();
                break;
            case "3":
                employeeDropShift();
                break;
            case "4":
                employeeGetNewMessage();
                break;
            case "q":
                stayInThisMenu = false;
                break;
            default:
                System.out.println("Invalid Entry");
                break;
        }
        return stayInThisMenu;
    }


    //MODIFIES: this
    //EFFECTS: displays the availability of user input week
    private void employeeDisplayAvailability() {
        System.out.println("Enter week:");
        System.out.println("Note: Week has to be in the range from 1-52");
        int week = input.nextInt();
        List<Day> daysInWeek = manager.getWeek(week).getDaysInWeek();

        for (Day d : daysInWeek) {
            System.out.println(d.getDayName());
            List<String> listOfTimeSlots = d.returnAvailability();
            System.out.println(listOfTimeSlots.get(0));
            System.out.println(listOfTimeSlots.get(1));
            System.out.println(listOfTimeSlots.get(2));

        }
    }

    //EFFECTS: if thisEmployee has messages, prints out the latest message from employer
    private void employeeGetNewMessage() {
        if (thisEmployee.getMessages().size() > 0) {
            System.out.println(thisEmployee.getNewMessage());
        } else {
            System.out.println("No messages yet");
        }
    }

    //REQUIRES: 1 <= int week <= 52, String day to be one of: "Monday", "Tuesday", "Wednesday", "Thursday", "Friday",
    //"Saturday", "Sunday"
    //MODIFIES: this
    //EFFECTS: if thisEmployee's inputted time is available, prints out "Successfully registered". Otherwise prints out
    // "Shift is already taken or false input"
    private void employeeRegisterShift() {
        System.out.println("Enter week of work:");
        System.out.println("Note: Week has to be in the range from 1-52");
        int week = input.nextInt();
        displayDays();
        String day = input.next();
        displayTimeOfDay();
        String time = input.next();
        if (thisEmployee.signUpShift(week, day, time)) {
            System.out.println("Successfully registered");
        } else {
            System.out.println("Shift is already taken or invalid entry");
        }
    }

    //REQUIRES: 1 <= int week <= 52, String day to be one of: "Monday", "Tuesday", "Wednesday", "Thursday", "Friday",
    //"Saturday", "Sunday"
    //MODIFIES: this
    //EFFECTS: if thisEmployee's is working at inputted time , prints out "Successfully dropped".
    // Otherwise print out "You aren't working at that time or invalid entry".
    private void employeeDropShift() {
        System.out.println("Enter week of work:");
        System.out.println("Note: Week has to be in the range from 1-52");
        int week = input.nextInt();
        displayDays();
        String day = input.next();
        displayTimeOfDay();
        String time = input.next();
        if (thisEmployee.dropShift(week, day, time)) {
            System.out.println("Successfully dropped");
        } else {
            System.out.println("You aren't working at that time or invalid entry");
        }
    }

    //EFFECTS: displays possible inputs for day when registering or dropping a shift
    private void displayDays() {
        System.out.println("Enter one of these options for day of work:");
        System.out.println("'Monday'");
        System.out.println("'Tuesday'");
        System.out.println("'Wednesday'");
        System.out.println("'Thursday'");
        System.out.println("'Friday'");
        System.out.println("'Saturday'");
        System.out.println("'Sunday'");
    }

    //EFFECTS: displays possible inputs for time when registering or dropping a shift
    private void displayTimeOfDay() {
        System.out.println("Enter time of day:");
        System.out.println("Note: ");
        System.out.println("M" + " " + "for morning shift");
        System.out.println("A" + " " + "for afternoon shift");
        System.out.println("N" + " " + "for night shift");
    }

    //MODIFIES: this
    //EFFECTS: processes employer's user input
    private void processEmployerEntry() {
        boolean stayInThisMenu = true;
        do {
            displayEmployerOptions();
            String command2 = input.next();
            switch (command2) {
                case "1": employerAddEmployee();
                    break;
                case "2": employerRemoveEmployee();
                    break;
                case "3": sendMessage();
                    break;
                case "4": printListOfEmployees();
                    break;
                case "5": saveWorkForce();
                    break;
                case "6": loadWorkForce();
                    break;
                case "q": stayInThisMenu = false;
                    break;
                default: System.out.println("Invalid Entry");
                    break;
            }
        } while (stayInThisMenu);
    }

    //MODIFIES: this
    //EFFECTS: employer adds details of a new employee and adds the employee to their workForce
    private void employerAddEmployee() {
        System.out.println("Name of new employee:");
        String name = input.next();
        System.out.println("Password of this employee");
        String password = input.next();
        Employee newEmployee = new Employee(name, password, manager);
        manager.addEmployee(newEmployee);
    }

    //MODIFIES: this
    //EFFECTS: employer removes Employee with the inputted name
    private void employerRemoveEmployee() {
        System.out.println("Name of employee:");
        String name = input.next();
        Employee current = manager.getEmployee(name);
        if (!(current == null)) {
            manager.removeEmployee(current);
            System.out.println("Successfully removed");
        } else {
            System.out.println("Employee with given name is not on work force");
        }

    }

    //MODIFIES: this
    //EFFECTS: asks employer for a message, message gets sent to employees if there is at least one employee already
    //added by employer
    private void sendMessage() {
        System.out.println("Message to send to all employees:");
        String message = input.next();
        if (manager.notifyAllEmployees(message)) {
            System.out.println("Successfully sent");
        } else {
            System.out.println("Add an employee first!");
        }
    }


    //EFFECTS: prints out list of employees currently employed under the employer
    private void printListOfEmployees() {
        List<Employee> workForce = manager.getListOfEmployees();
        if (workForce.size() == 0) {
            System.out.println("You have no employees added");
        } else {
            for (Employee e : workForce) {
                System.out.println("Name:" + e.getName());
            }

        }
    }

    // EFFECTS: saves the workForce to file
    private void saveWorkForce() {
        try {
            jsonWriter.open();
            jsonWriter.write(manager);
            jsonWriter.close();
            System.out.println("Saved " + manager.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads workForce from file
    private void loadWorkForce() {
        try {
            manager = jsonReader.read();
            System.out.println("Loaded " + manager.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }


}
