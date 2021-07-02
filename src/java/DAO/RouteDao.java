package DAO;

import Model.Route;
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
public class RouteDao {

    @Autowired
    private DataBaseConnection dataBaseConnection;

    private Connection connection;

    public TreeMap<Float, RouteData> getRoutesDataFromDB() {
        TreeMap<Float, RouteData> routes = new TreeMap<>();
        try {
            connection = dataBaseConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM route");
            while (resultSet.next()) {
                RouteData route = new RouteData();
                String routeNumber = resultSet.getString("number");
                String routeKey = routeNumber.replace("-", ".");
                String aPoint = resultSet.getString("a_point");
                String bPoint = resultSet.getString("b_point");
                String scheme = resultSet.getString("scheme");
                route.setNumber(routeNumber);
                route.setaPoint(aPoint);
                route.setbPoint(bPoint);
                route.setScheme(scheme);
                routes.put(Float.valueOf(routeKey), route);
            }

        } catch (SQLException ex) {
            Logger.getLogger(TechDao.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        }
        return routes;
    }

    public <K, V> void insertRoutes(TreeMap<K, V> routes) {
        try {
            connection = dataBaseConnection.getConnection();
            PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO route (number, prefix, suffix) VALUES (?,?,?)");
            String prefix;
            String suffix;
            for (Map.Entry<K, V> entry : routes.entrySet()) {
                Route route = (Route) entry.getValue();
                String routeNumber = route.getNumber();

                if (routeNumber.contains("-")) {
                    prefix = routeNumber.split("-")[0];
                    suffix = routeNumber.split("-")[1];
                } else {
                    prefix = routeNumber;
                    suffix = null;
                }

                insertStatement.setString(1, routeNumber);
                insertStatement.setString(2, prefix);
                insertStatement.setString(3, suffix);
                insertStatement.addBatch();
            }
            insertStatement.executeBatch();

        } catch (SQLException ex) {
            Logger.getLogger(TechDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("New routes from excel file has been inserted successfully into database");
    }

    public void updateRoute(RouteData route) {
        try {
            connection = dataBaseConnection.getConnection();
            PreparedStatement updateStatement = connection.prepareStatement("UPDATE route SET a_point = ?, b_point=?, scheme =? where number = ?");
            updateStatement.setString(1, route.getaPoint());
            updateStatement.setString(2, route.getbPoint());
            updateStatement.setString(3, route.getScheme());
            updateStatement.setString(4, route.getNumber());
            updateStatement.execute();
            System.out.println("Route #" + route.getNumber() + "hase been  updated");
        } catch (SQLException ex) {
            Logger.getLogger(RouteDao.class.getName()).log(Level.SEVERE, null, ex);

        }

    }

}
