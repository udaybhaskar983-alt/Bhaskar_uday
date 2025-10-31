
import java.sql.*;
import java.util.Scanner;

public class StudentManagement {
    static final String URL = "jdbc:mysql://localhost:3306/studentdb";
    static final String USER = "root";
    static final String PASS = "yourpassword";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n=== Student Management System ===");
            System.out.println("1. Add Student");
            System.out.println("2. View Students");
            System.out.println("3. Update Student");
            System.out.println("4. Delete Student");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1 -> addStudent();
                case 2 -> viewStudents();
                case 3 -> updateStudent();
                case 4 -> deleteStudent();
                case 5 -> System.out.println("Exiting...");
                default -> System.out.println("Invalid choice!");
            }
        } while (choice != 5);
    }

    public static void addStudent() {
        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             Scanner sc = new Scanner(System.in)) {

            System.out.print("Enter Name: ");
            String name = sc.nextLine();
            System.out.print("Enter Age: ");
            int age = sc.nextInt();
            sc.nextLine();
            System.out.print("Enter Course: ");
            String course = sc.nextLine();
            System.out.print("Enter Grade: ");
            String grade = sc.nextLine();

            String query = "INSERT INTO students (name, age, course, grade) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, name);
            ps.setInt(2, age);
            ps.setString(3, course);
            ps.setString(4, grade);
            ps.executeUpdate();

            System.out.println(" Student added successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void viewStudents() {
        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             Statement st = con.createStatement()) {

            ResultSet rs = st.executeQuery("SELECT * FROM students");
            System.out.println("\nID\tName\tAge\tCourse\tGrade");
            System.out.println("--------------------------------------");
            while (rs.next()) {
                System.out.println(rs.getInt("id") + "\t" + rs.getString("name") + "\t"
                        + rs.getInt("age") + "\t" + rs.getString("course") + "\t" + rs.getString("grade"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateStudent() {
        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             Scanner sc = new Scanner(System.in)) {

            System.out.print("Enter Student ID to update: ");
            int id = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter new Grade: ");
            String grade = sc.nextLine();

            String query = "UPDATE students SET grade=? WHERE id=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, grade);
            ps.setInt(2, id);
            int rows = ps.executeUpdate();

            if (rows > 0)
                System.out.println(" Student updated successfully!");
            else
                System.out.println(" No student found with that ID.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteStudent() {
        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             Scanner sc = new Scanner(System.in)) {

            System.out.print("Enter Student ID to delete: ");
            int id = sc.nextInt();

            String query = "DELETE FROM students WHERE id=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, id);
            int rows = ps.executeUpdate();

            if (rows > 0)
                System.out.println(" Student deleted successfully!");
            else
                System.out.println(" No student found with that ID.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
   
