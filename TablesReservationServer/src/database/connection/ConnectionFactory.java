/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.connection;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jeca
 */
public class ConnectionFactory {

    private Connection connection;
    private static ConnectionFactory instance;
    private String connectionString;
    private String username;
    private String password;

    private ConnectionFactory() throws SQLException {
        this.setDatabaseAccessParams();
        try {
            connection = DriverManager.getConnection(connectionString, username, password);
            connection.setAutoCommit(false);
        } catch (SQLException ex) {
            throw new SQLException("Konekcija nije kreirana");
        }
    }

    public static ConnectionFactory getInstance() throws SQLException {
        if (instance == null) {
            instance = new ConnectionFactory();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    private void setDatabaseAccessParams() {
        try {
            Properties properties = new Properties();
            String propertiesFileName = "config/config.properties";
            FileInputStream fileInputStream = new FileInputStream(propertiesFileName);

            properties.load(fileInputStream);

            String protocol = properties.getProperty("protocol");
            String driver = properties.getProperty("driver");
            String path = properties.getProperty("path");
            String port = properties.getProperty("port");
            String name = properties.getProperty("name");
            this.username = properties.getProperty("username");
            this.password = properties.getProperty("password");

            this.connectionString = String.format("%s:%s://%s:%s/%s", protocol, driver, path, port, name);
            fileInputStream.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
