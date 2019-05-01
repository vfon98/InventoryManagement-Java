
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class DataProvider {

//    private static DataProvider instance;
    private static Connection conn = null;
    private static PreparedStatement ps;
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
            e.printStackTrace();
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
            ex.printStackTrace();
        }
    }

    public static boolean isLogin(String user, String pass) {
        try {
            String sql = "SELECT * FROM Account WHERE [User] = ? AND [Pass] = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, user);
            ps.setString(2, pass);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                SharedData.setUser(rs.getString("User"));
                SharedData.setRole(rs.getString("Role"));
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public static ResultSet getAllAccounts() {
        try {
            getConnection();
            String sql = "SELECT * FROM Account";
            ps = conn.prepareStatement(sql);
            return ps.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void insertAccount(String user, String pass, String role) throws SQLException {
        String sql = "INSERT INTO Account VALUES (?, ?, ?)";
        ps = conn.prepareStatement(sql);
        ps.setString(1, user);
        ps.setString(2, pass);
        ps.setString(3, role);
        ps.executeUpdate();
    }

    public static void updateAccount(String user, String newPass, String newRole) {
        try {
            String sql = "UPDATE Account SET Pass = ?, [Role] = ? WHERE [User] = ?";
            ps = conn.prepareCall(sql);
            ps.setString(1, newPass);
            ps.setString(2, newRole);
            ps.setString(3, user);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteAccount(String user) {
        try {
            String sql = "DELETE FROM Account WHERE [User] = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, user);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
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

    public static void updateCategory(String id, String newName, String newDesc) {
        try {
            String sql = "UPDATE Category SET Name = ?, Description = ? WHERE ID = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, newName);
            ps.setString(2, newDesc);
            ps.setString(3, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DataProvider.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void deleteCategory(String id) {
        try {
            String sql = "DELETE FROM Category WHERE ID = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DataProvider.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static String getStaffId() {
        int id = 0;
        try {
            getConnection();
            String sql = "SELECT MAX(No) FROM Staff";
            ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (SQLException ex) {
            System.out.println("Empty Staff Table");
        }
        return String.format("NV%03d", id);
    }

    public static ResultSet getAllStaffs() {
        try {
            String sql = "SELECT * FROM Staff";
            ps = conn.prepareStatement(sql);
            return ps.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(DataProvider.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static ResultSet getStaffName() {
        try {
            getConnection();
            String sql = "SELECT Name FROM Staff";
            ps = conn.prepareStatement(sql);
            return ps.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void insertStaff(String id, String name, String address, String phoneNumber) {
        try {
            String sql = "INSERT INTO Staff (ID, Name, Address, PhoneNumber) VALUES (?, ?, ?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            ps.setString(2, name);
            ps.setString(3, address);
            ps.setString(4, phoneNumber);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateStaff(String id, String newName, String newAddr, String newPhone) {
        try {
            String sql = "UPDATE Staff SET Name = ?, Address = ?, PhoneNumber = ? WHERE ID = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, newName);
            ps.setString(2, newAddr);
            ps.setString(3, newPhone);
            ps.setString(4, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteStaff(String id) {
        try {
            String sql = "DELETE FROM Staff WHERE ID = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ResultSet getAllCategoryName() {
        try {
            getConnection();
            String sql = "SELECT Name FROM Category";
            ps = conn.prepareStatement(sql);
            return ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getProductId() {
        int id = 0;
        try {
            String sql = "SELECT MAX(No) FROM ImportBill";
            ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("Empty ImportBill Table");
        }
        return String.format("HH%03d", id);
    }

    public static ResultSet getAllImport() {
        try {
            getConnection();
            String sql = "SELECT * FROM ImportBill";
            ps = conn.prepareStatement(sql);
            return ps.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ResultSet getAllExport() {
        try {
            getConnection();
            String sql = "SELECT * FROM ExportBill";
            ps = conn.prepareStatement(sql);
            return ps.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ResultSet getImportByDate(String fromDate, String toDate) {
        try {
            getConnection();
            String sql = "SELECT * FROM ImportBill WHERE Created_at BETWEEN ? AND ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, fromDate);
            ps.setString(2, toDate);
            return ps.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ResultSet getExportByDate(String fromDate, String toDate) {
        try {
            getConnection();
            String sql = "SELECT * FROM ExportBill WHERE Created_at BETWEEN ? AND ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, fromDate);
            ps.setString(2, toDate);
            return ps.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ResultSet getDetailBill(String billId) {
        try {
            String sql = "SELECT * FROM DetailBill WHERE BillID = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, billId);
            return ps.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void insertImportBill(String id, String name, String category, String origin, int quantity, String unit, long impPrice, long expPrice) {
        try {
            String sql = "INSERT INTO ImportBill (ID, Name, Category, Origin, Quantity, Unit, ImpPrice, ExpPrice) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            ps.setString(2, name);
            ps.setString(3, category);
            ps.setString(4, origin);
            ps.setInt(5, quantity);
            ps.setString(6, unit);
            ps.setLong(7, impPrice);
            ps.setLong(8, expPrice);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertProduct(String id, String name, long price, int quan) {
        try {
            String sql = "INSERT INTO Products VALUES (?, ?, ?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            ps.setString(2, name);
            ps.setLong(3, price);
            ps.setInt(4, quan);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ResultSet getAllProducts(String keyword) {
        try {
            getConnection();
            String sql = "SELECT ID, Name, Price, Remain FROM Products WHERE Name LIKE ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + keyword + "%");
            return ps.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ResultSet getAllInventory() {
        try {
            getConnection();
            String sql = "SELECT i.ID, i.Name, i.Category, i.Origin, i.ImpPrice, i.ExpPrice, p.Remain, Created_at "
                    + "FROM ImportBill i, Products p WHERE i.ID = p.ID";
            ps = conn.prepareStatement(sql);
            return ps.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getExportID() {
        int id = 0;
        try {
            getConnection();
            String sql = "SELECT MAX(No) FROM ExportBill";
            ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("Empty ExportBill Table");
        }
        return String.format("HD%03d", id);
    }

    public static void insertExportBill(String id, String cus, String add, String phone, String staff, long price) {
        try {
            String sql = "INSERT INTO ExportBill (ID, Customer, Address, Phone, Staff, Price) VALUES (?, ?, ?, ?, ?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            ps.setString(2, cus);
            ps.setString(3, add);
            ps.setString(4, phone);
            ps.setString(5, staff);
            ps.setLong(6, price);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void insertDetailBill(String billId, String proId, String name, long price, int quan) {
        try {
            String sql = "INSERT INTO DetailBill VALUES (?, ?, ?, ?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, proId);
            ps.setString(2, name);
            ps.setLong(3, price);
            ps.setInt(4, quan);
            ps.setString(5, billId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getProviderID() {
        int id = 0;
        try {
            getConnection();
            String sql = "SELECT MAX(No) FROM Provider";
            ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (SQLException ex) {
            System.out.println("Empty Provider table");
        }
        return String.format("NCC%03d", id);
    }
    
    public static ResultSet getAllProviders() {
        try {
            String sql = "SELECT * FROM Provider";
            ps = conn.prepareStatement(sql);
            return ps.executeQuery();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static void insertProvider(String id, String name, String add, String phone) {
        try {
            String sql = "INSERT INTO Provider VALUES (?, ?, ?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            ps.setString(2, name);
            ps.setString(3, add);
            ps.setString(4, phone);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void updateProvider(String id, String newName, String newAdd, String newPhone) {
        try {
            String sql = "UPDATE Provider SET Name = ?, Address = ?, Phone = ? WHERE ID = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, newName);
            ps.setString(2, newAdd);
            ps.setString(3, newPhone);
            ps.setString(4, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public static void deleteProvider(String id) {
        try {
            String sql = "DELETE FROM Provider WHERE ID = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
