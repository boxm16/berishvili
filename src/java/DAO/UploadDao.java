package DAO;

import Model.Day;
import Model.Route;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UploadDao {

    @Autowired
    private DataSource dataSource;
    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;

    public String deleteLastUpload() {
        try {
            connection = DataSource.getInstance().getConnection();
            statement = connection.createStatement();
            statement.executeUpdate("delete from last_upload");
            statement.close();
            connection.close();
            return "Last uploaded routes and dates has been deleted";
        } catch (IOException ex) {
            Logger.getLogger(IndexDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(IndexDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(IndexDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Last uploaded routes and dates cant be deleted";
    }

    public String insertNewUpload(TreeMap<Float, Route> routesNumbersAndDatesFromUploadedExcelFile) {
        try {
            connection = DataSource.getInstance().getConnection();
            statement = connection.createStatement();
            PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO last_upload (number, date_stamp) VALUES (?,?)");
            for (Map.Entry<Float, Route> routeEntry : routesNumbersAndDatesFromUploadedExcelFile.entrySet()) {
                Route route = routeEntry.getValue();
                TreeMap<Date, Day> days = route.getDays();
                for (Map.Entry<Date, Day> dayEntry : days.entrySet()) {
                    Day day = dayEntry.getValue();
                    String routeNumber = route.getNumber();
                    insertStatement.setString(1, routeNumber);
                    insertStatement.setString(2, day.getDateStamp());
                    insertStatement.addBatch();
                }
            }
            insertStatement.executeBatch();
        } catch (SQLException ex) {
            Logger.getLogger(TechDao.class.getName()).log(Level.SEVERE, null, ex);
            return ex.getMessage();
        } catch (IOException ex) {
            Logger.getLogger(UploadDao.class.getName()).log(Level.SEVERE, null, ex);
            return ex.getMessage();
        } catch (PropertyVetoException ex) {
            Logger.getLogger(UploadDao.class.getName()).log(Level.SEVERE, null, ex);
            return ex.getMessage();
        }
        return "Data from excel file has been inserted successfully into database";
    }
}
