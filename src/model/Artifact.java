package model;

import dao.ArtifactDAO;

public class Artifact extends Item {

    private int price;

    public Artifact(String name, String description, int price){
        super(name, description);
        this.id = -1;
        this.price = price;
        this.genre = "artifact";
        this.type = 'B';
        this.id = saveNewObjectGetId();
    }

    public Artifact(int id, char type, String name, String description, int price){
        super(name, description);
        this.id = id;
        this.type = type;
        this.price = price;
        this.genre = "artifact";
    }

    public int getPrice(){
        return price;
    }

    public void setPrice(int newPrice){
        price = newPrice;
        saveObject();
    }

    public String toString(){
        return super.toString() + String.format(" -price: %s\n\n", getPrice());
    }

    public int saveNewObjectGetId(){
        ArtifactDAO dao = new ArtifactDAO();
        return dao.saveObjectAndGetId(this);
    }

    public void saveObject(){
        ArtifactDAO dao = new ArtifactDAO();
        dao.saveObject(this);
    }
}