public class SharedData {
    private static String user = "Bạn chưa đăng nhập";
    private static String role = "";
    
    private SharedData() {}

    public static String getUser() {
        return user;
    }

    public static void setUser(String user) {
        SharedData.user = user;
    }

    public static String getRole() {
        return role;
    }

    public static void setRole(String role) {
        SharedData.role = role;
    }
}
