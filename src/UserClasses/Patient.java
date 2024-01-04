package UserClasses;


import java.sql.*;

public class Patient extends Users {
    public Patient(String name, String surname, String birthday, String password, String gender, String tel) {
        super(name,surname,birthday,password,gender,tel);

    }
    public void addPatientDatabase(){
        Connection connection = DBConnection.getConnection();
        String insertQuery = "INSERT INTO Patient (patientID) VALUES (?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setInt(1, userID);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Done!");
            } else {
                System.out.println("Error!");
            }
            preparedStatement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
