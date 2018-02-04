package school;

import users.StudentDAO;

public class TeamModel extends StudentSets{

    public TeamModel(int id, String name) {
        super(id, name);
    }

    public TeamModel(String name) {
        super(name);
        this.id = saveNewObjectGetId();
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
}
