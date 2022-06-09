package fr.eni.gestion_parking.bll;

import fr.eni.gestion_parking.bo.VoiturePersonne;
import fr.eni.gestion_parking.dal.DALException;
import fr.eni.gestion_parking.dal.DaoFactory;
import fr.eni.gestion_parking.dal.VoituresPersonnesDAO;

import java.util.List;

public class VoituresPersonnesManager {

    private VoituresPersonnesDAO daoVoiturePersonnes = DaoFactory.getVoituresPersonnesDao();

    private volatile static VoituresPersonnesManager instance = new VoituresPersonnesManager();

    private VoituresPersonnesManager() {
    }

    public synchronized static VoituresPersonnesManager getInstance() {
        if (instance == null) {
            instance = new VoituresPersonnesManager();
        }
        return instance;
    }

    public List<VoiturePersonne> getAll() throws BLLException {
        try {
            return daoVoiturePersonnes.selectAll();
        } catch (DALException e) {
            throw new BLLException(e.getMessage());
        }
    }

}
