package users;

import application.DataTool;
import item.ArtifactModel;
import shop.*;

import java.util.List;

public class StudentController extends UserController{
    StudentModel student;
    StudentView view;
    ShopDAO shopDAO;

    public StudentController(StudentModel studentModel){
        student = studentModel;
        view = new StudentView();
        updateInventory();
    }

    private void showMyWallet(){
        view.displayMessage("Your wallet: " + String.valueOf(student.getWallet()));
    }

    private void showMyGroup(){
        view.displayMessage("Your Group:\n");
        view.displayObject(student.getGroup());
    }

    private void showMyTeam(){
        view.displayMessage("Your Team:\n");
        view.displayObject(student.getTeam());
    }

    private void showLevelOfExperience(){
        view.displayMessage("Your experience level: " + student.getExperienceLevel());
    }

    private void showMyInventory() { view.displayInventory(student.getInventory()); }

    public void removeFromInventory(ArtifactModel artifact) {student.getInventory().remove(artifact); }

    private void updateInventory() {
        shopDAO = new ShopDAO();
        List<String []> artifacts =  shopDAO.findStudentArtifacts(student.getId());
        student.setInventory(shopDAO.loadInventory(artifacts));
        shopDAO.saveInventory(student.getId(), student.getInventory());
    }

    private void executeShopping() {
        ShopModel shop = new ShopModel();
        ShopController controller = new ShopController(shop, student);
        controller.executeShoppingMenu();
        }

    private void useArtifacts() {
        showMyInventory();
        if(student.getInventory().isEmpty()){
            view.displayMessage("Sorry, You have nothing to use!");
            view.handlePause();
        } else {
            int id = view.getUserChoice("Enter artifact id: ");
            for(ArtifactModel artifact : student.getInventory()) {
                if(id == artifact.getId()) {
                    student.getInventory().remove(artifact);
                    view.displayMessage("Artifact used!");
                    shopDAO.deleteFromInventory(student.getId(), id);
                    shopDAO.saveStudentTransaction(student.getId(), artifact.getId());
                    updateInventory();
                    break;
                }
            }
        }
    }

    private void showStudentsFromMyTeam() {
        List<StudentModel> students = student.getTeam().getStudents();
        view.displayMessage("Your teammates:\n");
        view.displayUsersWithDetails(students);
    }

    public void handleMainMenu() {
        boolean isDone = false;
        while(! isDone){

            String userChoice = "";
            String[] correctChoices = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0"};
            Boolean isChoiceReady = false;
            while(! isChoiceReady){

                view.clearScreen();
                view.displayMenu();
                userChoice = view.getUserInput("Select an option: ");
                isChoiceReady = DataTool.checkIfElementInArray(correctChoices, userChoice);
            }

            view.clearScreen();
            switch(userChoice){
                case "1":
                    showProfile(student);
                    break;
                case "2":
                    showMyWallet();
                    break;
                case "3":
                    showLevelOfExperience();
                    break;
                case "4":
                    executeShopping();
                    updateInventory();
                    break;
                case "5":
                    showMyInventory();
                    break;
                case "6":
                    showMyGroup();
                    break;
                case "7":
                    showMyTeam();
                    break;
                case "8":
                    useArtifacts();
                    break;
                case "9":
                    showStudentsFromMyTeam();
                    break;
                case "0":
                    isDone = true;
                    break;
            }
            view.handlePause();
        }
    }
}
