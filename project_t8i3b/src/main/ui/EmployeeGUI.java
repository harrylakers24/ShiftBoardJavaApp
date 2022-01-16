//package ui;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//
//public class EmployeeGUI extends JFrame {
//    private JPanel employeePanel;
//    public EmployeeGUI() {
//        employeePanel = new JPanel();
//        employeePanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
//        employeePanel.setLayout(new GridLayout(0, 1));
//        insertEmployeeButtons();
//
//        this.add(employeePanel, BorderLayout.CENTER);
//        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        this.setTitle("Shiftboard GUI");
//        this.pack();
//        this.setVisible(true);
//    }
//
//    private void insertEmployeeButtons() {
//        JButton registerShiftButton = new JButton(RegisterShiftButton(this));
//        JButton dropShiftButton = new JButton(DropShiftButton(this));
//        JButton getMessageButton = new JButton(GetMessageButton(this));
//        JButton backToHomeScreenButton = new JButton(BackToHomeScreenButton(this));
//
//        employeePanel.add(registerShiftButton);
//        employeePanel.add(dropShiftButton);
//        employeePanel.add(getMessageButton);
//        employeePanel.add(backToHomeScreenButton);
//    }
//
//    private class RegisterShiftButton extends AbstractAction {
//        private JFrame jframe;
//
//        RegisterShiftButton(JFrame jframe) {
//            super("Register for shift");
//            this.jframe = jframe;
//        }
//
//        @Override
//        public void actionPerformed(ActionEvent evt) {
//            String week = JOptionPane.showInputDialog("Enter week of work: (Must be in range of 1-52)");
//            int numWeek = Integer.parseInt(week);
//            String day =  JOptionPane.showInputDialog("Enter day of work: (Ex. Monday, Tuesday)");
//            String time =  JOptionPane.showInputDialog("Enter time: (Ex. M (morning), A (afternoon), N (night))");
//
//            if (thisEmployee.signUpShift(numWeek, day, time)) {
//                JOptionPane.showMessageDialog(jframe,
//                        "Successfully registered",
//                        "Success",
//                        JOptionPane.PLAIN_MESSAGE);
//            } else {
//                JOptionPane.showMessageDialog(jframe,
//                        "Shift is already taken or invalid entry",
//                        "Failure",
//                        JOptionPane.ERROR_MESSAGE);
//            }
//        }
//
//    }
//
//    private class DropShiftButton extends AbstractAction {
//        private JFrame jframe;
//
//        DropShiftButton(JFrame jframe) {
//            super("Drop a shift");
//            this.jframe = jframe;
//        }
//
//        @Override
//        public void actionPerformed(ActionEvent evt) {
//            String week = JOptionPane.showInputDialog("Enter week of work: (Must be in range of 1-52)");
//            int numWeek = Integer.parseInt(week);
//            String day =  JOptionPane.showInputDialog("Enter day of work: (Ex. Monday, Tuesday)");
//            String time =  JOptionPane.showInputDialog("Enter time: (Ex. M (morning), A (afternoon), N (night))");
//
//            if (thisEmployee.dropShift(numWeek, day, time)) {
//                JOptionPane.showMessageDialog(jframe,
//                        "Successfully dropped",
//                        "Success",
//                        JOptionPane.PLAIN_MESSAGE);
//            } else {
//                JOptionPane.showMessageDialog(jframe,
//                        "You aren't working at that time or invalid entry",
//                        "Failure",
//                        JOptionPane.ERROR_MESSAGE);
//            }
//        }
//
//    }
//
//    private class GetMessageButton extends AbstractAction {
//        private JFrame jframe;
//
//        GetMessageButton(JFrame jframe) {
//            super("Check Recent Message");
//            this.jframe = jframe;
//        }
//
//        @Override
//        public void actionPerformed(ActionEvent evt) {
//            if (thisEmployee.getMessages().size() > 0) {
//                JOptionPane.showMessageDialog(jframe,
//                        thisEmployee.getNewMessage(),
//                        "Success",
//                        JOptionPane.PLAIN_MESSAGE);
//            } else {
//                JOptionPane.showMessageDialog(jframe,
//                        "No messages yet!",
//                        "Failure",
//                        JOptionPane.ERROR_MESSAGE);
//            }
//        }
//
//    }
//}
