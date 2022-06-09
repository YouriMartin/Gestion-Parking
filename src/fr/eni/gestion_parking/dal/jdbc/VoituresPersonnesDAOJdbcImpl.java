package fr.eni.gestion_parking.dal.jdbc;

import fr.eni.gestion_parking.bo.VoiturePersonne;
import fr.eni.gestion_parking.dal.DALException;
import fr.eni.gestion_parking.dal.VoituresPersonnesDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VoituresPersonnesDAOJdbcImpl implements VoituresPersonnesDAO {

    private final String REQUETE_FULL_JOIN =
            "SELECT * FROM Personnes FULL JOIN Voitures ON Personnes.id = Voitures.fkPersonne";


    private VoiturePersonne itemBuilder(ResultSet rs) throws SQLException {
        VoiturePersonne resultat = new VoiturePersonne();
        resultat.setPersonneId(rs.getInt("id"));
        resultat.setNomPersonne(rs.getString("nom"));
        resultat.setPrenom(rs.getString("prenom"));
        resultat.setVoitureId(rs.getInt("id"));
        resultat.setNomVoiture(rs.getString("nom"));
        resultat.setPI(rs.getString("plaqueImmatriculation"));
        return resultat;
    }

    public List<VoiturePersonne> selectAll() throws DALException {
        List<VoiturePersonne> voiturePersonnes = new ArrayList<>();
        try (
                var cnx = JdbcTools.getConnection();
                var requete = cnx.prepareStatement(REQUETE_FULL_JOIN);
        ) {
            var rs = requete.executeQuery();

            while (rs.next()) {
                voiturePersonnes.add(itemBuilder(rs));
            }

        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
        return voiturePersonnes;
    }
}
