package fr.eni.gestion_parking.bll;

import fr.eni.gestion_parking.bo.Voiture;
import fr.eni.gestion_parking.dal.DALException;
import fr.eni.gestion_parking.dal.DaoFactory;
import fr.eni.gestion_parking.dal.VoituresDAO;

import java.util.List;
import java.util.regex.Pattern;

public class VoituresManager {
    private VoituresDAO daoVoiture = DaoFactory.getVoituresDAO();
    private volatile static VoituresManager instance = new VoituresManager();

    private VoituresManager() {
    }

    public synchronized static VoituresManager getInstance() {
        if (instance == null) {
            instance = new VoituresManager();
        }
        return instance;
    }

    public void valider(Voiture a) throws BLLException {
        if (a.getNom().isBlank() || a.getNom() == null) {
            throw new BLLException("La nom est null ou vide");
        }
        if (a.getPlaqueImmatriculation().isBlank() || a.getPlaqueImmatriculation() == null) {
            throw new BLLException("La plaque immatriculation est null ou vide");
        }
        if (!Pattern.matches("^[A-Z]{2}[-][0-9]{3}[-][A-Z]{2}$", a.getPlaqueImmatriculation())) {
            throw new BLLException("La plaque d'immatriculation est incorect");
        }
    }


    public List<Voiture> getALL() throws BLLException {
        try {
            return daoVoiture.selectAll();

        } catch (DALException e) {
            throw new BLLException(e.getMessage());
        }
    }

    public void add(Voiture v) throws BLLException {
        try {
            valider(v);
            daoVoiture.insert(v);

        } catch (DALException e) {
            throw new BLLException(e.getMessage());
        }
    }

    public void update(Voiture v) throws BLLException {
        try {
            valider(v);
            daoVoiture.update(v);

        } catch (DALException e) {
            throw new BLLException(e.getMessage());
        }
    }

    public void remove(int index) throws BLLException {
        try {
            daoVoiture.selectById(index);
            daoVoiture.delete(index);
        } catch (DALException e) {
            throw new BLLException(e.getMessage());
        }
    }

    public Voiture get(int index) throws BLLException {
        try {
            return daoVoiture.selectAll().get(index);
        } catch (DALException e) {
            throw new BLLException(e.getMessage());
        }

    }
}
