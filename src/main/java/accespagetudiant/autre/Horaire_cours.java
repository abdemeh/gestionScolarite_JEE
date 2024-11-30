package accespagetudiant.autre;

import java.sql.Timestamp;

public class Horaire_cours {
    private String coursNom;
    private String date;

    public Horaire_cours(String coursNom, Timestamp date) {
        this.coursNom = coursNom;
        this.date = String.valueOf(date);
    }

    public String getCoursNom() {
        return coursNom;
    }

    public String getDate() {
        return date;
    }

}
