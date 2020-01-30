/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.broker;

import domain.Actor;
import domain.DiningTable;
import domain.DomainObject;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jeca
 */
public class DatabaseBroker {
//    private static DatabaseBroker instance;

    private Connection connection;
    private String url;
    private String username;
    private String password;
    private String driver;

    public DatabaseBroker() {
        this.setDatabaseAccessParams();
    }

    public void connect() throws Exception {
        try {
            Class.forName(this.driver);
            connection = DriverManager.getConnection(url, username, password);
            connection.setAutoCommit(false);
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new Exception("Greska prilikom uspostavljanja konekcije sa bazom");
        }
    }

    public void disconnect() throws Exception {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
                throw new Exception("Greska prilikom raskidanja konekcije sa bazom!");
            }
        }
    }

    public void commit() throws Exception {
        if (connection != null) {
            try {
                this.connection.commit();
            } catch (SQLException ex) {
                ex.printStackTrace();
                throw new Exception("Greska prilikom potvrdjivanja transakcije!");
            }
        }
    }

    public void rollback() throws Exception {
        if (connection != null) {
            try {
                this.connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
                throw new Exception("Greska prilikom ponistavanja transakcije!");
            }
        }
    }

    public List<DomainObject> get(DomainObject object) throws SQLException {
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT " + object.getAllColumnNames() + " FROM "
                    + object.getTableName() + " WHERE " + object.getSelectWhereClause();
            ResultSet rs = statement.executeQuery(query);

            return object.getObjectsFromResultSet(rs);
        } catch (SQLException ex) {
            //todo throw exception to service
            throw ex;
        }
    }

    public List<DomainObject> getAll(DomainObject object) throws SQLException {
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM " + object.getTableName();
            ResultSet rs = statement.executeQuery(query);
            return object.getObjectsFromResultSet(rs);
        } catch (SQLException ex) {
            throw ex;
        }
    }

    public Long insert(DomainObject object) throws SQLException {
        try {
            Statement statement = connection.createStatement();
            String query = "INSERT INTO " + object.getTableName() + "(" + object.getInsertColumnNames() + ")" + " VALUES (" + object.getColumnValues() + ")";
            statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                return rs.getLong(1);
            }
        } catch (SQLException ex) {
            throw ex;
        }
        return -1l;
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
            this.driver = properties.getProperty("driverName");
            this.username = properties.getProperty("username");
            this.password = properties.getProperty("password");

            this.url = String.format("%s:%s://%s:%s/%s", protocol, driver, path, port, name);
            fileInputStream.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public int update(DomainObject odo) throws SQLException {
        try {
            Statement statement = connection.createStatement();
            String query = "UPDATE " + odo.getTableName() + " SET " + odo.getUpdateClause() + " WHERE " + odo.getUpdateWhereClause();
            int rowsUpdated = statement.executeUpdate(query);
            return rowsUpdated;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    public int delete(DomainObject odo) throws SQLException {
        try {
            Statement statement = connection.createStatement();
            String query = "UPDATE " + odo.getTableName() + " SET " + odo.getDeleteClause() + " WHERE " + odo.getDeleteWhereClause();
            int rowsUpdated = statement.executeUpdate(query);
            return rowsUpdated;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }
}
