package fr.eni.gestion_parking.dal;

import fr.eni.gestion_parking.dal.jdbc.PersonnesDAOJdbcImpl;
import fr.eni.gestion_parking.dal.jdbc.VoituresDAOJdbcImpl;
import fr.eni.gestion_parking.dal.jdbc.VoituresPersonnesDAOJdbcImpl;

public class DaoFactory {

    public static VoituresDAO getVoituresDAO() {
        return new VoituresDAOJdbcImpl();
    }

    public static PersonnesDAO getPersonnesDAO() {
        return new PersonnesDAOJdbcImpl();
    }

    public static VoituresPersonnesDAO getVoituresPersonnesDao() {
        return new VoituresPersonnesDAOJdbcImpl();
    }

}
