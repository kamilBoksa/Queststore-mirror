package model;

import java.util.Map;
import java.util.HashMap;
import java.util.Set;

public abstract class Inventory extends StudentStock {

    private Map<Artifact,Integer> stock;

    Inventory(int ownerId) {
        super(ownerId);
        stock = new HashMap<>();
    }

    public Map<Artifact,Integer> getStock() {
        return stock;
    }

    public void addItem(Artifact item) {
        stock.put(item, 1);
        saveModel();
    }

    public void removeArtifact(Artifact artifact) {
        Artifact inStockItem = getItem(artifact.getId());
        stock.remove(inStockItem);
        saveModel();
    }

    public void modifyQuantity(Artifact artifact) {
        Artifact inStockItem = getItem(artifact.getId());
        Integer value = stock.get(inStockItem);
        stock.put(inStockItem, value + 1);
        saveModel();
    }

    public void decreaseQuantity(Artifact artifact) {
        Artifact inStockItem = getItem(artifact.getId());
        Integer value = stock.get(inStockItem);
        stock.put(inStockItem, value - 1);
        saveModel();
    }

    public Artifact getItem(int itemId) {
        Set<Artifact> inventory = stock.keySet();
        for(Artifact inStockItem: inventory){
            if (inStockItem.getId() == itemId) {
                return inStockItem;
            }
        }
    return null;
    }

    public boolean containsItem(Artifact item) {
        return getItem(item.getId()) != null;
    }

    public String toString() {
        return String.format("Inventory of (owner id): %s", getOwnerId());
    }

    public String getFullDataToString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<Artifact,Integer> entry : stock.entrySet()) {
            Artifact artifact = entry.getKey();
            Integer quantity = entry.getValue();
            stringBuilder.append(String.format("\tId: %d, Artifact: %s, Quantity: %d",
                    artifact.getId(), artifact.getName(), quantity));
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    public boolean isEmpty () {
        return stock.size() == 0;
    }

    public abstract void setStock();

    protected void setStock(Map<Artifact,Integer> stock) {
        this.stock = stock;
    }

}