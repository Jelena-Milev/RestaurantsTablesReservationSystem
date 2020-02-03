/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import domain.object.DomainObject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author jeca
 */
public class Reservation extends DomainObject {

    private DiningTable diningTable;
    private User user;
    private Date date;
    private LocalTime timeFrom;
    private LocalTime timeTo;
    private boolean canceled;

    public Reservation() {
    }

    public Reservation(DiningTable diningTable, User user, Date date, LocalTime timeFrom, LocalTime timeTo, boolean canceled) {
        this.diningTable = diningTable;
        this.user = user;
        this.date = date;
        this.timeFrom = timeFrom;
        this.timeTo = timeTo;
        this.canceled = canceled;
    }

    public Reservation(Restaurant restaurant, Date date, LocalTime timeFrom, LocalTime timeTo) {
        this.diningTable = new DiningTable();
        this.diningTable.setRestaurant(restaurant);
        this.date = date;
        this.timeFrom = timeFrom;
        this.timeTo = timeTo;
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
        return "tableLabel, userId, date, timeFrom, timeTo, canceled, restaurantId";
    }

    @Override
    public String getInsertColumnNames() {
        return "tableLabel, userId, date, timeFrom, timeTo, canceled, restaurantId";
    }

    @Override
    public String getSelectWhereClause() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return String.format("restaurantId = %d AND tableLabel = \"%s\", date = \"%s\"", diningTable.getRestaurant().getId(), diningTable.getLabel(), format.format(date));
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
                String tableLabel = rs.getString("tableLabel");
                Long userId = rs.getLong("userId");
                Date date = new Date(rs.getDate("date").getTime());
                LocalTime timeFrom = rs.getTime("timeFrom").toLocalTime();
                LocalTime timeTo = rs.getTime("timeTo").toLocalTime();
                boolean canceled = rs.getBoolean("canceled");

                DiningTable diningTable = new DiningTable(tableLabel, restaurantId);
                User user = new User(userId);
                Reservation r = new Reservation(diningTable, user, date, timeFrom, timeTo, canceled);

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

    @Override
    public String getSelectAllWhereClause() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return String.format("restaurantId = %d AND date = \"%s\" AND canceled = %b", diningTable.getRestaurant().getId(), format.format(date), false);
    }

}
