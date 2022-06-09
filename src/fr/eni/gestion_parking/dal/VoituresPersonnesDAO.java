package fr.eni.gestion_parking.dal;


import fr.eni.gestion_parking.bo.VoiturePersonne;

import java.util.List;

public interface VoituresPersonnesDAO {
    List<VoiturePersonne> selectAll() throws DALException;
}
