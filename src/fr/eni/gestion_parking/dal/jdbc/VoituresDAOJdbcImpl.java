package fr.eni.gestion_parking.dal.jdbc;


import fr.eni.gestion_parking.bo.Voiture;
import fr.eni.gestion_parking.dal.DALException;
import fr.eni.gestion_parking.dal.DaoFactory;
import fr.eni.gestion_parking.dal.VoituresDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class VoituresDAOJdbcImpl implements VoituresDAO {

    private final String REQUETE_SELECT_BY_ID =
            "SELECT * FROM dbo.Voitures WHERE id = ?";
    private final String REQUETE_SELECT_ALL =
            "SELECT * FROM Voitures";
    private final String REQUETE_UPDATE =
            "UPDATE [dbo].[Voitures] SET [nom] = ? ,[plaqueImmatriculation] = ?, [fkPersonne] = ? WHERE id = ?";
    private final String REQUETE_INSERT =
            "INSERT INTO Voitures VALUES(?,?,?)";
    private final String REQUETE_DELETE =
            "DELETE Voitures WHERE id = ?";


    private Voiture itemBuilder(ResultSet rs) throws SQLException, DALException {
        Voiture resultat = new Voiture();
        resultat.setId(rs.getInt("id"));
        resultat.setNom(rs.getString("nom"));
        resultat.setPlaqueImmatriculation(rs.getString("plaqueImmatriculation"));
        resultat.setPersonnes(DaoFactory.getPersonnesDAO().selectById(rs.getInt("fkPersonne")));
        return resultat;
    }

    /**
     * @param id
     * @return
     * @throws DALException
     */
    public Voiture selectById(int id) throws DALException {
        Voiture voiture = null;
        try (
                var cnx = JdbcTools.getConnection();
                var requete = cnx.prepareStatement(REQUETE_SELECT_BY_ID);
        ) {
            requete.setInt(1, id);
            var response = requete.executeQuery();

            if (response.next()) {
                voiture = itemBuilder(response);
            }

        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
        return voiture;
    }

    /**
     * @return
     * @throws DALException
     */
    public List<Voiture> selectAll() throws DALException {
        List<Voiture> voitures = new ArrayList<>();
        try (var cnx = JdbcTools.getConnection();
             var requete = cnx.prepareStatement(REQUETE_SELECT_ALL);) {
            var rs = requete.executeQuery();

            while (rs.next()) {
                voitures.add(itemBuilder(rs));
            }
        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
        return voitures;
    }

    /**
     * @param voiture
     * @return
     * @throws DALException
     */
    public boolean update(Voiture voiture) throws DALException {
        var result = false;
        try (
                var cnx = JdbcTools.getConnection();
                var requete = cnx.prepareStatement(REQUETE_UPDATE);
        ) {
            requete.setString(1, voiture.getNom());
            requete.setString(2, voiture.getPlaqueImmatriculation());
            if (voiture.getPersonnes() == null) {
                requete.setNull(3, Types.INTEGER);
            } else {
                requete.setInt(3, voiture.getPersonnes().getId());
            }
            requete.setInt(4, voiture.getId());
            requete.executeUpdate();
            result = true;

        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
        return result;
    }

    /**
     * @param voiture
     * @return
     * @throws DALException
     */
    public boolean insert(Voiture voiture) throws DALException {
        var result = false;
        try (
                var cnx = JdbcTools.getConnection();
                var requete = cnx.prepareStatement(REQUETE_INSERT, Statement.RETURN_GENERATED_KEYS);
        ) {
            requete.setString(1, voiture.getNom());
            requete.setString(2, voiture.getPlaqueImmatriculation());
            if (voiture.getPersonnes() == null) {
                requete.setNull(3, Types.INTEGER);
            } else {
                requete.setInt(3, voiture.getPersonnes().getId());
            }
            requete.executeUpdate();

            var rs = requete.getGeneratedKeys();

            if (rs.next()) {
                voiture.setId(rs.getInt(1));
            }
            result = true;

        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }

        return result;
    }

    /**
     * @param id
     * @return
     * @throws DALException
     */
    public boolean delete(int id) throws DALException {
        var result = false;
        try (var cnx = JdbcTools.getConnection();
             var requete = cnx.prepareStatement(REQUETE_DELETE);) {
            requete.setInt(1, id);
            requete.executeUpdate();
            result = true;
        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
        return result;
    }
}
