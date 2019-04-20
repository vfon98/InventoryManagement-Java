
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class DataProvider {

//    private static DataProvider instance;
    private static Connection conn = null;
    private static PreparedStatement ps;
    private static Statement st;
    private static final String dbUrl = "jdbc:sqlserver://localhost\\SQLExpress:1433;user=sa;password=vphong98;databaseName=InventoryManager";

    private DataProvider() {
    }

    public static void getConnection() {
        try {
            if (conn == null || conn.isClosed()) {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                conn = DriverManager.getConnection(dbUrl);
                System.out.println("Connected to SQL Server");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Cann't connect to SQL Server");
            System.exit(0);
        }
    }

    public static void closeConnection() {
        try {
            if (!conn.isClosed()) {
                conn.close();
            }
            System.out.println("Connection Closed");
        } catch (SQLException ex) {
            Logger.getLogger(DataProvider.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static String getCategoryId() {
        int id = 0;
        try {
            getConnection();
            String sql = "SELECT MAX(No) FROM Category";
            ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (SQLException ex) {
            System.out.println("Empty Category table");
        }
        return String.format("DM%03d", id);
    }

    public static void insertCategory(String id, String name, String description) {
        try {
            String sql = "INSERT INTO Category VALUES (?, ?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            ps.setString(2, name);
            ps.setString(3, description);
            ps.executeUpdate();
            System.out.println("Inserted to Category");
        } catch (SQLException ex) {
            Logger.getLogger(DataProvider.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static ResultSet getAllCategories() {
        try {
            String sql = "SELECT * FROM Category";
            ps = conn.prepareStatement(sql);
            return ps.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(DataProvider.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
