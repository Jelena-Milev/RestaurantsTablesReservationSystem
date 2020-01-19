/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import database.broker.DatabaseBroker;
import domain.DomainObject;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.ValidationException;
import validator.Validator;

/**
 *
 * @author jeca
 */
public abstract class SystemOperation {

    Validator validator;
    DatabaseBroker dbBroker;
    DomainObject odo;

    public SystemOperation() {
        dbBroker = DatabaseBroker.getInstance();
    }

    protected void checkPreconditions() throws ValidationException {
        if (validator != null) {
            validator.validate(odo);
        }
    }

    protected void connectStorage() {
        //TODO
    }

    protected void disconnectStorage() {
        //TODO
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
            throw ex;
        } finally {
            disconnectStorage();
        }
    }

}
