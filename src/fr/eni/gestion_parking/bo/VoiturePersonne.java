package fr.eni.gestion_parking.bo;

public class VoiturePersonne {
    private int personneId;
    private String nomPersonne;
    private String prenom;
    private int voitureId;
    private String nomVoiture;
    private String PI;

    public VoiturePersonne(int personneId, String nomPersonne, String prenom, int voitureId, String nomVoiture, String PI) {
        this.personneId = personneId;
        this.nomPersonne = nomPersonne;
        this.prenom = prenom;
        this.voitureId = voitureId;
        this.nomVoiture = nomVoiture;
        this.PI = PI;
    }

    public VoiturePersonne() {

    }

    public int getPersonneId() {
        return personneId;
    }

    public void setPersonneId(int personneId) {
        this.personneId = personneId;
    }

    public String getNomPersonne() {
        return nomPersonne;
    }

    public void setNomPersonne(String nomPersonne) {
        this.nomPersonne = nomPersonne;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public int getVoitureId() {
        return voitureId;
    }

    public void setVoitureId(int voitureId) {
        this.voitureId = voitureId;
    }

    public String getNomVoiture() {
        return nomVoiture;
    }

    public void setNomVoiture(String nomVoiture) {
        this.nomVoiture = nomVoiture;
    }

    public String getPI() {
        return PI;
    }

    public void setPI(String PI) {
        this.PI = PI;
    }
}
