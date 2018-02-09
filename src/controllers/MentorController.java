package controllers;

import model.Mentor;
import model.Student;
import dao.MentorDAO;
import dao.ArtifactDAO;
import model.Artifact;
import dao.QuestDAO;
import model.Quest;
import view.MentorView;

import java.util.List;

public class MentorController extends UserController{
    MentorView view;
    Mentor mentor;
    MentorDAO dao;

    public MentorController(Mentor mentor){
        this.mentor = mentor;
        view = new MentorView();
        dao = new MentorDAO();
    }

    public void handleMainMenu(){
        boolean isDone = false;
        while(! isDone){
            String[] correctChoices = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "0"};
            view.clearScreen();
            view.displayMenu();
            String userChoice = view.getMenuChoice(correctChoices);
            view.clearScreen();
            switch(userChoice){
                case "1":
                    showProfile(mentor);
                    break;
                case "2":
                    createStudent();
                    break;
                case "3":
                    assignStudentToGroup();
                    break;
                case "4":
                    assignStudentToTeam();
                    break;
                case "5":
                    createQuest();
                    break;
                case "6":
                    editQuest();
                    break;
                case "7":
                    createArtifact();
                    break;
                case "8":
                    editArtifact();
                    break;
                case "9":
                    markStudentQuest();
                    break;
                case "10":
                    displayStudentDetails();
                    break;
                case "11":
                    markStudentArtifacts();
                    break;
                case "12":
                    showStudentsFromMyGroup();
                    break;
                case "13":
                    SchoolController.createNewTeam();
                    break;
                case "14":
                    SchoolController.checkAttendance(mentor);
                    break;
                case "0":
                    isDone = true;
                    break;
            }
            view.handlePause();
        }
    }

    private void createQuest() {
        String name = view.getUserInput("Enter quest name: ");
        String description = view.getUserInput("Enter quest description: ");
        int reward = view.getValue();
        Quest quest = new Quest(name, description, reward);
        view.displayMessage("You've created something new:\n" + quest);
    }

    private void editQuest() {

        List<Quest> quests = getQuests();
        view.displayElementsOfCollection(prepareObjectsToDisplay(quests));
        String input = view.getUserInput("Enter id of quest which you would like to edit: ");
        for(Quest quest : quests) {
            if(String.valueOf(quest.getId()).equals(input)) {
                handleQuestEditMenu(quest);
                break;
            }
        }

    }

    private void createStudent(){
        String firstName = view.getUserInput("Enter first name: ");
        String lastName = view.getUserInput("Enter last name: ");
        String password = view.getUserInput("Enter password: ");
        Student newStudent = new Student(firstName, lastName, password);
        view.displayMessage("Student created successfully! \n" + newStudent.toString());
    }

    private void createArtifact(){
        String name = view.getUserInput("Enter artifact name: ");
        String description = view.getUserInput("Enter artifact description: ");
        int price = view.getValue();
        Artifact artifact = new Artifact(name, description, price);
        view.displayMessage("You've created something new:\n" + artifact);
        }

    private void editArtifact(){
        List<Artifact> artifacts = getArtifacts();
        view.displayElementsOfCollection(prepareObjectsToDisplay(artifacts));
        String input = view.getUserInput("Enter id of artifact which you would like to edit: ");
        for(Artifact artifact : artifacts) {
            if(String.valueOf(artifact.getId()).equals(input)) {
                handleArtifactEditMenu(artifact);
                break;
            }
        }
    }

    private void handleArtifactEditMenu(Artifact artifact){
        boolean isDone = false;
        while(! isDone){
            String[] correctChoices = {"1", "2", "3", "4", "0"};
            view.clearScreen();
            view.displayArtifactMenu();
            String userChoice = view.getMenuChoice(correctChoices);
            view.clearScreen();
            switch(userChoice){
                case "1":
                    artifact.setName(view.getUserInput("Enter new artifact name: "));
                    break;
                case "2":
                    artifact.setType(view.getUserInput("Enter new artifact type: ").charAt(0));
                    break;
                case "3":
                    artifact.setDescription(view.getUserInput("Enter new artifact description: "));
                    break;
                case "4":
                    artifact.setPrice(view.getValue());
                    break;
                case "0":
                    ArtifactDAO dao = new ArtifactDAO();
                    dao.saveObject(artifact);
                    isDone = true;
                    break;
            }
            if(! isDone){
               view.displayMessage(artifact.toString());
               view.handlePause();
            }
        }
    }

    private void handleQuestEditMenu(Quest quest){
        boolean isDone = false;
        while(! isDone){
            String[] correctChoices = {"1", "2", "3", "4", "0"};
            view.clearScreen();
            view.displayQuestMenu();
            String userChoice = view.getMenuChoice(correctChoices);
            view.clearScreen();
            switch(userChoice){
                case "1":
                    quest.setName(view.getUserInput("Enter new quest name: "));
                    break;
                case "2":
                    quest.setType(view.getUserInput("Enter new quest type: ").charAt(0));
                    break;
                case "3":
                    quest.setDescription(view.getUserInput("Enter new quest description: "));
                    break;
                case "4":
                    quest.setReward(view.getValue());
                    break;
                case "0":
                    QuestDAO dao = new QuestDAO();
                    dao.saveObject(quest);
                    isDone = true;
                    break;
            }
            if(! isDone){
                view.displayMessage(quest.toString());
                view.handlePause();
            }
        }
    }

    private void markStudentQuest() {
        executeNotImplementedInfo();
    }

    private void displayStudentWallet() {
        executeNotImplementedInfo();
    }

    private void markStudentArtifacts() {
        executeNotImplementedInfo();
    }

    private void showStudentsFromMyGroup() {
        List<Student> students = SchoolController.getStudentsByGroup(mentor.getGroup());
        view.displayMessage("\nYour students:\n");
        view.displayUsersWithDetails(students);
    }

    private void assignStudentToGroup() {
        List<Student> students = SchoolController.getAllStudents();
        Student student = SchoolController.pickStudentFromList(students);
        if (student != null){
            SchoolController.assignStudentToGroup(student);
            view.displayMessage("\nStudent edited:\n");
            view.displayUserWithDetails(student);
        } else {
            view.displayMessage("\nStudent does not exist...");
        }
    }

    private void assignStudentToTeam() {
        List<Student> students = SchoolController.getStudentsByGroup(mentor.getGroup());
        Student student = SchoolController.pickStudentFromList(students);
        if (student != null){
            SchoolController.assignStudentToTeam(student);
            view.displayMessage("\nStudent edited:\n");
            view.displayUserWithDetails(student);
        } else {
            view.displayMessage("\nStudent does not exist...");
        }
    }

    private void displayStudentDetails() {
        Student student = SchoolController.pickStudentFromList(mentor.getGroup().getStudents());
        if (student != null) {
            view.clearScreen();
            view.displayMessage("\n\n\tDetailed info:\n");
            view.displayUserWithDetails(student);
            String attendanceInfo = student.getAttendance().getFullDataToString();
            view.displayMessage(attendanceInfo);
        } else {
            view.displayMessage("   - there is no one to show..");
        }
    }
}