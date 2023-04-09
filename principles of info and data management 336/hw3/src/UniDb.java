// Alexander Zhao
// netID: ayz10

import java.sql.*;
import java.util.Scanner;

public class UniDb {

    public static void searchByName (Connection connection, String name) throws SQLException {
        String query1 = "SELECT DISTINCT id FROM students inner join majors on students.id = majors.sid inner join minors on majors.sid = minors.sid WHERE first_name LIKE ? OR last_name LIKE ?";
        PreparedStatement statement1 = connection.prepareStatement(query1);
        statement1.setString(1, "%" + name + "%");
        statement1.setString(2, "%" + name + "%");
        ResultSet rs1 = statement1.executeQuery();

        int studentCount = 0;
        while (rs1.next()){
            studentCount++;
        }
        System.out.println(studentCount + " Students found");

        String query = "select * from studentsfulldetails  WHERE first_name LIKE ? OR last_name LIKE ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, "%" + name + "%");
        statement.setString(2, "%" + name + "%");
        ResultSet rs = statement.executeQuery();
        while (rs.next()){
            int id = rs.getInt("id");
            String fName = rs.getString("first_name");
            String lName = rs.getString("last_name");
            String major = rs.getString("majors");
            String minor = rs.getString("minors");
            Double gpa = rs.getDouble("avg_gpa");
            int credits = rs.getInt("total_credits");

            System.out.println( fName + " " + lName +
                "\nId: "+id+ 
                "\nMajor: " + major + 
                "\nMinor: " + minor + 
                "\nGPA: " + gpa +
                "\nCredits: " + credits +"\n");
        }
    }

    public static void searchByYear (Connection connection, String year) throws SQLException {
        int upper=0;
        int lower=0;
        if(year.equals("Fr")){
            upper = 29;
            lower = 0;
        } else if (year.equals("So")){
            upper = 59;
            lower = 30;
        }
        else if (year.equals("Ju")){
            upper = 89;
            lower = 60;
        }
        else if (year.equals("Sr")){
            upper = 99999;
            lower = 90;
        }

        String query1 = "select * from studentsfulldetails where total_credits>= ? and total_credits <= ?";
        PreparedStatement statement1 = connection.prepareStatement(query1);
        statement1.setInt(1,lower );
        statement1.setInt(2,upper);
        ResultSet rs1 = statement1.executeQuery();

        int studentCount = 0;
        while (rs1.next()){
            studentCount++;
        }
        System.out.println(studentCount + " Students found");

        String query = "select * from studentsfulldetails where total_credits>= ? and total_credits <= ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1,lower);
        statement.setInt(2,upper);
        ResultSet rs = statement.executeQuery();
        while (rs.next()){
            int id = rs.getInt("id");
            String fName = rs.getString("first_name");
            String lName = rs.getString("last_name");
            String major = rs.getString("majors");
            String minor = rs.getString("minors");
            Double gpa = rs.getDouble("avg_gpa");
            int credits = rs.getInt("total_credits");

            System.out.println( fName + " " + lName +
                "\nId: "+id+ 
                "\nMajor: " + major + 
                "\nMinor: " + minor + 
                "\nGPA: " + gpa +
                "\nCredits: " + credits +"\n");
        }
    }

    public static void searchOverGpa(Connection connection, Double threshold) throws SQLException{
        String query1 = "select * from studentsfulldetails where avg_gpa >= ?";
        PreparedStatement statement1 = connection.prepareStatement(query1);
        statement1.setDouble(1,threshold );
        ResultSet rs1 = statement1.executeQuery();

        int studentCount = 0;
        while (rs1.next()){
            studentCount++;
        }
        System.out.println(studentCount + " Students found");

        String query = "select * from studentsfulldetails where avg_gpa >= ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setDouble(1,threshold );
        ResultSet rs = statement.executeQuery();
        while (rs.next()){
            int id = rs.getInt("id");
            String fName = rs.getString("first_name");
            String lName = rs.getString("last_name");
            String major = rs.getString("majors");
            String minor = rs.getString("minors");
            Double gpa = rs.getDouble("avg_gpa");
            int credits = rs.getInt("total_credits");

            System.out.println( fName + " " + lName +
                "\nId: "+id+ 
                "\nMajor: " + major + 
                "\nMinor: " + minor + 
                "\nGPA: " + gpa +
                "\nCredits: " + credits +"\n");
        }
    }

    public static void searchUnderGpa(Connection connection, Double threshold) throws SQLException{
        String query1 = "select * from studentsfulldetails where avg_gpa <= ?";
        PreparedStatement statement1 = connection.prepareStatement(query1);
        statement1.setDouble(1,threshold );
        ResultSet rs1 = statement1.executeQuery();

        int studentCount = 0;
        while (rs1.next()){
            studentCount++;
        }
        System.out.println(studentCount + " Students found");

        String query = "select * from studentsfulldetails where avg_gpa <= ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setDouble(1,threshold );
        ResultSet rs = statement.executeQuery();
        while (rs.next()){
            int id = rs.getInt("id");
            String fName = rs.getString("first_name");
            String lName = rs.getString("last_name");
            String major = rs.getString("majors");
            String minor = rs.getString("minors");
            Double gpa = rs.getDouble("avg_gpa");
            int credits = rs.getInt("total_credits");

            System.out.println( fName + " " + lName +
                "\nId: "+id+ 
                "\nMajor: " + major + 
                "\nMinor: " + minor + 
                "\nGPA: " + gpa +
                "\nCredits: " + credits +"\n");
        }
    }

    public static void searchDeptInfo(Connection connection, String dept) throws SQLException{
        String query = "select dname, count(dname) as count from onecolumndept where dname = ? group by dname";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1,dept );
        ResultSet rs = statement.executeQuery();
        while (rs.next()){
            int count = rs.getInt("count");
            System.out.println("Num students: "+count);
        }

        String query1 = "select dname, avg(avg_gpa) as avg_gpa from onecolumndept left join avg_gpas on onecolumndept.sid = avg_gpas.id where dname = ? group by dname";
        PreparedStatement statement1 = connection.prepareStatement(query1);
        statement1.setString(1,dept );
        ResultSet rs1 = statement1.executeQuery();
        while (rs1.next()){
            Double gpa = rs1.getDouble("avg_gpa");
            System.out.println("Average GPA: "+gpa);
        }
    }

    public static void searchClassInfo(Connection connection, String className) throws SQLException{
        String query = "select dname, count(dname) as count from istaking where dname = ? group by dname";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1,className);
        ResultSet rs = statement.executeQuery();
        while (rs.next()){
            int count = rs.getInt("count");
            System.out.println(count + " students currently enrolled");
        }

        System.out.println("Grades of previous enrolled:" + "\n");
        String query1 = "select grade, count(grade) as count from hastaken where dname = ? group by dname, grade";
        PreparedStatement statement1 = connection.prepareStatement(query1);
        statement1.setString(1,className );
        ResultSet rs1 = statement1.executeQuery();
        while (rs1.next()){
            String grade = rs1.getString("grade");
            int count1 = rs1.getInt("count");
            System.out.println(grade+ " " +count1);
        }
    }

    public static void arbitrarySQL (Connection connection, String query) throws SQLException{
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet rs = statement.executeQuery();
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();
        for (int i = 0; i < columnCount; i++){
            String name = rsmd.getColumnName(i+1);
            System.out.print(name + "\t");
        }
        System.out.print("\n");
        while(rs.next()){
            for (int i = 0; i < columnCount; i++){
                String value = rs.getString(i+1);
                System.out.print(value + "\t");
            }
            System.out.print("\n");
        }
    }

    public static void main(String[] args){
        String url = args[0];
        String userid = args[1];
        String passwd = args[2];

        // String userid = "root";
        // String passwd = "Ayz08192000!";
        // String db = "hw3";
        try (
            Connection conn = DriverManager.getConnection(
            url, userid, passwd);
            // Connection conn = DriverManager.getConnection(
            // "jdbc:mysql://localhost:3306/" + db,
            // userid, passwd);
            // Statement stmt = conn.createStatement();
            ){
                System.out.println("Welcome to the university database. Queries available:"+
                "\n1. Search students by name."+
                "\n2. Search students by year."+
                "\n3. Search for students with a GPA >= threshold."+
                "\n4. Search for students with a GPA <= threshold."+
                "\n5. Get department statistics."+
                "\n6. Get class statistics."+
                "\n7. Execute an abitrary SQL query."+
                "\n8. Exit the application."+
                "\nWhich query would you like to run (1-8)?");
                boolean runner = true;
                while(runner){
                    Scanner scanner = new Scanner(System.in);
                    while(runner & scanner.hasNext()){
                        String input = scanner.nextLine();
                        String[] tokens = input.split(" ");
                        switch (tokens[0]){
                            case "1" :
                                System.out.println("Please enter the name.");
                                Scanner scanner1 = new Scanner(System.in);
                                String input1 = scanner1.nextLine();
                                String[] tokens1 = input1.split(" "); 
                                searchByName(conn, tokens1[0]);
                                System.out.println("Which query would you like to run (1-8)?");
                                break;
                            case "2" : 
                                System.out.println("Please enter the year.");
                                scanner1 = new Scanner(System.in);
                                input1 = scanner1.nextLine();
                                tokens1 = input1.split(" "); 
                                searchByYear(conn, tokens1[0]);
                                System.out.println("Which query would you like to run (1-8)?");
                                break;
                            case "3" : 
                                System.out.println("Please enter the threshold.");
                                scanner1 = new Scanner(System.in);
                                input1 = scanner1.nextLine();
                                tokens1 = input1.split(" "); 
                                searchOverGpa(conn, Double.parseDouble(tokens1[0]));
                                System.out.println("Which query would you like to run (1-8)?");
                                break;
                            case "4" : 
                                System.out.println("Please enter the threshold.");
                                scanner1 = new Scanner(System.in);
                                input1 = scanner1.nextLine();
                                tokens1 = input1.split(" "); 
                                searchUnderGpa(conn, Double.parseDouble(tokens1[0]));
                                System.out.println("Which query would you like to run (1-8)?");
                                break;
                            case "5" : 
                                System.out.println("Please enter the department.");
                                scanner1 = new Scanner(System.in);
                                input1 = scanner1.nextLine();
                                tokens1 = input1.split(" "); 
                                searchDeptInfo(conn, tokens1[0]);
                                System.out.println("Which query would you like to run (1-8)?");
                                break;
                            case "6" : 
                                System.out.println("Please enter the class name.");
                                scanner1 = new Scanner(System.in);
                                input1 = scanner1.nextLine();
                                searchClassInfo(conn, input1);
                                System.out.println("Which query would you like to run (1-8)?");
                                break;
                            case "7" :
                                System.out.println("Please enter the query.");
                                scanner1 = new Scanner(System.in);
                                input1 = scanner1.nextLine();
                                arbitrarySQL(conn, input1);
                                System.out.println("Which query would you like to run (1-8)?");
                                break;
                            case "8" : 
                                System.out.println("Goodbye");
                                runner = false;
                                break;
                            default : System.out.println("Invalid Command");
                                break;
                        }
                    }
                    scanner.close();
                }
            }
        catch (Exception e){
            System.out.println("Exception : " + e);
        }
    }

}
