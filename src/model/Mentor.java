package model;

import enums.Role;
import dao.MentorDAO;

public class Mentor extends User {

    private Group group;

    public Mentor(String firstName, String lastName, String password) {
        super(firstName, lastName, password);
        group = new Group(1,"undefined");
        role = Role.MENTOR.getName();
        this.id = saveNewObjectGetId();
    }

    public Mentor(int id, String firstName, String lastName,
                       String email, String password, Group group) {
        super(id, firstName, lastName, email, password);
        this.group = group;
        role = Role.MENTOR.getName();

    }

    public Group getGroup() {
        setGroup();
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
        saveObject();
    }

    public void setGroup() {
        this.group.setStudents();
    }

    public String toString()
    {
        return super.toString()+String.format(" Group name: %s", group.getName());
     }

    public String getFullDataToString() {
        return super.getFullDataToString() + String.format(
                " \n\t -group: %s\n", getGroup());
    }

    public void saveObject(){
        MentorDAO dao = new MentorDAO();
        dao.saveObject(this);
    }

    public int saveNewObjectGetId(){
        MentorDAO dao = new MentorDAO();
        return dao.saveObjectAndGetId(this);
    }

}