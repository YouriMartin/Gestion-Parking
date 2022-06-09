package fr.eni.gestion_parking.bo;

public class Voiture {
    private int id;
    private String nom;
    private String plaqueImmatriculation;
    private Personne personne;

    public Voiture() {

    }


    public Voiture(String nom, String plaqueImmatriculation, Personne personne) {
        this.nom = nom;
        this.plaqueImmatriculation = plaqueImmatriculation;
        this.personne = personne;
    }

    public Voiture(int id, String nom, String plaqueImmatriculation, Personne personne) {
        this(nom, plaqueImmatriculation, personne);
        this.id = id;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPlaqueImmatriculation() {
        return plaqueImmatriculation;
    }

    public void setPlaqueImmatriculation(String plaqueImmatriculation) {
        this.plaqueImmatriculation = plaqueImmatriculation;
    }

    public Personne getPersonnes() {
        return personne;
    }

    public void setPersonnes(Personne personne) {
        this.personne = personne;
    }
}
