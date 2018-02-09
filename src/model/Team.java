package model;

import dao.TeamDAO;
import dao.StudentDAO;

public class Team extends StudentSets {

    private TeamInventory inventory;

    public Team(int id, String name) {
        super(id, name);
        inventory = new TeamInventory(id);
    }

    public Team(String name) {
        super(name);
        this.id = saveNewObjectGetId();
        inventory = new TeamInventory(id);
    }

    public TeamInventory getInventory() {
        inventory.setStock();
        return inventory;
    }

    public void setInventory(TeamInventory inventory) {
        this.inventory = inventory;
    }

    public int saveNewObjectGetId(){
        TeamDAO dao = new TeamDAO();
        return dao.saveObjectAndGetId(this);
    }

    public void setStudents() {
        StudentDAO dao = new StudentDAO();
        final String query = String.format("SELECT * FROM students WHERE team_id=%s;", id);
        this.students = dao.getManyObjects(query);
    }

    public void saveObject(){
        TeamDAO dao = new TeamDAO();
        dao.saveObject(this);
    }

    public int size(){
        return students.size();
    }
}