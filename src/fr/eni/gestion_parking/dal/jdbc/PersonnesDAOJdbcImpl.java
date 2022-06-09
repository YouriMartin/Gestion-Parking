package fr.eni.gestion_parking.dal.jdbc;

import fr.eni.gestion_parking.bo.Personne;
import fr.eni.gestion_parking.dal.DALException;
import fr.eni.gestion_parking.dal.PersonnesDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PersonnesDAOJdbcImpl implements PersonnesDAO {

    private final String REQUETE_SELECT_BY_ID =
            "SELECT * FROM dbo.Personnes WHERE id = ?";
    private final String REQUETE_SELECT_ALL =
            "SELECT * FROM Personnes";
    private final String REQUETE_UPDATE =
            "UPDATE [dbo].[Personnes] SET [nom] = ? ,[prenom] = ? WHERE id = ?";
    private final String REQUETE_INSERT =
            "INSERT INTO Personnes VALUES(?,?)";
    private final String REQUETE_DELETE =
            "DELETE Personnes WHERE id = ?";

    private Personne itemBuilder(ResultSet rs) throws SQLException {
        Personne resultat = new Personne();
        resultat.setId(rs.getInt("id"));
        resultat.setNom(rs.getString("nom"));
        resultat.setPrenom(rs.getString("prenom"));
        return resultat;
    }


    public Personne selectById(int id) throws DALException {
        Personne personne = null;
        try (
                var cnx = JdbcTools.getConnection();
                var requete = cnx.prepareStatement(REQUETE_SELECT_BY_ID);
        ) {
            requete.setInt(1, id);
            var response = requete.executeQuery();

            if (response.next()) {
                personne = itemBuilder(response);
            }

        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
        return personne;
    }


    public List<Personne> selectAll() throws DALException {
        List<Personne> personnes = new ArrayList<>();
        try (var cnx = JdbcTools.getConnection();
             var requete = cnx.prepareStatement(REQUETE_SELECT_ALL);) {
            var rs = requete.executeQuery();

            while (rs.next()) {
                personnes.add(itemBuilder(rs));
            }
        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
        return personnes;
    }


    public boolean update(Personne personne) throws DALException {
        var result = false;
        try (
                var cnx = JdbcTools.getConnection();
                var requete = cnx.prepareStatement(REQUETE_UPDATE);
        ) {
            requete.setString(1, personne.getNom());
            requete.setString(2, personne.getPrenom());
            requete.setInt(3, personne.getId());
            result = requete.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
        return result;
    }


    public boolean insert(Personne personne) throws DALException {
        var result = false;
        try (
                var cnx = JdbcTools.getConnection();
                var requete = cnx.prepareStatement(REQUETE_INSERT, Statement.RETURN_GENERATED_KEYS);
        ) {
            requete.setString(1, personne.getNom());
            requete.setString(2, personne.getPrenom());
            requete.executeUpdate();

            var rs = requete.getGeneratedKeys();

            if (rs.next()) {
                personne.setId(rs.getInt(1));
            }
            result = true;

        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }

        return result;
    }


    public boolean delete(int id) throws DALException {
        var result = false;
        try (var cnx = JdbcTools.getConnection();
             var requete = cnx.prepareStatement(REQUETE_DELETE);) {
            requete.setInt(1, id);
            result = requete.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
        return result;
    }
}
