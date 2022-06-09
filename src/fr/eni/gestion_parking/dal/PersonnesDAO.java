package fr.eni.gestion_parking.dal;

import fr.eni.gestion_parking.bo.Personne;

import java.util.List;

public interface PersonnesDAO {

    Personne selectById(int id) throws DALException;

    List<Personne> selectAll() throws DALException;

    boolean update(Personne personne) throws DALException;

    boolean insert(Personne personne) throws DALException;

    boolean delete(int id) throws DALException;

}
