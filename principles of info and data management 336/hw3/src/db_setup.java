import java.sql.*;
import java.util.concurrent.ThreadLocalRandom;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;

public class db_setup {
    public static void main(String[] args){
        String userid = "root";
        String passwd = "Ayz08192000!";
        String db = "hw3";
        try (
            Connection conn = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/" + db,
            userid, passwd);
            Statement stmt = conn.createStatement();
            ){

                //insert students

                insertStudents(conn, "students.txt");
                // ResultSet rs = stmt.executeQuery(
                //     "SELECT * from students");
                // while (rs.next()) {
                //     System.out.println(rs.getString("first_name") + " " +
                //     rs.getString("last_name")+ " " + rs.getInt("id"));
                // }

                // insert departments

                insertdept(conn);
                // rs = stmt.executeQuery(
                //     "SELECT * from departments");
                // while (rs.next()) {
                //     System.out.println(rs.getString("name") + " " +
                //     rs.getString("campus"));
                // }

                // insert classes
                insertClasses(conn, "classes.txt");
                // ResultSet rs = stmt.executeQuery(
                //     "SELECT * from classes");
                // while (rs.next()) {
                //     System.out.println(rs.getString("name") + " " +
                //     rs.getInt("credits"));
                // }
                //
                //insert majors
                //
                insertMajors(conn, "majors.txt");
                // ResultSet rs = stmt.executeQuery(
                // "SELECT * from majors");
                // while (rs.next()) {
                //     System.out.println(rs.getInt("sid") + " " +
                //     rs.getString("dname"));
                // }
                //
                //insert minors
                //
                insertMinors(conn, "minors.txt");
                // ResultSet rs = stmt.executeQuery(
                // "SELECT * from minors");
                // while (rs.next()) {
                //     System.out.println(rs.getInt("sid") + " " +
                //     rs.getString("dname"));
                // }
                insertHasTaken(conn,"taken.txt", "classes.txt", "students.txt");
                // ResultSet rs = stmt.executeQuery(
                // "SELECT * from hasTaken");
                // while (rs.next()) {
                //     System.out.println(rs.getInt("sid") + " " +
                //     rs.getString("dname") + 
                //     rs.getString("grade"));
                // }
        }
        catch (Exception e){
            System.out.println("Exception : " + e);
        }
    }

    public static void insertdept(Connection connection) throws SQLException{
        String insertDepartments = "INSERT INTO departments (name, campus) VALUES ('Bio', 'Busch'), ('Chem', 'CAC'), ('CS', 'Livi'), ('Eng', 'CD'), ('Math', 'Busch'), ('Phys', 'CAC')";
        Statement statement = connection.createStatement();
        statement.executeUpdate(insertDepartments);
    }

    public static void insertStudents(Connection connection, String studentFile) throws FileNotFoundException, IOException, SQLException{
        try (BufferedReader br = new BufferedReader(new FileReader(studentFile))){
            String line;
            int i = 1; 
            while ((line = br.readLine()) != null){

                    line = line + " " + i;
                    String[] student = line.split(" ");
                    String query =  "INSERT INTO students (first_name, last_name, id) VALUES (?, ?, ?)";
                    PreparedStatement insertStudentStatment = connection.prepareStatement(query);
                    insertStudentStatment.setString(1, student[0]);
                    insertStudentStatment.setString(2, student[1]);
                    insertStudentStatment.setInt(3, Integer.parseInt(student[2]));
                    insertStudentStatment.executeUpdate();
                    // System.out.println(line);
                    i++;
            }
        }
    }

    public static void insertClasses(Connection connection, String classFile) throws FileNotFoundException, IOException, SQLException{
        try (BufferedReader br = new BufferedReader(new FileReader(classFile))){
            String line;
            while ((line = br.readLine()) != null){
                    String[] classes = line.split(";");
                    // System.out.println(classes[0] + " | " +classes[1]);

                    String query =  "INSERT INTO classes (name, credits) VALUES (?, ?)";
                    PreparedStatement insertClassesStatment = connection.prepareStatement(query);
                    insertClassesStatment.setString(1, classes[0]);
                    insertClassesStatment.setInt(2, Integer.parseInt(classes[1]));
                    insertClassesStatment.executeUpdate();
                    // System.out.println(line);
            }
        }
    }

    public static void insertMajors(Connection connection, String majorFile) throws FileNotFoundException, IOException, SQLException{
        try(BufferedReader br = new BufferedReader(new FileReader(majorFile))){
            String line;
            while((line = br.readLine())!= null){
                String[] major = line.split(";");
                // System.out.println(major[0] + " " +major[1]);
                String query = "INSERT INTO majors (sid, dname) VALUES (?, ?)";
                PreparedStatement insertMajorsClasses = connection.prepareStatement(query);
                insertMajorsClasses.setInt(1, Integer.parseInt(major[0]));
                insertMajorsClasses.setString(2, major[1]);
                insertMajorsClasses.executeUpdate();
            }
        }
    }

    public static void insertMinors(Connection connection, String minorFile) throws FileNotFoundException, IOException, SQLException{
        try(BufferedReader br = new BufferedReader(new FileReader(minorFile))){
            String line;
            while((line = br.readLine())!= null){
                String[] minor = line.split(";");
                // System.out.println(minor[0] + " " +minor[1]);
                String query = "INSERT INTO minors (sid, dname) VALUES (?, ?)";
                PreparedStatement insertMiinorsClasses = connection.prepareStatement(query);
                insertMiinorsClasses.setInt(1, Integer.parseInt(minor[0]));
                insertMiinorsClasses.setString(2, minor[1]);
                insertMiinorsClasses.executeUpdate();
            }
        }
    }

    public static void insertHasTaken(Connection connection, String takenFile, String classFile, String studentFile) throws FileNotFoundException, IOException, SQLException{
        String[] possGrades = {"A", "B", "C", "D", "F"};
        String [] allClasses = new String[100];
        String [] taken = new String[100];

        try(BufferedReader br = new BufferedReader(new FileReader(classFile))){
            String line;
            int i = 0;
            while((line = br.readLine())!= null){
                String[] classes = line.split(";");
                allClasses[i] = classes[0];
                i++;
            }
        }

        try(BufferedReader br = new BufferedReader(new FileReader(takenFile))){
            String line;

            int i = 0;
            while((line = br.readLine())!= null){
                String[] classes = line.split(";");
                taken[i] = classes[1];
                i++;
            }
        }

        for (int sid = 0; sid <100; sid++){
            int numTaken = Integer.parseInt(taken[sid]);
            for (int i = 0; i < numTaken; i++){
                int st_id = sid+ 1;
                int rand = ThreadLocalRandom.current().nextInt(0, 4 + 1);
                String grade = possGrades[rand];
                String name = allClasses[i];
                String query = "INSERT INTO hastaken (sid, dname, grade) VALUES (?, ?, ?)";
                PreparedStatement insertTakenStatment = connection.prepareStatement(query);
                insertTakenStatment.setInt(1, st_id);
                insertTakenStatment.setString(2, name);
                insertTakenStatment.setString(3, grade);
                insertTakenStatment.executeUpdate();
                if (i == numTaken - 1){
                    int numTaking = 4;
                    for(int j = 1; j < numTaking; j++){
                        int index = j + i;
                        String currentName = allClasses[index];
                        String query2 = "INSERT INTO istaking (sid, dname) VALUES (?, ?)";
                        PreparedStatement insertTakingStatment = connection.prepareStatement(query2);
                        insertTakingStatment.setInt(1, st_id);
                        insertTakingStatment.setString(2, currentName);
                        insertTakingStatment.executeUpdate();
                    }
                }
            }
        }

        for(int j = 1; j < 4; j++){
            String query2 = "INSERT INTO istaking (sid, dname) VALUES (?, ?)";
            PreparedStatement insertTakingStatment = connection.prepareStatement(query2);
            insertTakingStatment.setInt(1, 1);
            insertTakingStatment.setString(2, allClasses[j]);
            insertTakingStatment.executeUpdate();
        }
    }

}
