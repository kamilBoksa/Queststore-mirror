package view;

import model.Artifact;
import java.util.List;

public class ShopView extends AbstractView {

    public void displayMenu() {
        String[] options = {"\n      *** Shop online ***     \n",
                "[1] display available artifacts",
                "[2] buy artifact",
                "[3] buy artifact for team",
                "[0] exit"};

        for(String element : options) {
            System.out.println(element);
        }
    }

    public void displayListOfArtifacts(List<Artifact> store) {
        System.out.format("%-4s | %-3s | %-21s | %-80s | %-9s |\n", "Id", "Type", "Name", "Description", "Price");
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------------");
        for (Artifact artifact : store) {
            System.out.format("%-4d | %-4s | %-21s | %-80s | %-6d cc |\n", artifact.getId(),
                    artifact.getType(), artifact.getName(), artifact.getDescription(), artifact.getPrice());
            System.out.println("---------------------------------------------------------------------------------------------------------------------------------------");
        }
    }

}