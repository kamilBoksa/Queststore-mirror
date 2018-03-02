package dao;

import enums.Table;
import managers.ResultSetManager;
import model.Team;

import java.sql.Connection;

public class TeamDAO extends ActiveModelDAOImpl<Team> {

    private String name;

    TeamDAO(Connection connection) {
        super(connection);
    }

    public Team getOneObject(String[] teamData) {

        final Integer ID_INDEX = 0;
        final Integer NAME_INDEX = 1;

        int id = Integer.parseInt(teamData[ID_INDEX]);
        name = teamData[NAME_INDEX];

        return new Team(id, name);
    }

    public void save(Team team){
        String teamId = String.valueOf(team.getId());
        name = team.getName();
        String query;
        if(teamId.equals("-1")){
            query = String.format(
                    "INSERT INTO %s " +
                            "VALUES(null, '%s');",
                    DEFAULT_TABLE, name);
        } else{
            query = String.format(
                    "UPDATE %s SET name='%s' " +
                            "WHERE id=%s;", DEFAULT_TABLE, name, teamId);
        }
        dao = new ResultSetManager();
        dao.inputData(query);
    }

    protected void setDefaultTable(){
        this.DEFAULT_TABLE = Table.TEAMS.getName();
    }
}