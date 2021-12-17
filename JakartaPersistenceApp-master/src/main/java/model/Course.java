package model;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @Column(unique = true, length = 8)
    public String number;
    @Column(length = 64)
    public String title;
    public byte units;

    @OneToMany
    public List<Prerequisite> prerequisiteList = new ArrayList<>();

    @OneToOne
    //@JoinColumn(name = "abbreviation", referencedColumnName = "abbreviation", insertable=false, updatable=false)
    public Department department;

    public Course() {
    }


    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public byte getUnits() {
        return units;
    }

    public void setUnits(byte units) {
        this.units = units;
    }

    public List<Prerequisite> getPrerequisiteList() {
        return prerequisiteList;
    }

    public void setPrerequisiteList(List<Prerequisite> prerequisiteList) {
        this.prerequisiteList = prerequisiteList;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }


    public void addDepartment(Department d){
        this.department = d;
        d.addCourse(this);
    }

    public void addPrerequisite(Course course, char minGrade){
        Prerequisite prerequisite = new Prerequisite();
        prerequisite.setCourse(course);
        prerequisite.setMinimumGrade(minGrade);
        prerequisiteList.add(prerequisite);
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", title='" + title + '\'' +
                ", units=" + units +
                //", prerequisiteList=" + prerequisiteList +
                ", department=" + department.getName() +
                '}';
    }
}
