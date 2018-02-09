package controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import model.StudentInventory;
import model.TeamInventory;
import model.Shop;
import view.ShopView;
import tools.DataTool;
import dao.ArtifactDAO;
import model.Artifact;
import model.Team;
import model.Student;

public class ShopController {

    Shop shop;
    ShopView view;
    ArtifactDAO artifactDao;
    Student student;

    public ShopController(Shop shop, Student student) {
        this.shop = shop;
        this.student = student;
        view = new ShopView();
        artifactDao = new ArtifactDAO();
        List<Artifact> artifacts = artifactDao.getManyObjects("SELECT * FROM artifacts;");
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
                userChoice = view.getUserInput("\n  Select an option: ");
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
        int id = view.getNumber("\nEnter artifact id: ");
        for (Artifact artifact : shop.getStore()) {
            if (id == artifact.getId() && Objects.equals(artifact.getType(), 'B')) {
                if (artifact.getPrice() <= student.getWallet()) {
                    finalizeTransaction(artifact);
                    pay(artifact);
                    view.displayMessage("\n   You've bought " + artifact.getName() + "!\n");
                    view.displayMessage(artifact.toString());

                } else {
                    view.displayMessage("   - This artifact is to expensive!");
                }
            }
        }
        view.displayMessage("   Bye!");
    }

    public void finalizeTransaction(Artifact artifact) {
        StudentInventory inventory = student.getInventory();
        if(inventory.containsItem(artifact)) {
            inventory.modifyQuantity(artifact);
        } else {
            inventory.addItem(artifact);
        }
    }

    public void finalizeTeamTransaction(Artifact artifact) {
        TeamInventory inventory = student.getTeam().getInventory();
        if(inventory.containsItem(artifact)) {
            inventory.modifyQuantity(artifact);
        } else {
            inventory.addItem(artifact);
        }
    }

    public void pay(Artifact artifact) {
        student.setWallet(student.getWallet() - artifact.getPrice());
    }

    public void buyArtifactForTeam() {
        view.displayListOfArtifacts(getArtifactsByType('M'));
        int id = view.getNumber("\nEnter artifact id: ");
        for (Artifact artifact : shop.getStore()) {
            if (id == artifact.getId() && Objects.equals(artifact.getType(), 'M')) {
                if (checkTeamResources(artifact, student.getTeam())) {
                    chargeTeamMembers(artifact, student.getTeam());
                    finalizeTeamTransaction(artifact);
                    view.displayMessage("   - You bought " + artifact.getName() + "!\n");
                    view.displayMessage(artifact.toString());
                } else {
                    view.displayMessage("   - Not enough coolcoins to buy this artifact!");
                }
            }
        }
    }

    public boolean checkTeamResources(Artifact artifact, Team team) {

        int pricePerTeamMember = artifact.getPrice() / team.getStudents().size();
        for (Student student : team.getStudents()) {
            if (student.getWallet() < pricePerTeamMember) {
                return false;
            }
        }
        return true;
    }

    public void chargeTeamMembers(Artifact artifact, Team team) {

        for (Student student : team.getStudents()) {
            student.setWallet(student.getWallet() - (artifact.getPrice() / team.getStudents().size()));
            }
    }

    public List<Artifact> getArtifactsByType(char type) {

        List<Artifact> artifacts = new ArrayList<>();
        for(Artifact artifact : shop.getStore()){
            if(Objects.equals(artifact.getType(), type)) {
                artifacts.add(artifact);
            }
        }
        return artifacts;
    }
}
