package shop;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import application.DataTool;
import item.ArtifactDAO;
import item.ArtifactModel;
import school.TeamModel;
import users.StudentDAO;
import users.StudentModel;

public class ShopController {
    ShopModel shop;
    ShopView view;
    ArtifactDAO artifactDao;
    StudentDAO studentDAO;
    StudentModel student;
    ShopDAO shopDAO;

    public ShopController(ShopModel shop, StudentModel student) {
        this.shop = shop;
        this.student = student;
        view = new ShopView();
        artifactDao = new ArtifactDAO();
        List<ArtifactModel> artifacts = artifactDao.getManyObjects("SELECT * FROM artifacts;");
        shop.setStore(artifacts);
    }

    public void executeShoppingMenu() {
        boolean isDone = false;
        while (!isDone) {
            String userChoice = "";
            String[] correctChoices = {"1", "2", "3", "0"};
            Boolean isChoiceReady = false;
            while (!isChoiceReady) {
                view.clearScreen();
                view.displayMenu();
                userChoice = view.getUserInput("Select an option: ");
                isChoiceReady = DataTool.checkIfElementInArray(correctChoices, userChoice);
            }

            view.clearScreen();
            switch (userChoice) {
                case "1":
                    view.displayListOfArtifacts(shop.getStore());
                    view.handlePause();
                    break;
                case "2":
                    buyArtifact();
                    view.handlePause();
                    break;
                case "3":
                    buyArtifactForTeam();
                    view.handlePause();
                    break;
                case "0":
                    isDone = true;
                    break;
            }
        }
    }

    public void buyArtifact() {
        view.displayListOfArtifacts(getArtifactsByType('B'));
        int id = view.getNumber("Enter artifact id: ");
        for (ArtifactModel artifact : shop.getStore()) {
            if (id == artifact.getId() && Objects.equals(artifact.getType(), 'B')) {
                if (artifact.getPrice() <= student.getWallet()) {
                    finalizeTransaction(artifact);
                    pay(artifact);
                    view.displayMessage("You bought " + artifact.getName() + "!");

                } else {
                    view.displayMessage("This artifact is to expensive!");
                }
            }
        }
        view.displayMessage("Bye");
    }

    public void finalizeTransaction(ArtifactModel artifact) {
        InventoryModel inventory = student.getInventory();
        if(inventory.containsItem(artifact)) {
            inventory.modifyQuantity(artifact);
        } else {
            inventory.addItem(artifact);
        }
    }

    public void pay(ArtifactModel artifact) {
        student.setWallet(student.getWallet() - artifact.getPrice());
    }

    public void saveStudentInventory(ArtifactModel artifact) {
        shopDAO = new ShopDAO();
        shopDAO.saveInventory(student.getId(), "students_artifacts", student.getInventory());
        shopDAO.saveStudentTransaction(student.getId(), artifact.getId());
    }

    public void buyArtifactForTeam() {
        view.displayListOfArtifacts(getArtifactsByType('M'));
        int id = view.getNumber("Enter artifact id: ");
        for (ArtifactModel artifact : shop.getStore()) {
            if (id == artifact.getId() && Objects.equals(artifact.getType(), 'M')) {
                if (checkTeamResources(artifact, student.getTeam())) {
                    chargeTeamMembers(artifact, student.getTeam());
                    student.getTeam().getInventory().add(artifact);
                    view.displayMessage("You bought " + artifact.getName() + "!");
//                    save

                } else {
                    view.displayMessage("Not enough coolcoins to buy this artifact!");
                }
            }
        }
    }

    public boolean checkTeamResources(ArtifactModel artifact, TeamModel team) {

        int pricePerTeamMember = artifact.getPrice() / team.getStudents().size();
        for (StudentModel student : team.getStudents()) {
            if (student.getWallet() < pricePerTeamMember) {
                return false;
            }
        }
        return true;
    }

    public void chargeTeamMembers(ArtifactModel artifact, TeamModel team) {

        for (StudentModel student : team.getStudents()) {
            student.setWallet(student.getWallet() - (artifact.getPrice() / team.getStudents().size()));
            }
    }

    public List<ArtifactModel> getArtifactsByType(char type) {

        List<ArtifactModel> artifacts = new ArrayList<>();
        for(ArtifactModel artifact : shop.getStore()){
            if(Objects.equals(artifact.getType(), type)) {
                artifacts.add(artifact);
            }
        }
        return artifacts;
    }
  
}

