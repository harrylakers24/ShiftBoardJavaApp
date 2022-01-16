package ui;

import exceptions.WeekOutOfBoundsException;
import model.Employee;
import model.Employer;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Represents application's main window frame.
 */
public class GUI extends JFrame {
    private static final String JSON_STORE = "./data/workForce.json";
    private Employer manager;
    private Employee thisEmployee;
    private JPanel homePanel;
    private JFrame employeeFrame;
    private JPanel employeePanel;
    private JFrame employerFrame;
    private JPanel employerPanel;
    private JPanel employerListOfEmployeePanel;
    private JList employeeNamesText;
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;
    private SoundPlayer soundPlayer;
    private DefaultListModel<Employee> model;

    /**
     * Constructor sets up the initial log in screen.
     */
    public GUI() {
        init();

        JLabel chooseOption = new JLabel("Type of worker");
        JButton employerLogInButton = new JButton(new EmployeeLogInButton(this));
        JButton employeeLogInButton = new JButton(new EmployerLogInButton(this));

        homePanel = new JPanel();
        homePanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        homePanel.setLayout(new GridLayout(0, 1));
        homePanel.add(chooseOption);
        homePanel.add(employerLogInButton);
        homePanel.add(employeeLogInButton);

        this.add(homePanel, BorderLayout.CENTER);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Shiftboard GUI");
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);

    }

    //MODIFIES: this
    //EFFECTS: creates objects needed to run the program
    private void init() {
        manager = new Employer("Bob", "12345");
        jsonReader = new JsonReader(JSON_STORE);
        jsonWriter = new JsonWriter(JSON_STORE);
        soundPlayer = new SoundPlayer();
    }

    /**
     * Represents the action to be taken when the user wants to log in as an employee
     */
    private class EmployeeLogInButton extends AbstractAction {
        private JFrame jframe;

        EmployeeLogInButton(JFrame jframe) {
            super("Employee");
            this.jframe = jframe;
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            soundPlayer.play("buttonclick.wav");
            String name = JOptionPane.showInputDialog("Enter name:");
            String password = JOptionPane.showInputDialog("Enter password:");
            if (checkLogIn(name, password)) {
                soundPlayer.play("login.wav");
                JOptionPane.showMessageDialog(jframe,
                        "Successfully logged in!",
                        "Success",
                        JOptionPane.PLAIN_MESSAGE);

                createEmployeeFrame();
            } else {
                soundPlayer.play("error.wav");
                JOptionPane.showMessageDialog(jframe,
                        "You are not registered on shiftboard or wrong password",
                        "Failure",
                        JOptionPane.ERROR_MESSAGE);

            }
        }

        //EFFECTS: returns true if employee is registered and the correct password is entered
        private Boolean checkLogIn(String name, String password) {
            Employee e = manager.getEmployee(name);
            if ((!(e == null)) && (e.getPassword().equals(password))) {
                thisEmployee = manager.getEmployee(name);
                return true;
            } else {
                return false;
            }
        }

    }

    /**
     * Represents the action to be taken when the user wants to log in as an employer
     */
    private class EmployerLogInButton extends AbstractAction {
        private JFrame jframe;

        EmployerLogInButton(JFrame jframe) {
            super("Employer");
            this.jframe = jframe;
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            soundPlayer.play("buttonclick.wav");
            String password = JOptionPane.showInputDialog("Enter password:");
            if (checkLogIn(password)) {
                soundPlayer.play("login.wav");
                JOptionPane.showMessageDialog(jframe,
                        "Successfully logged in!",
                        "Success",
                        JOptionPane.PLAIN_MESSAGE);
                createEmployerFrame();
            } else {
                soundPlayer.play("error.wav");
                JOptionPane.showMessageDialog(jframe,
                        "Wrong password!",
                        "Failure",
                        JOptionPane.ERROR_MESSAGE);
            }
        }

        //EFFECTS: returns true if employer password entered is correct
        private Boolean checkLogIn(String password) {

            if (manager.checkPassword(password)) {
                return true;
            } else {
                return false;
            }
        }

    }

    /**
     * Represents application's main window frame for an employee.
     */
    private void createEmployeeFrame() {
        employeeFrame = new JFrame();
        employeePanel = new JPanel();
        employeePanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        employeePanel.setLayout(new GridLayout(0, 1));
        insertEmployeeButtons();

        employeeFrame.add(employeePanel, BorderLayout.CENTER);
        employeeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        employeeFrame.setTitle("Shiftboard GUI");
        employeeFrame.pack();
        employerFrame.setLocationRelativeTo(null);
        this.setVisible(false);
        employeeFrame.setVisible(true);
    }

    //MODIFIES: this
    //EFFECTS: instantiates buttons on in the employeeFrame and adds them to the employeePanel
    private void insertEmployeeButtons() {
        JButton registerShiftButton = new JButton(new RegisterShiftButton(employeeFrame));
        JButton dropShiftButton = new JButton(new DropShiftButton(employeeFrame));
        JButton getMessageButton = new JButton(new GetMessageButton(employeeFrame));
        JButton backToHomeScreenButton = new JButton(new BackToHomeScreenButton(employeeFrame));

        employeePanel.add(registerShiftButton);
        employeePanel.add(dropShiftButton);
        employeePanel.add(getMessageButton);
        employeePanel.add(backToHomeScreenButton);
    }

    /**
     * Represents the action to be taken when the user wants to register for a shift
     */
    private class RegisterShiftButton extends AbstractAction {
        private JFrame jframe;

        RegisterShiftButton(JFrame jframe) {
            super("Register for shift");
            this.jframe = jframe;
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            soundPlayer.play("buttonclick.wav");
            String week = JOptionPane.showInputDialog("Enter week of work: (Must be in range of 1-52)");
            int numWeek = Integer.parseInt(week);
            String day =  JOptionPane.showInputDialog("Enter day of work: (Ex. Monday, Tuesday)");
            String time =  JOptionPane.showInputDialog("Enter time: (Ex. M (morning), A (afternoon), N (night))");

            try {
                if (thisEmployee.signUpShift(numWeek, day, time)) {
                    soundPlayer.play("login.wav");
                    JOptionPane.showMessageDialog(jframe, "Successfully registered",
                            "Success",
                            JOptionPane.PLAIN_MESSAGE);
                } else {
                    soundPlayer.play("error.wav");
                    JOptionPane.showMessageDialog(jframe, "Shift is already taken or invalid entry",
                            "Failure",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (WeekOutOfBoundsException e) {
                soundPlayer.play("error.wav");
                JOptionPane.showMessageDialog(jframe, e.getMessage(), "Failure", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Represents the action to be taken when the user wants to drop one of their shifts
     */
    private class DropShiftButton extends AbstractAction {
        private JFrame jframe;

        DropShiftButton(JFrame jframe) {
            super("Drop a shift");
            this.jframe = jframe;
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            soundPlayer.play("buttonclick.wav");
            String week = JOptionPane.showInputDialog("Enter week of work: (Must be in range of 1-52)");
            int numWeek = Integer.parseInt(week);
            String day =  JOptionPane.showInputDialog("Enter day of work: (Ex. Monday, Tuesday)");
            String time =  JOptionPane.showInputDialog("Enter time: (Ex. M (morning), A (afternoon), N (night))");

            try {
                if (thisEmployee.dropShift(numWeek, day, time)) {
                    soundPlayer.play("login.wav");
                    JOptionPane.showMessageDialog(jframe, "Successfully dropped",
                            "Success",
                            JOptionPane.PLAIN_MESSAGE);
                } else {
                    soundPlayer.play("error.wav");
                    JOptionPane.showMessageDialog(jframe, "You aren't working at that time or invalid entry",
                            "Failure",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (WeekOutOfBoundsException e) {
                JOptionPane.showMessageDialog(jframe, e.getMessage(), "Failure", JOptionPane.ERROR_MESSAGE);
            }
        }

    }

    /**
     * Represents the action to be taken when the user wants to see the most recent message from their employer
     */
    private class GetMessageButton extends AbstractAction {
        private JFrame jframe;

        GetMessageButton(JFrame jframe) {
            super("Check Recent Message");
            this.jframe = jframe;
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            soundPlayer.play("buttonclick.wav");
            if (thisEmployee.getMessages().size() > 0) {
                soundPlayer.play("login.wav");
                JOptionPane.showMessageDialog(jframe,
                        thisEmployee.getNewMessage(),
                        "Success",
                        JOptionPane.PLAIN_MESSAGE);
            } else {
                soundPlayer.play("error.wav");
                JOptionPane.showMessageDialog(jframe,
                        "No messages yet!",
                        "Failure",
                        JOptionPane.ERROR_MESSAGE);
            }
        }

    }

    /**
     * Represents application's main window frame for an employer.
     */
    private void createEmployerFrame() {
        employerFrame = new JFrame();
        employerPanel = new JPanel();
        employerListOfEmployeePanel = new JPanel();
        employeeNamesText = new JList<>();
        model = new DefaultListModel<>();
        JScrollPane jscrollPane = new JScrollPane(employeeNamesText);
        addEmployeesToJList();

        employerPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        employerPanel.setLayout(new GridLayout(0, 1));
        insertEmployerButtons();

        employeeNamesText.setModel(model);

        employerFrame.add(employerPanel, BorderLayout.CENTER);
        employerListOfEmployeePanel.add(jscrollPane);
        employerFrame.add(employerListOfEmployeePanel, BorderLayout.SOUTH);
        employerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        employerFrame.setTitle("Shiftboard GUI");
        employerFrame.pack();
        employerFrame.setLocationRelativeTo(null);
        this.setVisible(false);
        employerFrame.setVisible(true);
    }

    //MODIFIES: this
    //EFFECTS: instantiates buttons on in the employerFrame and adds them to the employerPanel
    private void insertEmployerButtons() {
        JButton addEmployeeButton = new JButton(new AddEmployeeButton(employerFrame));
        JButton removeEmployeeButton = new JButton(new RemoveEmployeeButton(employerFrame));
        JButton messageEmployeeButton = new JButton(new MessageEmployeeButton(employerFrame));
        JButton changeEmployeeNameButton = new JButton(new ChangeEmployeeNameButton(employerFrame));
        JButton changeEmployeePasswordButton = new JButton(new ChangeEmployeePasswordButton(employerFrame));
        JButton loadEmployeeButton = new JButton(new LoadEmployeeButton(employerFrame));
        JButton saveEmployeeButton = new JButton(new SaveEmployeeButton(employerFrame));
        JButton backToHomeScreenButton = new JButton(new BackToHomeScreenButton(employerFrame));

        employerPanel.add(addEmployeeButton);
        employerPanel.add(removeEmployeeButton);
        employerPanel.add(messageEmployeeButton);
        employerPanel.add(changeEmployeeNameButton);
        employerPanel.add(changeEmployeePasswordButton);
        employerPanel.add(loadEmployeeButton);
        employerPanel.add(saveEmployeeButton);
        employerPanel.add(backToHomeScreenButton);
    }

    /**
     * Represents the action to be taken when the user wants to add an employee to their workforce
     * the system.
     */
    private class AddEmployeeButton extends AbstractAction {
        private JFrame jframe;

        AddEmployeeButton(JFrame jframe) {
            super("Add Employee");
            this.jframe = jframe;
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            soundPlayer.play("buttonclick.wav");
            String name = JOptionPane.showInputDialog("Enter name:");
            String password = JOptionPane.showInputDialog("Enter password:");
            Employee newEmployee = new Employee(name, password, manager);
            manager.addEmployee(newEmployee);
            model.addElement(newEmployee);

            soundPlayer.play("login.wav");
            JOptionPane.showMessageDialog(jframe,
                    "Successfully added employee!",
                    "Success",
                    JOptionPane.PLAIN_MESSAGE);
        }

    }

    /**
     * Represents the action to be taken when the user wants to remove an employee from their workforce
     * the system.
     */
    private class RemoveEmployeeButton extends AbstractAction {
        private JFrame jframe;

        RemoveEmployeeButton(JFrame jframe) {
            super("Remove Selected Employee");
            this.jframe = jframe;
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            soundPlayer.play("buttonclick.wav");
            Employee current = (Employee) employeeNamesText.getSelectedValue();
            if (!(current == null)) {
                manager.removeEmployee(current);
                model.removeElement(current);

                soundPlayer.play("login.wav");
                JOptionPane.showMessageDialog(jframe,
                        "Successfully removed employee!",
                        "Success",
                        JOptionPane.PLAIN_MESSAGE);
            } else {
                soundPlayer.play("error.wav");
                JOptionPane.showMessageDialog(jframe,
                        "Select an employee first!",
                        "Failure",
                        JOptionPane.ERROR_MESSAGE);
            }
        }

    }

    /**
     * Represents the action to be taken when the user wants to send a message to all of their employees
     * the system.
     */
    private class MessageEmployeeButton extends AbstractAction {
        private JFrame jframe;

        MessageEmployeeButton(JFrame jframe) {
            super("Message current employees");
            this.jframe = jframe;
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            soundPlayer.play("buttonclick.wav");
            String message = JOptionPane.showInputDialog("Message to send to all employees:");
            if (manager.notifyAllEmployees(message)) {
                soundPlayer.play("login.wav");
                JOptionPane.showMessageDialog(jframe,
                        "Successfully sent!",
                        "Success",
                        JOptionPane.PLAIN_MESSAGE);
            } else {
                soundPlayer.play("error.wav");
                JOptionPane.showMessageDialog(jframe,
                        "Add an employee first!",
                        "Failure",
                        JOptionPane.ERROR_MESSAGE);
            }

        }
    }

    /**
     * Represents the action to be taken when the user wants to change an employees password
     * the system.
     */
    private class ChangeEmployeePasswordButton extends AbstractAction {
        private JFrame jframe;

        ChangeEmployeePasswordButton(JFrame jframe) {
            super("Change Selected Employee's Password");
            this.jframe = jframe;
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            soundPlayer.play("buttonclick.wav");
            Employee current = (Employee) employeeNamesText.getSelectedValue();

            if (!(current == null)) {
                String newPassword = JOptionPane.showInputDialog("New Employee password:");
                current.setPassword(newPassword);
                removeCurrentEmployeesFromJList();
                addEmployeesToJList();

                soundPlayer.play("login.wav");
                JOptionPane.showMessageDialog(jframe,
                        "Successfully changed password!",
                        "Success",
                        JOptionPane.PLAIN_MESSAGE);
            } else {
                soundPlayer.play("error.wav");
                JOptionPane.showMessageDialog(jframe,
                        "Select an employee first!",
                        "Failure",
                        JOptionPane.ERROR_MESSAGE);
            }

        }
    }

    /**
     * Represents the action to be taken when the user wants to change an employees name
     * the system.
     */
    private class ChangeEmployeeNameButton extends AbstractAction {
        private JFrame jframe;

        ChangeEmployeeNameButton(JFrame jframe) {
            super("Change Selected Employee's Name");
            this.jframe = jframe;
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            soundPlayer.play("buttonclick.wav");
            Employee current = (Employee) employeeNamesText.getSelectedValue();

            if (!(current == null)) {
                String newPassword = JOptionPane.showInputDialog("New Employee Name:");
                current.setName(newPassword);
                removeCurrentEmployeesFromJList();
                addEmployeesToJList();

                soundPlayer.play("login.wav");
                JOptionPane.showMessageDialog(jframe,
                        "Successfully changed employee name!",
                        "Success",
                        JOptionPane.PLAIN_MESSAGE);
            } else {
                soundPlayer.play("error.wav");
                JOptionPane.showMessageDialog(jframe,
                        "Select an employee first!",
                        "Failure",
                        JOptionPane.ERROR_MESSAGE);
            }

        }
    }

    /**
     * Represents the action to be taken when the user wants to load data into the application
     * the system.
     */
    private class LoadEmployeeButton extends AbstractAction {
        private JFrame jframe;

        LoadEmployeeButton(JFrame jframe) {
            super("Load work force from file");
            this.jframe = jframe;
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            soundPlayer.play("buttonclick.wav");
            try {
                removeCurrentEmployeesFromJList();
                manager = jsonReader.read();
                addEmployeesToJList();
                soundPlayer.play("login.wav");
                JOptionPane.showMessageDialog(jframe,
                        "Loaded " + manager.getName() + " from " + JSON_STORE,
                        "Success",
                        JOptionPane.PLAIN_MESSAGE);
            } catch (IOException | WeekOutOfBoundsException e) {
                soundPlayer.play("error.wav");
                JOptionPane.showMessageDialog(jframe,
                        "Unable to read to file: " + JSON_STORE,
                        "Failure",
                        JOptionPane.ERROR_MESSAGE);
            }

        }



    }

    //MODIFIES: model and employeeNamesText (JList)
    //EFFECTS: adds the employer's employees onto the JList object
    public void addEmployeesToJList() {
        for (Employee e: manager.getListOfEmployees()) {
            model.addElement(e);
        }
    }

    //MODIFIES: model and employeeNamesText (JList)
    //EFFECTS: removes all current employees from the JList object
    public void removeCurrentEmployeesFromJList() {
        model.removeAllElements();
    }

    /**
     * Represents the action to be taken when the user wants to save data from the application
     * the system.
     */
    private class SaveEmployeeButton extends AbstractAction {
        private JFrame jframe;

        SaveEmployeeButton(JFrame jframe) {
            super("Save work force from file");
            this.jframe = jframe;
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            soundPlayer.play("buttonclick.wav");
            try {
                jsonWriter.open();
                jsonWriter.write(manager);
                jsonWriter.close();
                soundPlayer.play("login.wav");
                JOptionPane.showMessageDialog(jframe,
                        "Saved " + manager.getName() + " to " + JSON_STORE,
                        "Success",
                        JOptionPane.PLAIN_MESSAGE);
            } catch (FileNotFoundException e) {
                soundPlayer.play("error.wav");
                JOptionPane.showMessageDialog(jframe,
                        "Unable to write to file: " + JSON_STORE,
                        "Failure",
                        JOptionPane.ERROR_MESSAGE);
            }

        }
    }

    /**
     * Represents the action to be taken when the user wants to return to the home screen
     * the system.
     */
    private class BackToHomeScreenButton extends AbstractAction {
        private JFrame jframe;

        BackToHomeScreenButton(JFrame jframe) {
            super("Quit to main menu");
            this.jframe = jframe;
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            soundPlayer.play("buttonclick.wav");
            jframe.setVisible(false);
            JFrame homeFrame = returnMainMenuFrame();
            homeFrame.setVisible(true);


        }
    }

    //EFFECTS: returns the initial home screen frame
    public GUI returnMainMenuFrame() {
        return this;
    }

}
