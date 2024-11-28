package accespagetudiant;

public class Note {
    private String coursNom;
    private int note;

    public Note(String coursNom, int note) {
        this.coursNom = coursNom;
        this.note = note;
    }

    public String getCoursNom() {
        return coursNom;
    }

    public int getNote() {
        return note;
    }
}

