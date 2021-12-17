package model;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="Student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @Column(unique=true)
    public int studentID;
    @Column(length = 128)
    public String name;


    @OneToMany
    public List<Section> sectionList = new ArrayList<>();

    @OneToMany
    public List<Transcript> transcriptList = new ArrayList<>();

    public Student() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Section> getSectionList() {
        return sectionList;
    }

    public void setSectionList(List<Section> sectionList) {
        this.sectionList = sectionList;
    }

    public List<Transcript> getTranscriptList() {
        return transcriptList;
    }

    public void setTranscriptList(List<Transcript> transcriptList) {
        this.transcriptList = transcriptList;
    }

    public void addSection(Section s){
        this.sectionList.add(s);
        s.addStudent(this);
    }

    public void addTranscript(Transcript transcript){
        transcriptList.add(transcript);
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", studentID=" + studentID +
                ", name='" + name + '\'' +
                //", sectionList=" + sectionList.toString() +
                '}';
    }

    public double getGpa(){
        int totalNumberOfUnits = 0;
        int totalPointsWithUnits = 0;
        for(Transcript transcript : transcriptList){
                 if(transcript.getGradeEarned().equals("A")){
                     totalPointsWithUnits+=transcript.getSection().getCourse().getUnits()*4;
                 }else if(transcript.getGradeEarned().equals("B")){
                     totalPointsWithUnits+=transcript.getSection().getCourse().getUnits()*3;
                 }else if(transcript.getGradeEarned().equals("C")){
                     totalPointsWithUnits+=transcript.getSection().getCourse().getUnits()*2;
                 }else if(transcript.getGradeEarned().equals("D")){
                     totalPointsWithUnits+=transcript.getSection().getCourse().getUnits()*1;
                 }else {
                     totalPointsWithUnits+=transcript.getSection().getCourse().getUnits()*0;
                 }
            totalNumberOfUnits+=transcript.getSection().getCourse().getUnits();
        }

        return totalPointsWithUnits/totalNumberOfUnits;
    }

    public RegistrationResult registerForSection(Section s){

        for(Student std :  s.getStudentList()){
            if(std.getStudentID()==this.getStudentID()){
                for(Transcript transcript : std.getTranscriptList()){
                    if(transcript.getSection().getSectionNumber()==s.getSectionNumber()){
                        if(transcript.getGradeEarned().equals("A") || transcript.getGradeEarned().equals("B")
                        || transcript.getGradeEarned().equals("C")) {
                           return RegistrationResult.ALREADY_PASSED;
                        }
                    }
                }
              return RegistrationResult.ENROLLED_IN_SECTION;
            }
        }

        for(Prerequisite prerequisite : s.getCourse().getPrerequisiteList()){
                boolean isPrerequisiteMet = true;
                for( int i=0; i<this.getSectionList().size(); i++){
                    Section section = this.getSectionList().get(i);
                    if(section.getCourse().getNumber().equals(prerequisite.getCourse().getNumber())){
                        break;
                    }
                    if(i==this.getSectionList().size()-1){
                        return RegistrationResult.NO_PREREQUISITES;
                    }
                }
        }

        for(Section sctn : this.getSectionList()){
                if(sctn.getSectionNumber() == s.getSectionNumber()){
                    continue;
                }
                if(sctn.getCourse().getNumber().equals(s.getCourse().getNumber())){
                    return RegistrationResult.ENROLLED_IN_ANOTHER;
                }
        }

        for(Section sctn :  this.getSectionList()){
            if(s.getTimeSlot().getDaysOfWeek() == sctn.getTimeSlot().getDaysOfWeek()){
                if(s.getTimeSlot().getStartTime().isAfter(sctn.getTimeSlot().getStartTime())){
                    if(!sctn.getTimeSlot().getEndTime().isBefore(s.getTimeSlot().getStartTime())){
                        return RegistrationResult.TIME_CONFLICT;
                    }
                }else if(s.getTimeSlot().getStartTime().isBefore(sctn.getTimeSlot().getStartTime())) {
                    if(!s.getTimeSlot().getEndTime().isBefore(sctn.getTimeSlot().getStartTime())){
                        return RegistrationResult.TIME_CONFLICT;
                    }
                }else if(s.getTimeSlot().getStartTime().compareTo(sctn.getTimeSlot().getStartTime())==0){
                    return RegistrationResult.TIME_CONFLICT;
                }
            }

        }


        this.addSection(s);
        return RegistrationResult.ENROLLED_IN_SECTION;
    }



}
