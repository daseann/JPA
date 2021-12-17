import jakarta.persistence.Query;
import model.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager em = entityManagerFactory.createEntityManager();
        //em.getTransaction().begin();
        System.out.println("Select Option");
        System.out.println("1. Initiate Models");
        System.out.println("2. Student lookup");
        System.out.println("3. Register for a course");

        Scanner in = new Scanner(System.in);
        System.out.print("Enter your choice:");
        int choice = in.nextInt();
        boolean isModelsInitialized = false;


        while(true){
            if(choice==1){
                if(isModelsInitialized){
                    System.out.print("Database already initialized, Please Choose other options: ");
                    choice = in.nextInt();
                }else {
                    InitiateModels(em);
                    isModelsInitialized = true;
                    System.out.println("Database initialized Successfully");
                    choice = -1;

                }
            }else if(choice==2){
                System.out.print("Enter the name of a student:");
                in.nextLine();   // skip the newline character
                String stdName = in.nextLine();

                Query query = em.createQuery("Select s from Student s where s.name LIKE '%"+stdName+"%'");
                List<Student> list = query.getResultList();
                if(list.size()==0){
                    System.out.println("No Student Found with the name: '"+stdName+"'");
                }
                for(Student std :  list){
                    for(int i=0; i<std.getTranscriptList().size(); i++){
                        System.out.print(std.getTranscriptList().get(i).getSection().getCourse().getDepartment().getAbbreviation()+" ");
                        System.out.print(std.getTranscriptList().get(i).getSection().getCourse().getNumber()+", ");
                        System.out.print(std.getTranscriptList().get(i).getSection().getSemester().getTitle()+". ");
                        System.out.println("Grade earned: "+std.getTranscriptList().get(i).getGradeEarned());
                    }
                    System.out.println("GPA "+std.getGpa());
                }
                choice = -1;
            }else if(choice==3){
                Query query = em.createQuery("Select sem from Semester sem");
                List<Semester> list = query.getResultList();
                if(list.size()==0){
                    System.out.println("No Semester Found");
                }else {

                    for(int i=0; i<list.size(); i++){
                        System.out.println(i+"."+list.get(i).getTitle());
                    }
                    System.out.print("Select Semester:");
                    int SemesterSelection = in.nextInt();
                    Semester  selectedSemester = list.get(SemesterSelection);
                    System.out.println();

                    System.out.print("Enter the name of a student:");
                    in.nextLine();   // skip the newline character
                    String stdName = in.nextLine();

                    Query query2 = em.createQuery("Select s from Student s where s.name LIKE '%"+stdName+"%'");
                    List<Student> studentList = query2.getResultList();
                    if(studentList.size()==0){
                        System.out.println("No Student Found with the name: '"+stdName+"'");
                    }else {

                        Student studentForRegistration = studentList.get(0);
                        //selectedSemester

                        System.out.print("Enter the name of course section:");
                        String userinput = in.nextLine();

                        String[] department  = userinput.split(" ");

                        String[] CrsSection = department[1].split("-");

                        String crsNum,sectionNum,dep = "";

                        crsNum = CrsSection[0];
                        sectionNum = CrsSection[1];
                        dep = department[0];

                        System.out.println("Entered Course Number:"+crsNum);
                        System.out.println("Entered Section Number:"+sectionNum);
                        System.out.println("Entered Department Name:"+dep);


                        Query query3 = em.createQuery("Select sec from Section sec where sec.sectionNumber = "+sectionNum);
                        List<Section> sectionList = query3.getResultList();
                        if(sectionList.size()==0){
                            System.out.println("No Section Found with the section number: '"+sectionNum+"'");
                        }else {

                            Section sectionForRegistration = sectionList.get(0);
                            RegistrationResult registrationResult = studentForRegistration.registerForSection(sectionForRegistration);
                            System.out.println("Student: "+studentForRegistration);
                            System.out.println("Registration Result: "+registrationResult);

                        }


                    }

                }
                choice = -1;
            }else {
                System.out.println();
                System.out.print("Enter your choice:");
                choice = in.nextInt();
                //break;
            }
        }


        /*em.getTransaction().commit();
        em.close();*/
        //entityManagerFactory.close();

    }

    public static void InitiateModels(EntityManager entityManager){
        //entityManager.getTransaction().begin();
        entityManager.getTransaction().begin();
        entityManager.flush();
        entityManager.clear();
        Semester spring2021 = new Semester();
        Semester fall2021 = new Semester();
        Semester spring2022 = new Semester();

        spring2021.setTitle("Spring 2021");
        spring2021.setStartDate(LocalDate.of(2021,1,19));

        fall2021.setTitle("Fall 2021");
        fall2021.setStartDate(LocalDate.of(2021,8,17));

        spring2022.setTitle("Spring 2022");
        spring2022.setStartDate(LocalDate.of(2022,1,20));

        Department CECS = new Department();
        Department ITAL = new Department();

        CECS.setAbbreviation("CECS");
        CECS.setName("Computer Engineering and Computer Science");

        ITAL.setAbbreviation("ITAL");
        ITAL.setName("Italian Studies");

        Course cecs174 = new Course();
        Course cecs274 = new Course();
        Course cecs277 = new Course();
        Course cecs282 = new Course();
        Course ital101A = new Course();
        Course ital101B = new Course();

        cecs174.setNumber("174");
        cecs174.setTitle("Introduction to Programming and Problem Solving.");
        cecs174.setUnits((byte)3);
        cecs174.setDepartment(CECS);

        cecs274.setNumber("274");
        cecs274.setTitle("Data Structures.");
        cecs274.setUnits((byte)3);
        cecs274.addPrerequisite(cecs174,'C');
        cecs274.setDepartment(CECS);

        cecs277.setNumber("277");
        cecs277.setTitle("Object Oriented Application Programming.");
        cecs277.setUnits((byte)3);
        cecs277.addPrerequisite(cecs174,'C');
        cecs277.setDepartment(CECS);

        cecs282.setNumber("282");
        cecs282.setTitle("Advanced C++.");
        cecs282.setUnits((byte)3);
        cecs282.addPrerequisite(cecs274,'C');
        cecs282.setDepartment(CECS);

        ital101A.setNumber("101A");
        ital101A.setTitle("Fundamentals of Italian.");
        ital101A.setUnits((byte)4);
        ital101A.setDepartment(ITAL);

        ital101B.setNumber("101B");
        ital101B.setTitle("Fundamentals of Italian.");
        ital101B.setUnits((byte)4);
        ital101B.addPrerequisite(ital101A,'D');
        ital101B.setDepartment(ITAL);

        TimeSlot mw = new TimeSlot();
        TimeSlot tuTh = new TimeSlot();
        TimeSlot mwf = new TimeSlot();
        TimeSlot fri = new TimeSlot();

        mw.setDaysOfWeek((byte)40);
        mw.setStartTime(LocalTime.of(12,30));
        mw.setEndTime(LocalTime.of(13,45));

        tuTh.setDaysOfWeek((byte)20);
        tuTh.setStartTime(LocalTime.of(14,00));
        tuTh.setEndTime(LocalTime.of(15,45));

        mwf.setDaysOfWeek((byte)42);
        tuTh.setStartTime(LocalTime.of(12,00));
        tuTh.setEndTime(LocalTime.of(12,50));

        fri.setDaysOfWeek((byte)2);
        fri.setStartTime(LocalTime.of(8,00));
        fri.setEndTime(LocalTime.of(10,45));


        Section a = new Section();
        Section b = new Section();
        Section c = new Section();
        Section d = new Section();
        Section e = new Section();
        Section f = new Section();
        Section g = new Section();


        a.setCourse(cecs174);
        a.setSectionNumber(1);
        a.setSemester(spring2021);
        a.setTimeSlot(mw);
        a.setMaxCapacity(105);


        b.setCourse(cecs274);
        b.setSectionNumber(1);
        b.setSemester(fall2021);
        b.setTimeSlot(tuTh);
        b.setMaxCapacity(140);

        c.setCourse(cecs277);
        c.setSectionNumber(3);
        c.setSemester(fall2021);
        c.setTimeSlot(fri);
        c.setMaxCapacity(35);

        d.setCourse(cecs282);
        d.setSectionNumber(5);
        d.setSemester(spring2022);
        d.setTimeSlot(tuTh);
        d.setMaxCapacity(35);

        e.setCourse(cecs277);
        e.setSectionNumber(1);
        e.setSemester(spring2022);
        e.setTimeSlot(mw);
        e.setMaxCapacity(35);

        f.setCourse(cecs282);
        f.setSectionNumber(7);
        f.setSemester(spring2022);
        f.setTimeSlot(mw);
        f.setMaxCapacity(35);

        g.setCourse(ital101A);
        g.setSectionNumber(1);
        g.setSemester(spring2022);
        g.setTimeSlot(mwf);
        g.setMaxCapacity(25);


        Student NaomiNagata = new Student();
        Student JamesHolden = new Student();
        Student AmosBurton = new Student();

        NaomiNagata.setName("Naomi Nagata");
        NaomiNagata.setStudentID(123456789);
        Transcript transcript1 = new Transcript();
        transcript1.setGradeEarned("A");
        transcript1.setSection(a);
        NaomiNagata.addTranscript(transcript1);
        Transcript transcript2 = new Transcript();
        transcript2.setGradeEarned("A");
        transcript2.setSection(b);
        NaomiNagata.addTranscript(transcript2);
        Transcript transcript3 = new Transcript();
        transcript3.setGradeEarned("A");
        transcript3.setSection(c);
        NaomiNagata.addTranscript(transcript3);
        //NaomiNagata.addSection(d);


        JamesHolden.setName("James Holden");
        JamesHolden.setStudentID(987654321);
        Transcript transcript4 = new Transcript();
        transcript4.setGradeEarned("C");
        transcript4.setSection(a);
        JamesHolden.addTranscript(transcript4);
        Transcript transcript5 = new Transcript();
        transcript5.setGradeEarned("C");
        transcript5.setSection(b);
        JamesHolden.addTranscript(transcript5);
        Transcript transcript6 = new Transcript();
        transcript6.setGradeEarned("C");
        transcript6.setSection(c);
        JamesHolden.addTranscript(transcript6);


        AmosBurton.setName("Amos Burton");
        AmosBurton.setStudentID(555555555);
        Transcript transcript7 = new Transcript();
        transcript7.setGradeEarned("C");
        transcript7.setSection(a);
        AmosBurton.addTranscript(transcript7);
        Transcript transcript8 = new Transcript();
        transcript8.setGradeEarned("B");
        transcript8.setSection(b);
        AmosBurton.addTranscript(transcript8);
        Transcript transcript9 = new Transcript();
        transcript9.setGradeEarned("D");
        transcript9.setSection(c);
        AmosBurton.addTranscript(transcript9);



        //Persisting Semesters
        entityManager.persist(spring2021);
        entityManager.persist(fall2021);
        entityManager.persist(spring2022);

        //Persisting Departments
        entityManager.persist(CECS);
        entityManager.persist(ITAL);

        //Persisting Prerequisite
        for(Prerequisite prerequisite : cecs174.getPrerequisiteList() ){
            entityManager.persist(prerequisite);
        }

        for(Prerequisite prerequisite : cecs274.getPrerequisiteList() ){
            entityManager.persist(prerequisite);
        }

        for(Prerequisite prerequisite : cecs277.getPrerequisiteList() ){
            entityManager.persist(prerequisite);
        }

        for(Prerequisite prerequisite : cecs282.getPrerequisiteList() ){
            entityManager.persist(prerequisite);
        }

        for(Prerequisite prerequisite : ital101A.getPrerequisiteList() ){
            entityManager.persist(prerequisite);
        }

        for(Prerequisite prerequisite : ital101B.getPrerequisiteList() ){
            entityManager.persist(prerequisite);
        }


        //Persisting Courses
        entityManager.persist(cecs174);
        entityManager.persist(cecs274);
        entityManager.persist(cecs277);
        entityManager.persist(cecs282);
        entityManager.persist(ital101A);
        entityManager.persist(ital101B);


        //Persisting Timeslots
        entityManager.persist(mw);
        entityManager.persist(tuTh);
        entityManager.persist(mwf);
        entityManager.persist(fri);

        //Persisting Sections
        entityManager.persist(a);
        entityManager.persist(b);
        entityManager.persist(c);
        entityManager.persist(d);
        entityManager.persist(e);
        entityManager.persist(f);
        entityManager.persist(g);


        //Persisting Transcript
        entityManager.persist(transcript1);
        entityManager.persist(transcript2);
        entityManager.persist(transcript3);
        entityManager.persist(transcript4);
        entityManager.persist(transcript5);
        entityManager.persist(transcript6);
        entityManager.persist(transcript7);
        entityManager.persist(transcript8);
        entityManager.persist(transcript9);


        //Persisting Student
        entityManager.persist(NaomiNagata);
        entityManager.persist(JamesHolden);
        entityManager.persist(AmosBurton);



        entityManager.getTransaction().commit();
        //entityManager.close();
        //return entityManager;
    }

}
