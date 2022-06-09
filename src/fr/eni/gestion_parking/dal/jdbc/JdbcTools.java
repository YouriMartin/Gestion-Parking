package fr.eni.gestion_parking.dal.jdbc;



import fr.eni.gestion_parking.dal.Settings;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class JdbcTools {
    private static String urldb;
    private static String userdb;
    private static String passworddb;

    public static Connection getConnection() throws SQLException {
        urldb = Settings.getProperty("urldb");
        userdb = Settings.getProperty("userdb");
        passworddb = Settings.getProperty("passworddb");
        return DriverManager.getConnection("jdbc:sqlserver://" + urldb + ";databaseName=GESTION_PARKING;trustServerCertificate=true;username=" + userdb + ";password=" + passworddb);
    }

    PersonnesDAOJdbcImpl personnesDAO = new PersonnesDAOJdbcImpl();
    VoituresDAOJdbcImpl voituresDAO = new VoituresDAOJdbcImpl();
}
