/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.sql.ResultSet;
import java.util.List;

/**
 *
 * @author jeca
 */
public abstract class AbstractModel {
    
    public abstract String getAllColumnNames();
    public abstract String getInsertColumnNames();
    public abstract String getDefaultWhereClause();
    public abstract String getTableName();
    public abstract List<AbstractModel> getObjectsFromResultSet(ResultSet rs);
//    public abstract AbstractModel getObjectFromResultSet(ResultSet rs);
    public abstract String getColumnValues();
}
