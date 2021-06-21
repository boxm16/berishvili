package DAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Repository;

@Repository
public class TechDao {

    public String createSchema() {
        try {
            Connection connection = DataBaseConnection.getInitConnection();
            Statement statement = connection.createStatement();
            String sql = "CREATE SCHEMA berishvili_db";
            int count = statement.executeUpdate(sql);
            if (count == 1) {
                return "Schema 'berishvili_db' created.";
            }

        } catch (SQLException ex) {
            Logger.getLogger(TechDao.class.getName()).log(Level.SEVERE, null, ex);
            return ex.getMessage();

        }
        return "Schema couldn`t be created.";
    }

}
