//package ui;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//
//public class Tutorial implements ActionListener {
//
//
//    public Tutorial() {
//
//        JFrame frame = new JFrame();
//
//        JButton employerLogInButton = new JButton("Employer");
//        JButton employeeLogInButton = new JButton("Employee");
//        JLabel chooseOption = new JLabel("Type of worker");
//
//        JPanel panel = new JPanel();
//        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
//        panel.setLayout(new GridLayout(0, 1));
//        panel.add(chooseOption);
//        panel.add(employerLogInButton);
//        panel.add(employeeLogInButton);
//
//        frame.add(panel, BorderLayout.CENTER);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setTitle("Shiftboard GUI");
//        frame.pack();
//        frame.setVisible(true);
//
//    }
//
//    @Override
//    public void actionPerformed(ActionEvent e) {
//
//    }
//}
