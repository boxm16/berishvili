package DAO;

import Controller.Converter;
import Model.Day;
import Model.BasicRoute;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class IndexDao {

    @Autowired
    private DataSource dataSource;
    private Converter converter;
    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;

    public TreeMap<Float, BasicRoute> getRoutes() {
        TreeMap<Float, BasicRoute> routes = new TreeMap<>();
        this.converter = new Converter();
        try {
            connection = DataSource.getInstance().getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from last_upload");
            while (resultSet.next()) {
                BasicRoute route ;
                String routeNumber = resultSet.getString("number");
                String dateStamp = resultSet.getString("date_stamp");
                Date date = this.converter.convertDateStampDatabaseFormatToDate(dateStamp);

                Day day = new Day();
                day.setDateStamp(dateStamp);
                String routeKey = routeNumber.replace("-", ".");
                Float routeNumberFloat = this.converter.convertRouteNumber(routeNumber);
                if (routes.containsKey(routeNumberFloat)) {
                    route = routes.get(routeNumberFloat);

                } else {
                    route = new BasicRoute();

                }
                route.setNumber(routeNumber);
                route.getDays().put(date, day);
                routes.put(Float.valueOf(routeKey), route);
            }
            resultSet.close();
            statement.close();
            connection.close();
            return routes;
        } catch (IOException ex) {
            Logger.getLogger(IndexDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(IndexDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(IndexDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return routes;
    }

}
