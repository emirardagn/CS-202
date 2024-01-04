import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import UserClasses.DBConnection;
import UserClasses.Patient;
import UserClasses.Users;


public class Main {

    private static JTextField usernameField;
    private static JPasswordField passwordField;
    private static JTextField nameField;
    private static JTextField surnameField;
    private static JPasswordField passwordFieldSignUp;
    private static JTextField birthDateField;
    private static JTextField genderField;
    private static JTextField telField;
    private static String selectedRole;
    public static void main(String[] args) {
        // Login screen
        JRadioButton radioButtonPatient = new JRadioButton("Patient");
        JRadioButton radioButtonManager = new JRadioButton("Manager");
        JRadioButton radioButtonDoctor = new JRadioButton("Doctor");
        JRadioButton radioButtonNurse = new JRadioButton("Nurse");

        ButtonGroup buttonGroup = new ButtonGroup();

        buttonGroup.add(radioButtonPatient);
        buttonGroup.add(radioButtonDoctor);
        buttonGroup.add(radioButtonNurse);
        buttonGroup.add(radioButtonManager);


        JPanel loginPanel = new JPanel(new GridLayout(5, 2));
        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        JButton loginButton = new JButton("Login");
        JButton signUpButton = new JButton("Sign Up");

        loginPanel.add(usernameLabel);
        loginPanel.add(usernameField);
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordField);
        loginPanel.add(loginButton);
        loginPanel.add(signUpButton);

        loginPanel.add(radioButtonPatient);
        loginPanel.add(radioButtonDoctor);
        loginPanel.add(radioButtonNurse);
        loginPanel.add(radioButtonManager);

        // Sign up screen
        JPanel signUpPanel = new JPanel(new GridLayout(7, 2));
        JLabel nameLabel = new JLabel("Name:");
        JLabel surnameLabel = new JLabel("Surname:");
        JLabel passwordLabelSignUp = new JLabel("Password:");
        JLabel birthDateLabel = new JLabel("Birth Date:");
        JLabel genderLabel = new JLabel("Gender:");
        JLabel telLabel = new JLabel("Tel:");
        nameField = new JTextField();
        surnameField = new JTextField();
        passwordFieldSignUp = new JPasswordField();
        birthDateField = new JTextField();
        genderField = new JTextField();
        telField = new JTextField();
        JButton signUpConfirmButton = new JButton("Sign Up");

        signUpPanel.add(nameLabel);
        signUpPanel.add(nameField);
        signUpPanel.add(surnameLabel);
        signUpPanel.add(surnameField);
        signUpPanel.add(passwordLabelSignUp);
        signUpPanel.add(passwordFieldSignUp);
        signUpPanel.add(birthDateLabel);
        signUpPanel.add(birthDateField);
        signUpPanel.add(genderLabel);
        signUpPanel.add(genderField);
        signUpPanel.add(telLabel);
        signUpPanel.add(telField);
        signUpPanel.add(signUpConfirmButton);
        signUpPanel.add(signUpConfirmButton);

        // Main frame
        JFrame mainFrame = new JFrame("Login Application");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(new CardLayout());
        mainFrame.add(loginPanel, "LoginPanel");
        mainFrame.add(signUpPanel, "SignUpPanel");

        // When the login button is clicked
        ItemListener itemListener = new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    selectedRole =((JRadioButton) e.getItem()).getText();
                }
            }
        };
        radioButtonPatient.addItemListener(itemListener);
        radioButtonDoctor.addItemListener(itemListener);
        radioButtonNurse.addItemListener(itemListener);
        radioButtonManager.addItemListener(itemListener);


        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userID = usernameField.getText();
                String password = new String(passwordField.getPassword());
                String selectedPanel = selectedRole+"Panel";
                Connection connection = DBConnection.getConnection();
                String insertQuery = "SELECT * FROM Users NATURAL RIGHT JOIN "+ selectedRole +" WHERE userID = ? AND Pass = ?";

                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
                    preparedStatement.setInt(1, Integer.parseInt(userID));
                    preparedStatement.setString(2, password);

                    int rowsAffected = preparedStatement.executeUpdate();
                    if (rowsAffected > 0) {
                        System.out.println("Done!");
                        CardLayout cardLayout = (CardLayout) mainFrame.getContentPane().getLayout();
                        cardLayout.show(mainFrame.getContentPane(), selectedPanel);

                    } else {
                        System.out.println("Error!");
                    }
                    preparedStatement.close();
                    connection.close();

                } catch (SQLException es) {
                    es.printStackTrace();
                }
            }
        });

        // When the sign-up button is clicked
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) mainFrame.getContentPane().getLayout();
                cardLayout.show(mainFrame.getContentPane(), "SignUpPanel");
            }
        });

        // When the sign-up confirmation button is clicked
        signUpConfirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String surname = surnameField.getText();
                String password = new String(passwordFieldSignUp.getPassword());
                String birthDate = birthDateField.getText();
                String gender = genderField.getText();
                String tel = telField.getText();
                Patient patient = new Patient(name,surname,birthDate,password,gender,tel);

                patient.addPatientDatabase();
                patient.addUsersDatabase();


                // Use the sign-up information
                JOptionPane.showMessageDialog(mainFrame, "You have signed up! your userID is: " + patient.getUserID());

                // Switch back to the login screen
                CardLayout cardLayout = (CardLayout) mainFrame.getContentPane().getLayout();
                cardLayout.show(mainFrame.getContentPane(), "PatientPanel");
            }
        });

        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }
}