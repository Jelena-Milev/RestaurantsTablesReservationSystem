/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import util.DomainObjectStatus;

/**
 *
 * @author jeca
 */
public class Reservation extends DomainObject{
    
    private Long id;
    private DiningTable diningTable;
    private User user;
    private Date date;
    private LocalTime timeFrom;
    private LocalTime timeTo;
    private boolean canceled;

    public Reservation() {
    }

    public Reservation(Long id, DiningTable diningTable, User user, Date date, LocalTime timeFrom, LocalTime timeTo, boolean canceled) {
        this.id = id;
        this.diningTable = diningTable;
        this.user = user;
        this.date = date;
        this.timeFrom = timeFrom;
        this.timeTo = timeTo;
        this.canceled = canceled;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DiningTable getDiningTable() {
        return diningTable;
    }

    public void setDiningTable(DiningTable diningTable) {
        this.diningTable = diningTable;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public LocalTime getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom(LocalTime timeFrom) {
        this.timeFrom = timeFrom;
    }

    public LocalTime getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(LocalTime timeTo) {
        this.timeTo = timeTo;
    }
    
    public boolean isCanceled() {
        return canceled;
    }

    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }

    @Override
    public String getAllColumnNames() {
        return "tableNo, userId, date, timeFrom, timeTo, canceled, restaurantId";
    }

    @Override
    public String getInsertColumnNames() {
        return "tableNo, userId, date, timeFrom, timeTo, canceled, restaurantId";
    }

    @Override
    public String getSelectWhereClause() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return String.format("restaurantId = %d, tableNo = %d, date = \"%s\"", diningTable.getRestaurant().getId(), diningTable.getId(), format.format(date));
    }

    @Override
    public String getTableName() {
        return "Reservation";
    }

    @Override
    public List<DomainObject> getObjectsFromResultSet(ResultSet rs) {
        List<DomainObject> reservations = new ArrayList();
        try {
            while (rs.next()) {
                Long restaurantId = rs.getLong("restaurantId");
                Long tableNo = rs.getLong("tableNo");
                Long userId = rs.getLong("userId");
                Date date = rs.getObject("timeFrom", Date.class);
                LocalTime timeFrom = rs.getObject("timeFrom", LocalTime.class);
                LocalTime timeTo = rs.getObject("timeTo", LocalTime.class);
                boolean canceled = rs.getBoolean("canceled");
                
                DiningTable dt = new DiningTable(tableNo, restaurantId);
                User u = new User(userId);
                Reservation r = new Reservation(null, dt, u, date, timeFrom, timeTo, canceled);

                reservations.add(r);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return reservations;
    }

    @Override
    public String getColumnValues() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getUpdateClause() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getUpdateWhereClause() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getDeleteClause() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getDeleteWhereClause() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}
