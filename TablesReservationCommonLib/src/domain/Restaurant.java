/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author jeca
 */
public class Restaurant extends AbstractModel implements Serializable{
    private Long id;
    private Date dateAdded;
    private String taxIdNumber;
    private String name;
    private String adress;
    private boolean petsAllowed;
    private boolean nonSmoking;
    private String cuisine;
    private Long adminId;

    public Restaurant() {
    }

    public Restaurant(Long id, Date dateAdded, String taxIdNumber, String name, String adress, boolean petsAllowed, boolean nonSmoking, String cuisine, Long adminId) {
        this.id = id;
        this.dateAdded = dateAdded;
        this.taxIdNumber = taxIdNumber;
        this.name = name;
        this.adress = adress;
        this.petsAllowed = petsAllowed;
        this.nonSmoking = nonSmoking;
        this.cuisine = cuisine;
        this.adminId = adminId;
    }

    public Long getAdmin() {
        return adminId;
    }

    public void setAdmin(Long adminId) {
        this.adminId = adminId;
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

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getTaxIdNumber() {
        return taxIdNumber;
    }

    public void setTaxIdNumber(String taxIdNumber) {
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

    @Override
    public String toString() {
        return "Restaurant{" + "taxIdNumber=" + taxIdNumber + ", name=" + name + ", adress=" + adress + ", petsAllowed=" + petsAllowed + ", nonSmoking=" + nonSmoking + ", cuisine=" + cuisine + '}';
    }

    @Override
    public String getAllColumnNames() {
        return "id, dateAdded, taxIdNumber, name, adress, petsAllowed, nonSmoking, cuisine, adminId";
    }

    @Override
    public String getInsertColumnNames() {
        return "dateAdded, taxIdNumber, name, adress, petsAllowed, nonSmoking, cuisine, adminId";
    }

    @Override
    public String getDefaultWhereClause() {
        return "id = "+this.getId();
    }

    @Override
    public String getTableName() {
        return "Restaurant";
    }

    @Override
    public List<AbstractModel> getObjectsFromResultSet(ResultSet rs) {
        List<AbstractModel> restaurants = new ArrayList();
        try {
            while (rs.next()) {
                Long id = rs.getLong("id");
                Date date = new Date(rs.getDate("dateAdded").getTime());
                String taxIdNumber = rs.getString("tax_id_number");
                String name = rs.getString("name");
                String adress = rs.getString("adress");
                boolean petsAllowed = rs.getBoolean("petsAllowed");
                boolean nonSmoking = rs.getBoolean("nonSmoking");
                String cuisine = rs.getString("cuisine");
                Long adminId = rs.getLong("adminId");
                Restaurant restaurant = new Restaurant(id, date, taxIdNumber, name, adress, petsAllowed, nonSmoking, cuisine, adminId);
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
        return this.id+", \""+format.format(this.dateAdded)+"\" "+this.taxIdNumber+"\", \""+this.name+"\", \""+this.adress+"\", "+this.petsAllowed+", "+this.nonSmoking+", \""+this.cuisine+"\", "+this.adminId;
    }
    
    
}
