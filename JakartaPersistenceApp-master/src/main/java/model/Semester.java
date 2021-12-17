package model;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Entity
public class Semester {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @Column(length = 16)
    public String title;
    public LocalDate startDate;

    @OneToMany
    public List<Section> sectionList = new ArrayList<>();

    public Semester() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public List<Section> getSectionList() {
        return sectionList;
    }

    public void setSectionList(List<Section> sectionList) {
        this.sectionList = sectionList;
    }


    public void addSection(Section s){
        this.sectionList.add(s);
        s.setSemester(this);
    }

    @Override
    public String toString() {
        return "Semester{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", startDate=" + startDate +
                '}';
    }
}
