package fr.eni.gestion_parking.dal;

import fr.eni.gestion_parking.utils.MonLogger;

import java.util.logging.Level;
import java.util.logging.Logger;

public class DALException extends Exception {

    public static final Logger logger = MonLogger.getLogger(DALException.class.getSimpleName());

    //Constructeurs
    public DALException() {
        super();
    }

    public DALException(String message) {
        super(message);
    }

    public DALException(String message, Throwable exception) {
        super(message, exception);
    }

    //Méthodes
    @Override
    public String getMessage() {
        StringBuffer sb = new StringBuffer("Couche DAL - ");
        sb.append(super.getMessage());
        logger.log(Level.SEVERE, sb.toString());
        return sb.toString();
    }


}
