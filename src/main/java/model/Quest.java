package model;

import enums.QuestsStatus;

public class Quest extends Item {

    private int reward;
    private String status;

    Quest(String name, String description, int reward){
        super(name, description);
        this.reward = reward;
        this.status = QuestsStatus.AVAILABLE.getName();
        setGenre("quest");
    }

    Quest(int id, char type, String name, String description, int reward, String status){
        super(id, name, type, description);
        this.reward = reward;
        this.status = status;
        setGenre("quest");

    }

    public int getReward(){
        return reward;
    }

    public void setReward(int reward){
        this.reward = reward;
        saveModel();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
        saveModel();
    }


    public String toString() {
        return String.format(super.toString() + " reward: %d, status: %s", reward, status);
    }

    public String getFullDataToString() {
        return  String.format(super.getFullDataToString() + "\n\treward: %s\n\tstatus: %s\n", reward, status);
    }
}
