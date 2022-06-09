package fr.eni.gestion_parking.bll;

import fr.eni.gestion_parking.dal.DALException;
import fr.eni.gestion_parking.utils.MonLogger;

import java.util.logging.Level;
import java.util.logging.Logger;

public class BLLException extends Exception {

    public static final Logger logger = MonLogger.getLogger(DALException.class.getSimpleName());

    public BLLException() {
        super();
    }

    public BLLException(String message) {
        super(message);
    }


    public BLLException(String message, Throwable exception) {
        super(message, exception);
    }


    //Méthodes
    @Override
    public String getMessage() {
        StringBuffer sb = new StringBuffer("Couche BLL - ");
        sb.append(super.getMessage());
        logger.log(Level.SEVERE, sb.toString());
        return sb.toString();
    }
}
