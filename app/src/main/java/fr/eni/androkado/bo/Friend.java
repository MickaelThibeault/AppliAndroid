package fr.eni.androkado.bo;

public class Friend {

    private int id;
    private String nom;
    private String number;

    public Friend() {
    }

    public Friend(String nom, String number) {
        this.nom = nom;
        this.number = number;
    }

    public Friend(int id, String nom, String number) {
        this.id = id;
        this.nom = nom;
        this.number = number;
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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "Friend{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", number='" + number + '\'' +
                '}';
    }
}
