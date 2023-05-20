import java.sql.*;
import java.util.Locale;

public class classForBD {

    private ResultSet rs;
    private Connection con;
    private Statement statement;

    public classForBD() {
        Locale.setDefault(Locale.ENGLISH);
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "mik_6307", "11");
            statement = con.createStatement();
            rs = statement.executeQuery(
                    "select * from paper"
            );

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (con == null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public ResultSet getRS() {
            return rs;
    }

    public Statement getStatement(){
        return statement;
    }

}