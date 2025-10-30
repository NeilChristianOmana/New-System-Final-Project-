import java.sql.*;
import java.util.Scanner;

public class Main {
    static final String URL = "jdbc:mysql://localhost:3306/incident_system?useSSL=false";
    static final String USER = "root";
    static final String PASSWORD = "Bens@200694";

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            System.out.println("Connected");
            Scanner sc = new Scanner(System.in);

            while (true) {
                System.out.println("");
                System.out.println("1. View All Records");
                System.out.println("2. Insert New Record");
                System.out.println("3. Exit");
                System.out.print("Enter choice: ");
                int choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {
                    case 1 -> viewRecords(conn);
                    case 2 -> insertRecord(conn, sc);
                    case 3 -> {
                        System.out.println("Exit");
                        return;
                    }
                    default -> System.out.println("Invalid choice, please try again.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Connection failed!");
            e.printStackTrace();
        }
    }
    public static void viewRecords(Connection conn) {
        String query = "SELECT id, Persons_involve, Date_recorded FROM non_fatal ORDER BY id ASC";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            System.out.println("\n--- LIVESTOCK RECORDS ---");
            System.out.printf("%-5s %-20s %-15s%n", "ID", "Persons Involve", "Date Recorded");

            while (rs.next()) {
                int id = rs.getInt("id");
                String Person = rs.getString("Persons_involve");
                Date date = rs.getDate("Date_recorded");
                System.out.printf("%-5d %-20s %-15s%n", id, Person, date);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving data!");
            e.printStackTrace();
        }
    }
    public static void insertRecord(Connection conn, Scanner sc) {
        String sql = """
            INSERT INTO non_fatal (
                        Incident_type varchar,
                        Select_type varchar,
                        recorded_time,
                        Date_of_Incident,
                        Location_or_Zone,
                        Persons_involve,
                        Persons_status,
                        Description_of_incident ,
                        Date_recorded
            ) VALUES (?,?,?,?,?,?,?,?)
        """;
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            System.out.print("Enter Name: ");
            String Person = sc.nextLine();
            pstmt.setString(1, Person);

            int index = 2;
            String[] incident = {
                    "Incident_type",
                    "Select_type",
                    "recorded_time",
                    "Date_of_Incident",
                    "Location_or_Zone",
                    "Persons_involve",
                    "Persons_status",
                    "Description_of_incident",
            };
            for (String person : incident) {
                System.out.print(person + " (Incident_type): ");
                String Incident_type = sc.nextLine();
                System.out.print(person + " (Select_type): ");
                String Select_type = sc.nextLine();
                System.out.print(person + " (recorded_time): ");
                int recorded_time = sc.nextInt();
                System.out.print(person + " (Date_of_Incident): ");
                int Date_of_Incident = sc.nextInt();
                System.out.println(person + " (Location_or_Zone): ");
                String Location_or_Zone = sc.nextLine();
                System.out.print(person + " (Persons_involve): ");
                String Persons_involve = sc.nextLine();
                System.out.print(person + " (Persons_status): ");
                String Persons_status = sc.nextLine();
                System.out.print(person + " (Description_of_incident): ");
                String Description_of_incident = sc.nextLine();

                pstmt.setString(index++, Incident_type);
                pstmt.setString(index++, Select_type);
                pstmt.setInt(index++, recorded_time);
                pstmt.setInt(index++, Date_of_Incident);
                pstmt.setString(index++, Location_or_Zone);
                pstmt.setString(index++, Persons_involve);
                pstmt.setString(index++,Persons_status);
                pstmt.setString(index++, Description_of_incident);
            }
            sc.nextLine();

            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("New livestock record added successfully!");
            }
        } catch (SQLException e) {
            System.out.println("Error inserting record!");
            e.printStackTrace();
        }
    }
}