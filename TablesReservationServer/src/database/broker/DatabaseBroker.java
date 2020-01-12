/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.broker;

import database.connection.ConnectionFactory;
import domain.AbstractModel;
import domain.User;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jeca
 */
public class DatabaseBroker {
    private static DatabaseBroker instance;
    private Connection connection;

    private DatabaseBroker() {
        try {
            connection = ConnectionFactory.getInstance().getConnection();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public static DatabaseBroker getInstance(){
        if (instance == null) {
            instance = new DatabaseBroker();
        }
        return instance;
    }
    
    public void commit() throws SQLException{
        this.connection.commit();
    }
    
    public void rollback() throws SQLException{
        this.connection.rollback();
    }

    public List<AbstractModel> get(AbstractModel object) throws SQLException{
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT "+object.getAllColumnNames()+" FROM "
                    +object.getTableName()+" WHERE "+object.getDefaultWhereClause();
            ResultSet rs = statement.executeQuery(query);
            
            return object.getObjectsFromResultSet(rs);
        } catch (SQLException ex) {
            //todo throw exception to service
            throw ex;
        }
    }    
    
    public List<AbstractModel> getAll(AbstractModel object) throws SQLException{
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM "+object.getTableName();
            ResultSet rs = statement.executeQuery(query);
            return object.getObjectsFromResultSet(rs);
        } catch (SQLException ex) {
            throw ex;
        }
    }

    public Long insert(AbstractModel object) throws SQLException {
        try {
            boolean t = object instanceof User;
            Statement statement = connection.createStatement();
            String query = "INSERT INTO " +object.getTableName()+ "( "+object.getInsertColumnNames()+" )"
                    +" VALUES ("+object.getColumnValues()+")";
            statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = statement.getGeneratedKeys();
            if(rs.next()){
                return rs.getLong(1);
            }
        } catch (SQLException ex) {
            throw ex;
        }
        return -1l;
    }
}
