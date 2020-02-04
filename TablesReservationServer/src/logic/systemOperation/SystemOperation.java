/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.systemOperation;

import database.broker.DatabaseBroker;
import domain.object.DomainObject;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.ValidationException;
import validator.Validator;

/**
 *
 * @author jeca
 */
public abstract class SystemOperation {

    protected Validator validator;
    protected DatabaseBroker dbBroker;
    protected DomainObject odo;

    public SystemOperation() {
        dbBroker = new DatabaseBroker();
    }

    protected void checkPreconditions() throws ValidationException {
        if (validator != null) {
            validator.validate(odo);
        }
    }

    protected void connectStorage() throws Exception {
        dbBroker.connect();
    }

    protected void disconnectStorage() throws Exception {
        dbBroker.disconnect();
    }

    protected abstract void operation() throws Exception;

    public void execute() throws Exception {
        checkPreconditions();
        connectStorage();
        try {
            operation();
            dbBroker.commit();
        } catch (Exception ex) {
            dbBroker.rollback();
            System.out.println("Namerno ispisan exception");
            ex.printStackTrace();
            throw ex;
        } finally {
            disconnectStorage();
        }
    }

    public DomainObject getDomainObject(){
        return this.odo;
    }
}
