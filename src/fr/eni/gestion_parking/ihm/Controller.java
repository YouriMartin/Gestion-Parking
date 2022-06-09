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
import fr.eni.gestion_parking.bo.Personne;
import fr.eni.gestion_parking.bo.Voiture;
import fr.eni.gestion_parking.dal.DaoFactory;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

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

    private List<Personne> personnes = new ArrayList<>();
    private List<Voiture> voitures = new ArrayList<>();
    private static Integer idPersonneSelected;
    private static Integer idVoitureSelected;

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

    public void showError(Label label, BLLException e) {
        label.setVisible(true);
        label.setText(e.getMessage());
        label.setStyle("-fx-text-fill:red;");
    }

    public void setupPersonnes() throws BLLException {
        personnes = PersonnesManager.getInstance().getALL();

        nameFx.setCellValueFactory(new PropertyValueFactory<Personne, String>("nom"));
        firstnameFx.setCellValueFactory(new PropertyValueFactory<Personne, String>("prenom"));
        personnesFx.setItems(FXCollections.observableArrayList(personnes));

        personneSelectFx.setItems(FXCollections.observableArrayList(personnes));

        errorPersonneFx.setVisible(false);

    }

    public void setupVoitures() throws BLLException {
        voitures = VoituresManager.getInstance().getALL();

        voitureNameFx.setCellValueFactory(new PropertyValueFactory<Voiture, String>("nom"));
        piFx.setCellValueFactory(new PropertyValueFactory<Voiture, String>("plaqueImmatriculation"));
        nameAndFirstNameFx.setCellValueFactory(voiture -> {
            Personne personne = voiture.getValue().getPersonnes();
            return new SimpleStringProperty(personne == null ? "" :
                    personne.getNom().toUpperCase().concat(" ").concat(personne.getPrenom()));
        });
        voituresFx.setItems(FXCollections.observableArrayList(voitures));

        errorVoitureFx.setVisible(false);

    }

    public void selectPersonne() {
        Personne personne = (Personne) personnesFx.getSelectionModel().getSelectedItem();
        idPersonneSelected = personne.getId();
        nomPersonneFieldFX.setText(personne.getNom());
        prenomFieldFx.setText(personne.getPrenom());
    }

    public void selectVoiture() {
        Voiture voiture = (Voiture) voituresFx.getSelectionModel().getSelectedItem();
        idVoitureSelected = voiture.getId();
        nomVoitureFieldFx.setText(voiture.getNom());
        piFieldFx.setText(voiture.getPlaqueImmatriculation());
        personneSelectFx.setValue(voiture.getPersonnes());
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

    private static void writeXml(Document doc, OutputStream output) throws TransformerException {

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(output);

        transformer.transform(source, result);

    }

    public void exportXml() throws ParserConfigurationException, TransformerException, BLLException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        // root elements
        Document doc = docBuilder.newDocument();
        Element rootElement = doc.createElement("gestion-parking");
        doc.appendChild(rootElement);

        // child elements
        Element personnesElement = doc.createElement("personnes");
        rootElement.appendChild(personnesElement);
        Element voituresElement = doc.createElement("voitures");
        rootElement.appendChild(voituresElement);

        // Personne
        personnes = PersonnesManager.getInstance().getALL();

        for (Personne personne : personnes) {
            Element personneElement = doc.createElement("personne");
            personnesElement.appendChild(personneElement);
            personneElement.setAttribute("id", Integer.toString(personne.getId()));
            personneElement.setAttribute("nom", personne.getNom());
            personneElement.setAttribute("prenom", personne.getPrenom());
        }

        // V


        // write dom document to a file
        try (FileOutputStream output =
                     new FileOutputStream("C:\\Users\\ymartin2021\\IdeaProjects\\gestion_parking\\xml\\staff-dom.xml")) {
            writeXml(doc, output);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
