/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

/**
 *
 * @author jeca
 */
public abstract class DomainObject implements Serializable {

    public abstract String getAllColumnNames();

    public abstract String getInsertColumnNames();

    public abstract String getSelectWhereClause();

    public abstract String getTableName();

    public abstract List<DomainObject> getObjectsFromResultSet(ResultSet rs);

    public abstract String getColumnValues();

    public abstract String getUpdateClause();

    public abstract String getUpdateWhereClause();

    public abstract String getDeleteClause();

    public abstract String getDeleteWhereClause();
}
