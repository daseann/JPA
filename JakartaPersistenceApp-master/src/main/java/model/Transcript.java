package model;
import jakarta.persistence.*;

@Entity
public class Transcript {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @Column(length = 2)
    public String gradeEarned;

    @OneToOne
    public Section section;

    public Transcript() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGradeEarned() {
        return gradeEarned;
    }

    public void setGradeEarned(String gradeEarned) {
        this.gradeEarned = gradeEarned;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }


    @Override
    public String toString() {
        return "Transcript{" + "id=" + id + ", gradeEarned='" + gradeEarned + '\'' + ", section=" + section + '}';
    }
}
