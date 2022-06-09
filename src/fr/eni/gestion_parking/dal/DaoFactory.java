package fr.eni.gestion_parking.dal;

import fr.eni.gestion_parking.dal.jdbc.PersonnesDAOJdbcImpl;
import fr.eni.gestion_parking.dal.jdbc.VoituresDAOJdbcImpl;

public class DaoFactory {

    public static VoituresDAO getVoituresDAO(){
        return new VoituresDAOJdbcImpl();
    }
    public static PersonnesDAO getPersonnesDAO(){
        return new PersonnesDAOJdbcImpl();
    }

}
