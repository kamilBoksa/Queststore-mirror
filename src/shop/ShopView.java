package shop;

import application.AbstractView;
import item.ArtifactModel;
import java.util.List;
import java.util.Objects;

public class ShopView extends AbstractView {

    public void displayMenu() {
        String[] options = {"      *** ShopModel online ***     ",
                "[1] display available artifacts",
                "[2] buy artifact",
                "[3] buy artifact for team",
                "[0] exit"};

        for(String element : options) {
            System.out.println(element);
        }
    }

    public void displayListOfArtifacts(List<ArtifactModel> store) {
        System.out.format("%-10s | %-4s | %-3s | %-21s | %-65s | %-8s |\n", "Genre", "Id", "Type", "Name", "Description", "Price");
        System.out.println("------------------------------------------------------------------------------------------------------------------------------");
        for(ArtifactModel artifact : store) {
                System.out.format("%-10s | %-4d | %-4s | %-21s | %-65s | %-5d cc |\n", artifact.getGenre(), artifact.getId(),
                        artifact.getType(), artifact.getName(), artifact.getDescription(), artifact.getPrice());
                System.out.println("--------------------------------------------------------------------------------------------------------------------------------");
            }
        }

    public void displayListOfArtifacts(List<ArtifactModel> store, char artifactType) {
        System.out.format("%-10s | %-4s | %-3s | %-21s | %-65s | %-8s |\n", "Genre", "Id", "Type", "Name", "Description", "Price");
        System.out.println("------------------------------------------------------------------------------------------------------------------------------");
        for(ArtifactModel artifact : store) {
            if(Objects.equals(artifact.getType(), artifactType)) {
                System.out.format("%-10s | %-4d | %-4s | %-21s | %-65s | %-5d cc |\n", artifact.getGenre(), artifact.getId(),
                        artifact.getType(), artifact.getName(), artifact.getDescription(), artifact.getPrice());
                System.out.println("--------------------------------------------------------------------------------------------------------------------------------");
            }
        }

    }
}
