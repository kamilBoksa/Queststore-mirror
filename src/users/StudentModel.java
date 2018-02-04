package users;

import java.util.*;

import application.Role;
import item.ArtifactModel;
import school.GroupModel;
import school.SchoolController;
import school.TeamModel;

public class StudentModel extends UserModel {

    private GroupModel group;
    private TeamModel team;
    private int wallet;
    private int experience;
    private List<ArtifactModel> inventory;
    private float attendance;
    private String experienceLevel;

    public StudentModel(String firstName, String lastName, String password) {
        super(firstName, lastName, password);
        wallet = 0;
        experience = 0;
        attendance = 100;
        team = new TeamModel(1, "undefined");
        group = new GroupModel(1,"undefined");
        inventory = new ArrayList<>();
        role = Role.STUDENT.getName();
        this.id = saveNewObjectGetId();

    }

    public StudentModel(int id, String firstName, String lastName, String email,
                        String password, int wallet, int experience, float attendance,
                        TeamModel team, GroupModel group, List<ArtifactModel> inventory) {

        super(id, firstName, lastName, email, password);
        this.wallet = wallet;
        this.experience = experience;
        this.attendance = attendance;
        this.team = team;
        this.group = group;
        this.inventory = inventory;
        role = Role.STUDENT.getName();
    }

    public GroupModel getGroup() {
        setGroup();
        return group;
    }

    public void setGroup(GroupModel group) {
        this.group = group;
        saveObject();
    }

    public void setGroup() {
        this.group.setStudents();
    }

    public TeamModel getTeam() {
        setTeam();
        return team;
    }

    public void setTeam(TeamModel team) {
        this.team = team;
        saveObject();
    }

    public void setTeam(){
        this.team.setStudents();
    }

    public int getWallet()
    {
        return wallet;
    }

    public void setWallet(int value) {
        this.wallet = value;
        saveObject();
    }

    public int getExperience()
    {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
        saveObject();
    }

    public float getAttendance()
    {
        return attendance;
    }

    public void setAttendance(float attendance) {
        // temporary!!!
        this.attendance = attendance;
    }

    public void incrementExperience(int pointsToAdd) {
        this.experience += pointsToAdd;
    }

    public void setExperienceLevel(String level, int experienceToNextLevel){
        experienceLevel = String.format("%s (%s/%s)", level, experience, experienceToNextLevel);
    }

    public String getExperienceLevel(){
        new SchoolController().setStudentExperienceLevel(this);
        return experienceLevel;
    }

    public List<ArtifactModel> getInventory() { return inventory; }

    public void setInventory(List<ArtifactModel> inventory) { this.inventory = inventory; }

    public String getFullDataToString() {
        return super.getFullDataToString() + String.format(
                " \n\t -group: %s\n\t -team: %s\n\t -wallet: %dcc\n\t" +
                " -level: %s\n\t -attendance: %.2f\n", getGroup(), getTeam(),
                wallet, getExperienceLevel(), attendance);
    }

    public void saveObject(){
        StudentDAO dao = new StudentDAO();
        dao.saveObject(this);
    }

    public int saveNewObjectGetId(){
        StudentDAO dao = new StudentDAO();
        return dao.saveObjectAndGetId(this);
    }
  
}
