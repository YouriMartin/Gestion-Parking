package fr.eni.gestion_parking.bll;

import fr.eni.gestion_parking.bo.Personne;
import fr.eni.gestion_parking.dal.DALException;
import fr.eni.gestion_parking.dal.DaoFactory;
import fr.eni.gestion_parking.dal.PersonnesDAO;
import fr.eni.gestion_parking.dal.VoituresDAO;

import java.util.List;

public class PersonnesManager {
    private PersonnesDAO daoPersonne = DaoFactory.getPersonnesDAO();
    private VoituresDAO daoVoiture = DaoFactory.getVoituresDAO();
    private volatile static PersonnesManager instance = new PersonnesManager();

    private PersonnesManager() {
    }

    public synchronized static PersonnesManager getInstance() {
        if (instance == null) {
            instance = new PersonnesManager();
        }
        return instance;
    }

    public void valider(Personne a) throws BLLException {
        if (a.getNom().isBlank() || a.getNom() == null) {
            throw new BLLException("La nom est null ou vide");
        }
        if (a.getPrenom().isBlank() || a.getPrenom() == null) {
            throw new BLLException("La prenom est null ou vide");
        }
    }


    public List<Personne> getALL() throws BLLException {
        try {
            return daoPersonne.selectAll();

        } catch (DALException e) {
            throw new BLLException(e.getMessage());
        }
    }

    public void add(Personne v) throws BLLException {
        try {
            valider(v);
            daoPersonne.insert(v);

        } catch (DALException e) {
            throw new BLLException(e.getMessage());
        }
    }

    public void update(Personne v) throws BLLException {
        try {
            valider(v);
            daoPersonne.update(v);

        } catch (DALException e) {
            throw new BLLException(e.getMessage());
        }
    }

    public void remove(int index) throws BLLException {
        try {
            Personne personne = daoPersonne.selectById(index);
            if (daoVoiture.selectAll().stream()
                    .anyMatch(voiture -> voiture.getPersonnes().equals(personne))
            ) {
                throw new BLLException("cette personne est lié à la table voiture");
            }
            daoPersonne.delete(index);
        } catch (DALException e) {
            throw new BLLException(e.getMessage());
        }
    }

    public Personne get(int index) throws BLLException {
        try {
            return daoPersonne.selectAll().get(index);
        } catch (DALException e) {
            throw new BLLException(e.getMessage());
        }

    }
}
