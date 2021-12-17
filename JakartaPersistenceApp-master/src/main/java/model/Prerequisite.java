package model;
import jakarta.persistence.*;

@Entity
public class Prerequisite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public char minimumGrade;

    @OneToOne
    public Course course;

    public Prerequisite() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public char getMinimumGrade() {
        return minimumGrade;
    }

    public void setMinimumGrade(char minimumGrade) {
        this.minimumGrade = minimumGrade;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    @Override
    public String toString() {
        return "Prerequisite{" +
                "id=" + id +
                ", minimumGrade=" + minimumGrade +
                ", course=" + course +
                '}';
    }
}
