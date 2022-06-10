package fr.eni.gestion_parking.ihm;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import fr.eni.gestion_parking.bll.BLLException;
import fr.eni.gestion_parking.bll.PersonnesManager;
import fr.eni.gestion_parking.bll.VoituresManager;
import fr.eni.gestion_parking.bll.VoituresPersonnesManager;
import fr.eni.gestion_parking.bo.Personne;
import fr.eni.gestion_parking.bo.Voiture;
import fr.eni.gestion_parking.bo.VoiturePersonne;
import fr.eni.gestion_parking.dal.DaoFactory;
import fr.eni.gestion_parking.dal.VoituresPersonnesDAO;
import fr.eni.gestion_parking.utils.MonLogger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.beans.XMLEncoder;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller {

    public TableView voituresFx;
    public TableColumn voitureNameFx;
    public TableColumn piFx;
    public TableColumn<Voiture, String> nameAndFirstNameFx;
    public TableView personnesFx;
    public TableColumn nameFx;
    public TableColumn firstnameFx;
    public TextField nomVoitureFieldFx;
    public TextField piFieldFx;

    public Button addVoitureFx;
    public Button updateVoitureFx;
    public Button deleteVoitureFx;
    public TextField nomPersonneFieldFX;
    public TextField prenomFieldFx;
    public Button addPersonneFx;
    public Button updatePersonneFx;
    public Button deletePersonneFx;
    public ComboBox<Personne> personneSelectFx;
    public Label errorVoitureFx;
    public Label errorPersonneFx;
    public Label errorExportFx;

    private List<Personne> personnes = new ArrayList<>();
    private List<Voiture> voitures = new ArrayList<>();

    private List<VoiturePersonne> voituresPersonnes = new ArrayList<>();
    private static Integer idPersonneSelected;
    private static Integer idVoitureSelected;

    public static final Logger logger = MonLogger.getLogger(Controller.class.getSimpleName());


    public void initialize() throws BLLException {
        setupVoitures();
        setupPersonnes();
    }

    public void clearPersonne() {
        nomPersonneFieldFX.clear();
        prenomFieldFx.clear();
        idPersonneSelected = null;
    }

    public void clearVoiture() {
        nomVoitureFieldFx.clear();
        piFieldFx.clear();
        personneSelectFx.getSelectionModel().clearSelection();
        idVoitureSelected = null;
    }

    public void showError(Label label, Exception e) {
        label.setVisible(true);
        label.setText(e.getMessage());
        label.setStyle("-fx-text-fill:red;");
    }

    public void resetError() {
        errorPersonneFx.setVisible(false);
        errorExportFx.setVisible(false);
        errorVoitureFx.setVisible(false);
    }

    public void setupPersonnes() throws BLLException {
        resetError();
        personnes = PersonnesManager.getInstance().getALL();

        nameFx.setCellValueFactory(new PropertyValueFactory<Personne, String>("nom"));
        firstnameFx.setCellValueFactory(new PropertyValueFactory<Personne, String>("prenom"));
        personnesFx.setItems(FXCollections.observableArrayList(personnes));

        // le select des personnes dans le form voiture

        personneSelectFx.setItems(FXCollections.observableArrayList(personnes));
        personneSelectFx.getItems().add(null);


    }

    public void setupVoitures() throws BLLException {
        resetError();
        voitures = VoituresManager.getInstance().getALL();

        voitureNameFx.setCellValueFactory(new PropertyValueFactory<Voiture, String>("nom"));
        piFx.setCellValueFactory(new PropertyValueFactory<Voiture, String>("plaqueImmatriculation"));
        nameAndFirstNameFx.setCellValueFactory(voiture -> {
            Personne personne = voiture.getValue().getPersonnes();
            return new SimpleStringProperty(personne == null ? "" :
                    personne.getNom().toUpperCase().concat(" ").concat(personne.getPrenom()));
        });
        voituresFx.setItems(FXCollections.observableArrayList(voitures));


    }

    public void selectPersonne() {
        Personne personne = (Personne) personnesFx.getSelectionModel().getSelectedItem();
        if (personne != null) {
            idPersonneSelected = personne.getId();
            nomPersonneFieldFX.setText(personne.getNom());
            prenomFieldFx.setText(personne.getPrenom());
        }
    }

    public void selectVoiture() {
        Voiture voiture = (Voiture) voituresFx.getSelectionModel().getSelectedItem();
        if (voiture != null) {
            idVoitureSelected = voiture.getId();
            nomVoitureFieldFx.setText(voiture.getNom());
            piFieldFx.setText(voiture.getPlaqueImmatriculation());
            personneSelectFx.setValue(voiture.getPersonnes());
        }
    }

    public void addVoiture() {
        try {
            Voiture voiture = new Voiture(nomVoitureFieldFx.getText(), piFieldFx.getText(), personneSelectFx.getValue());
            VoituresManager.getInstance().add(voiture);
            setupVoitures();
            clearVoiture();
        } catch (BLLException e) {
            showError(errorVoitureFx, e);
        }
    }

    public void addPersonne() {
        try {
            Personne personne = new Personne(nomPersonneFieldFX.getText(), prenomFieldFx.getText());
            PersonnesManager.getInstance().add(personne);
            setupPersonnes();
            clearPersonne();
        } catch (BLLException e) {
            showError(errorPersonneFx, e);
        }
    }

    public void deletePersonne() {
        try {
            if (idPersonneSelected != null) {
                PersonnesManager.getInstance().remove(idPersonneSelected);
                setupPersonnes();
                clearPersonne();
            }
        } catch (BLLException e) {
            showError(errorPersonneFx, e);
        }

    }

    public void deleteVoiture() {
        try {
            if (idVoitureSelected != null) {
                VoituresManager.getInstance().remove(idVoitureSelected);
                setupVoitures();
                clearVoiture();
            }
        } catch (BLLException e) {
            showError(errorVoitureFx, e);
        }

    }

    public void updatePersonne() {
        try {
            if (idPersonneSelected != null) {
                Personne personne = new Personne(idPersonneSelected, nomPersonneFieldFX.getText(), prenomFieldFx.getText());
                PersonnesManager.getInstance().update(personne);
                setupPersonnes();
                setupVoitures();
                clearPersonne();
            }
        } catch (BLLException e) {
            showError(errorPersonneFx, e);
        }

    }

    public void updateVoiture() {
        try {
            if (idVoitureSelected != null) {
                Voiture voiture = new Voiture(idVoitureSelected, nomVoitureFieldFx.getText(), piFieldFx.getText(), personneSelectFx.getValue());
                VoituresManager.getInstance().update(voiture);
                setupVoitures();
                clearVoiture();
            }
        } catch (BLLException e) {
            showError(errorVoitureFx, e);
        }
    }

    public void encodeToFile() throws FileNotFoundException {
        XMLEncoder encoder = new XMLEncoder(new FileOutputStream("C:\\Users\\ymartin2021\\IdeaProjects\\gestion_parking\\xml\\GestionParking.xml"));
        try {
            voituresPersonnes = VoituresPersonnesManager.getInstance().getAll();
            encoder.writeObject(voituresPersonnes);
            encoder.flush();
        } catch (BLLException e) {
            logger.log(Level.SEVERE, e.getMessage());
            showError(errorExportFx, e);
        } finally {
            encoder.close();
        }
    }


    public void exportCsv() throws Exception {
        try {
            File file = new File("C:\\Users\\ymartin2021\\IdeaProjects\\gestion_parking\\csv\\GestionParking.csv.");
            Writer writer = new BufferedWriter(new FileWriter(file));

            voituresPersonnes = VoituresPersonnesManager.getInstance().getAll();
            StringBuilder sb = new StringBuilder();
            sb.append("id");
            sb.append(";");
            sb.append("nom");
            sb.append(";");
            sb.append("prenom");
            sb.append(";");
            sb.append("id");
            sb.append(";");
            sb.append("nom");
            sb.append(";");
            sb.append("PI");
            sb.append(";");
            sb.append("\n");

            for (VoiturePersonne voiturePersonne : voituresPersonnes) {
                sb.append(voiturePersonne.getPersonneId());
                sb.append(";");
                sb.append(voiturePersonne.getNomPersonne());
                sb.append(";");
                sb.append(voiturePersonne.getPrenom());
                sb.append(";");
                sb.append(voiturePersonne.getVoitureId());
                sb.append(";");
                sb.append(voiturePersonne.getNomVoiture());
                sb.append(";");
                sb.append(voiturePersonne.getPI());
                sb.append("\n");
            }
            writer.write(sb.toString());
            writer.flush();
            writer.close();
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
            showError(errorExportFx, e);
        }
    }

}
