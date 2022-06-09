package fr.eni.gestion_parking.dal;

import fr.eni.gestion_parking.bo.Voiture;

import java.util.List;

public interface VoituresDAO {
    Voiture selectById(int id) throws DALException;

    List<Voiture> selectAll() throws DALException;

    boolean update(Voiture voiture) throws DALException;

    boolean insert(Voiture voiture) throws DALException;

    boolean delete(int id) throws DALException;
}
