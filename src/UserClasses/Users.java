package UserClasses;
import java.sql.*;

public class Users {
    int userID;
    String name;
    String surname;
    String password;
    String birthday;
    String gender;
    String tel;

    public Users(String name, String surname, String birthday, String password, String gender, String tel) {

        this.userID = findMaxUserID();
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.birthday = birthday;
        this.gender = gender;
        this.tel = tel;



    }
    public void addUsersDatabase(){
        Connection connection = DBConnection.getConnection();
        String insertQuery = "INSERT INTO Users (userID, Pass, userName, userSurname, birthday, gender, phoneNumber) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setInt(1, userID);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, name);
            preparedStatement.setString(4, surname);
            preparedStatement.setString(5, birthday);
            preparedStatement.setString(6, gender);
            preparedStatement.setInt(7, Integer.parseInt(tel));


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
    public int findMaxUserID(){
        int userID;
        int lastUserID=0;
        try {
            Statement stmt = DBConnection.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery("SELECT MAX(userID) FROM Users");

            while (rs.next()){
                lastUserID = rs.getInt(1);
            }
            userID=lastUserID+1;
            return userID;


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getUserID(){
        return userID;
    }
}

