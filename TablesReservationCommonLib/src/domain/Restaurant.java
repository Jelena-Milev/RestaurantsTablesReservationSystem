/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import domain.object.DomainObject;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import util.DomainObjectStatus;

/**
 *
 * @author jeca
 */
public class Restaurant extends DomainObject implements Serializable {

    private Long id;
    private Date dateAdded;
    private String taxIdNumber;
    private String name;
    private String adress;
    private boolean petsAllowed;
    private boolean nonSmoking;
    private String cuisine;
    private Admin admin;
    private List<DiningTable> tables;
    private DomainObjectStatus status;

    public Restaurant() {
        this.tables = new LinkedList();
        this.admin = new Admin();
    }

    public Restaurant(Long id, Date dateAdded, String taxIdNumber, String name, String adress, boolean petsAllowed, boolean nonSmoking, String cuisine, Admin admin, List<DiningTable> tables) {
        this.id = id;
        this.dateAdded = dateAdded;
        this.taxIdNumber = taxIdNumber;
        this.name = name;
        this.adress = adress;
        this.petsAllowed = petsAllowed;
        this.nonSmoking = nonSmoking;
        this.cuisine = cuisine;
        this.admin = admin;
        this.tables = tables;
    }

    public Restaurant(Long id, Date dateAdded, String taxIdNumber, String name, String adress, boolean petsAllowed, boolean nonSmoking, String cuisine, DomainObjectStatus status) {
        this.id = id;
        this.dateAdded = dateAdded;
        this.taxIdNumber = taxIdNumber;
        this.name = name;
        this.adress = adress;
        this.petsAllowed = petsAllowed;
        this.nonSmoking = nonSmoking;
        this.cuisine = cuisine;
        this.admin = new Admin();
        this.status = status;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) throws Exception {
        if (dateAdded.before(new Date())) {
            throw new Exception("Datum dodavanja restorana ne moze biti u proslosti.");
        }
        this.dateAdded = dateAdded;
    }

    public String getTaxIdNumber() {
        return taxIdNumber;
    }

    public void setTaxIdNumber(String taxIdNumber) throws Exception {
        if (taxIdNumber.matches("[0-9]{9}") == false) {
            throw new Exception("PIB mora imati 9 cifara.");
        }
        this.taxIdNumber = taxIdNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public boolean isPetsAllowed() {
        return petsAllowed;
    }

    public void setPetsAllowed(boolean petsAllowed) {
        this.petsAllowed = petsAllowed;
    }

    public boolean isNonSmoking() {
        return nonSmoking;
    }

    public void setNonSmoking(boolean nonSmoking) {
        this.nonSmoking = nonSmoking;
    }

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    public List<DiningTable> getTables() {
        return tables;
    }

    public void setTables(List<DiningTable> tables) {
        this.tables = tables;
    }

    public DomainObjectStatus getStatus() {
        return status;
    }

    public void setStatus(DomainObjectStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Restaurant{" + "taxIdNumber=" + taxIdNumber + ", name=" + name + ", adress=" + adress + ", petsAllowed=" + petsAllowed + ", nonSmoking=" + nonSmoking + ", cuisine=" + cuisine + '}';
    }

    @Override
    public String getAllColumnNames() {
        return "id, dateAdded, taxIdNumber, name, adress, petsAllowed, nonSmoking, cuisine, adminId, status";
    }

    @Override
    public String getInsertColumnNames() {
        return "dateAdded, taxIdNumber, name, adress, petsAllowed, nonSmoking, cuisine, adminId, status";
    }

    @Override
    public String getSelectWhereClause() {
//        return String.format("id = %d, status = \"%s\"", this.getId(), DomainObjectStatus.ACTIVE.toString());
        return String.format("id = %d", this.getId());
    }

    @Override
    public String getTableName() {
        return "Restaurant";
    }

    @Override
    public List<DomainObject> getObjectsFromResultSet(ResultSet rs) {
        List<DomainObject> restaurants = new ArrayList();
        try {
            while (rs.next()) {
                Long id = rs.getLong("id");
                Date date = new Date(rs.getDate("dateAdded").getTime());
                String taxIdNumber = rs.getString("taxIdNumber");
                String name = rs.getString("name");
                String adress = rs.getString("adress");
                boolean petsAllowed = rs.getBoolean("petsAllowed");
                boolean nonSmoking = rs.getBoolean("nonSmoking");
                String cuisine = rs.getString("cuisine");
                Long adminId = rs.getLong("adminId");
                DomainObjectStatus status = DomainObjectStatus.valueOf(rs.getString("status"));
                Restaurant restaurant = new Restaurant(id, date, taxIdNumber, name, adress, petsAllowed, nonSmoking, cuisine, status);
                restaurant.getAdmin().setId(adminId);
                restaurants.add(restaurant);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return restaurants;
    }

    @Override
    public String getColumnValues() {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return String.format("\"%s\", \"%s\", \"%s\", \"%s\", %b, %b, \"%s\", %d, \"%s\"",
                format.format(this.dateAdded), this.taxIdNumber, this.name, this.adress, this.petsAllowed, this.nonSmoking, this.cuisine, this.admin.getId(), this.status.toString());
    }

    @Override
    public String getUpdateClause() {
        return String.format("name = \"%s\", adress = \"%s\", petsAllowed = %b, nonSmoking = %b, cuisine = \"%s\", status = \"%s\"", name, adress, petsAllowed, nonSmoking, cuisine, status);
    }

    @Override
    public String getUpdateWhereClause() {
        return String.format("id = %d", this.getId());
    }

    @Override
    public String getDeleteClause() {
        return "status = 'DELETED'";
    }

    @Override
    public String getDeleteWhereClause() {
        return String.format("id = %d", this.getId());
    }

    @Override
    public String getSelectAllWhereClause() {
        //this.status ili active?
        return String.format("status = \"%s\"", this.status);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Restaurant other = (Restaurant) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }    
}
