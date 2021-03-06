package model;

import dao.ActiveModelDAOImpl;
import managers.SQLProcessManager;
import enums.Table;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MentorDAO extends ActiveModelDAOImpl<Mentor> {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private int groupId;

    MentorDAO(Connection connection) {
        super(connection);
    }

    public Mentor extractModel(String[] record) {

        final Integer ID_INDEX = 0;
        final Integer FIRST_NAME_INDEX = 1;
        final Integer LAST_NAME_INDEX = 2;
        final Integer EMAIL_INDEX = 3;
        final Integer PASSWORD_INDEX = 4;
        final Integer GROUP_INDEX = 5;

        int mentorId = Integer.parseInt(record[ID_INDEX]);
        firstName = record[FIRST_NAME_INDEX];
        lastName = record[LAST_NAME_INDEX];
        email = record[EMAIL_INDEX];
        password = record[PASSWORD_INDEX];
        groupId = Integer.parseInt(record[GROUP_INDEX]);

        Group group = ModelDaoFactory.getByType(GroupDAO.class).getModelById(groupId);

        return new Mentor(mentorId, firstName, lastName, email, password, group);
    }

    public boolean saveModel(Mentor mentor){
        String mentorId = String.valueOf(mentor.getId());
        firstName = mentor.getFirstName();
        lastName = mentor.getLastName();
        email = mentor.getEmail();
        password = mentor.getPassword();
        groupId = mentor.getGroup().getId();

        String query;
        if (mentorId.equals("-1")) {
            query = String.format(
                "INSERT INTO %s " +
                "VALUES(null, ?, ?, ?, ?, ?)", getDefault_Table());
        } else {
            query = String.format(
                    "UPDATE %s SET first_name=?, last_name=?, email=?, password=?, group_id=?" +
                            "WHERE id=?", getDefault_Table());
        }
        try {
            PreparedStatement preparedStatement;
            preparedStatement = getConnection().prepareStatement(query);
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, password);
            preparedStatement.setInt(5, groupId);
            if(!mentorId.equals("-1")) {
                preparedStatement.setInt(6, Integer.valueOf(mentorId));
            }
            SQLProcessManager.executeUpdate(preparedStatement);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    protected void setDefaultTable(){
        setDefaultTable(Table.MENTORS);
    }
}