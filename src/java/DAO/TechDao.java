package DAO;

import Model.RouteData;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TechDao {

    @Autowired
    private DataBaseConnection dataBaseConnection;

    @Autowired
    private RouteDao routeDao;

    private Connection connection;

    public String createSchema() {
        try {
            connection = dataBaseConnection.getInitConnection();
            Statement statement = connection.createStatement();
            String sql = "CREATE SCHEMA berishvili_db";
            statement.executeUpdate(sql);
            statement.close();
            connection.close();
            return "Schema 'berishvili_db' created.";

        } catch (SQLException ex) {
            Logger.getLogger(TechDao.class.getName()).log(Level.SEVERE, null, ex);
            return ex.getMessage();
        }
    }

    public String createRouteTable() {
        String sql = "CREATE TABLE `route` ("
                + "`number` VARCHAR(10) NOT NULL, "
                + "`prefix` int(4) NOT NULL, "
                + "`suffix` INT(3) NULL, "
                + "`a_point` VARCHAR(100) NULL, "
                + "`b_point` VARCHAR(100) NULL, "
                + "`scheme` VARCHAR(5000) NULL, "
                + "PRIMARY KEY (`number`)) "
                + "ENGINE = InnoDB "
                + "DEFAULT CHARACTER SET = utf8;";

        try {
            connection = dataBaseConnection.getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            statement.close();
            connection.close();
            return "Table 'route' created successfully";
        } catch (SQLException ex) {
            if (ex.getSQLState().equals("42S01")) {
                return "Table 'route' already exists";
            } else {
                Logger.getLogger(TechDao.class.getName()).log(Level.SEVERE, null, ex);
                return ex.getMessage();
            }
        }
    }

    public String createLastUploadTable() {
        String sql = "CREATE TABLE `last_upload` ("
                + "`number` VARCHAR(10) NOT NULL, "
                + "`date_stamp` DATE NOT NULL) "
                + "ENGINE = InnoDB "
                + "DEFAULT CHARACTER SET = utf8;";

        try {
            connection = dataBaseConnection.getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            statement.close();
            connection.close();
            return "Table 'last_upload' created successfully";
        } catch (SQLException ex) {
            if (ex.getSQLState().equals("42S01")) {
                return "Table 'last_upload' already exists";
            } else {
                Logger.getLogger(TechDao.class.getName()).log(Level.SEVERE, null, ex);
                return ex.getMessage();
            }
        }
    }

    public String createTripVoucherTable() {
        String sql = "CREATE TABLE `trip_voucher` ("
                + "`number` VARCHAR(20) NOT NULL, "
                + "`route_number` VARCHAR(10) NOT NULL, "
                + "`date_stamp` DATE NOT NULL, "
                + "`base_number` INT(2) NOT NULL, "
                + "`exodus_number` INT(2) NOT NULL, "
                + "`driver_number` VARCHAR(10) NULL, "
                + "`driver_name` VARCHAR(45) NULL, "
                + "`bus_number` VARCHAR(15) NULL, "
                + "`bus_type` VARCHAR(35) NULL, "
                + "`base_leaving_time_scheduled` TIME(0) NULL  DEFAULT NULL, "
                + "`base_leaving_time_actual` TIME(0) NULL  DEFAULT NULL, "
                + "`base_leaving_time_redacted` TIME(0) NULL  DEFAULT NULL, "
                + "`base_return_time_scheduled` TIME(0) NULL  DEFAULT NULL, "
                + "`base_return_time_actual` TIME(0) NULL  DEFAULT NULL, "
                + "`base_return_time_redacted` TIME(0) NULL  DEFAULT NULL, "
                + "`notes` VARCHAR(2000) NULL, "
                + "PRIMARY KEY (`number`), "
                + "CONSTRAINT `route_number` "
                + "FOREIGN KEY (`route_number`) "
                + "REFERENCES `route` (`number`) "
                + "ON DELETE CASCADE "
                + "ON UPDATE NO ACTION) "
                + "ENGINE = InnoDB "
                + "DEFAULT CHARACTER SET = utf8;";

        try {
            connection = dataBaseConnection.getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            statement.close();
            connection.close();
            return "Table 'trip_voucher' created successfully";
        } catch (SQLException ex) {
            if (ex.getSQLState().equals("42S01")) {
                return "Table 'trip_voucher' already exists";
            } else {
                Logger.getLogger(TechDao.class.getName()).log(Level.SEVERE, null, ex);
                return ex.getMessage();
            }
        }
    }

    public String createTripPeriodTable() {
        String sql = "CREATE TABLE `trip_period` ("
                + "`trip_voucher_number` VARCHAR(20) NOT NULL, "
                + "`type` VARCHAR(15) NOT NULL, "
                + "`start_time_scheduled` TIME(0) NULL  DEFAULT NULL, "
                + "`start_time_actual` TIME(0) NULL  DEFAULT NULL, "
                + "`start_time_difference` VARCHAR(10) NULL, "
                + "`arrival_time_scheduled` TIME(0) NULL  DEFAULT NULL, "
                + "`arrival_time_actual` TIME(0) NULL  DEFAULT NULL, "
                + "`arrival_time_difference` VARCHAR(10) NULL, "
                + "CONSTRAINT `trip_voucher` "
                + "FOREIGN KEY (`trip_voucher_number`) "
                + "REFERENCES `trip_voucher` (`number`) "
                + "ON DELETE CASCADE "
                + "ON UPDATE NO ACTION) "
                + "ENGINE = InnoDB "
                + "DEFAULT CHARACTER SET = utf8";

        try {
            connection = dataBaseConnection.getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            statement.close();
            connection.close();
            return "Table 'trip_period' created successfully";
        } catch (SQLException ex) {
            if (ex.getSQLState().equals("42S01")) {
                return "Table 'trip_period' already exists";
            } else {
                Logger.getLogger(TechDao.class.getName()).log(Level.SEVERE, null, ex);
                return ex.getMessage();
            }
        }
    }

    //---------------------//----------------------//----------------------
    public String uploadRoutesNamesData(TreeMap<Float, RouteData> routesData) {

        try {
            connection = dataBaseConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT number FROM route");
            PreparedStatement deleteStatement = connection.prepareStatement("DELETE FROM route  where number = ?");
            PreparedStatement updateStatement = connection.prepareStatement("UPDATE route SET a_point = ?, b_point=?, scheme =? where number = ?");

            while (resultSet.next()) {
                String routeNumber = resultSet.getString("number");

                routeNumber = routeNumber.replace("-", ".");

                RouteData routeData = routesData.remove(Float.valueOf(routeNumber));
                if (routeData == null) {
                    deleteStatement.setString(1, routeNumber);
                    deleteStatement.addBatch();
                } else {
                    updateStatement.setString(1, routeData.getaPoint());
                    updateStatement.setString(2, routeData.getbPoint());
                    updateStatement.setString(3, routeData.getScheme());
                    updateStatement.setString(4, routeNumber);
                    updateStatement.addBatch();
                }
            }
            updateStatement.executeBatch();
            deleteStatement.executeBatch();

            resultSet.close();
            updateStatement.close();
            deleteStatement.close();

            PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO route (number, prefix, suffix, a_point, b_point, scheme) VALUES (?,?,?,?,?,?)");
            String prefix;
            String suffix;
            for (Map.Entry<Float, RouteData> entry : routesData.entrySet()) {
                RouteData routeData = entry.getValue();
                String routeNumber = routeData.getNumber();

                if (routeNumber.contains("-")) {
                    prefix = routeNumber.split("-")[0];
                    suffix = routeNumber.split("-")[1];
                } else {
                    prefix = routeNumber;
                    suffix = null;
                }
                String aPoint = routeData.getaPoint();
                String bPoint = routeData.getbPoint();
                String scheme = routeData.getScheme();

                insertStatement.setString(1, routeNumber);
                insertStatement.setString(2, prefix);
                insertStatement.setString(3, suffix);
                insertStatement.setString(4, aPoint);
                insertStatement.setString(5, bPoint);
                insertStatement.setString(6, scheme);
                insertStatement.addBatch();
            }
            insertStatement.executeBatch();

            insertStatement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(TechDao.class.getName()).log(Level.SEVERE, null, ex);
            return ex.getMessage();
        }
        return "Data from excel file has been inserted successfully into database";
    }

    public String deleteRouteTable() {
        try {
            connection = dataBaseConnection.getConnection();
            Statement statement = connection.createStatement();
            String sql = "DROP TABLE route";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Table route deleted successfully";
    }

    public String deleteLastUploadTable() {
        try {
            connection = dataBaseConnection.getConnection();
            Statement statement = connection.createStatement();
            String sql = "DROP TABLE last_upload";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Table last_upload deleted successfully";
    }

    public String deleteTripVoucherTable() {
        try {
            connection = dataBaseConnection.getConnection();
            Statement statement = connection.createStatement();
            String sql = "DROP TABLE trip_voucher";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Table trip_voucher deleted successfully";
    }

    public String deleteTripPeriodTable() {
        try {
            connection = dataBaseConnection.getConnection();
            Statement statement = connection.createStatement();
            String sql = "DROP TABLE trip_period";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Table trip_period deleted successfully";
    }

}
