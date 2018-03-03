package dao;

import managers.ResultSetManager;
import model.ActiveModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class ActiveModelDAOImpl<T extends ActiveModel> implements ActiveModelDAO<T>,
        FilterModelDAO<T> {

    protected String DEFAULT_TABLE;
    protected ResultSetManager dao;
    protected Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    ActiveModelDAOImpl(Connection connection) {
        this.connection = connection;
        setDefaultTable();
    }

    public T getModelById(int id)  {
        String query = String.format("Select * from %s WHERE id=?", DEFAULT_TABLE);
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            String[] data = ResultSetManager.getObjectData(resultSet);
            return extractModel(data);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<T> getAllModels() {
        List<T> objects = new ArrayList<>();
        String query = String.format("Select * from %s", DEFAULT_TABLE);
        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            List<String[]> dataCollection = ResultSetManager.getObjectsDataCollection(resultSet);
            if (dataCollection != null) {
                objects = extractManyModels(dataCollection);
            }
            return objects;
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<T> getFilteredModelsByIntegerParameter(String sqlTableParameter, int parameterValue) {
        // sqlTableParameter e.g. "team_id"
        String query = String.format("SELECT * FROM %s WHERE %s=?",
                                        DEFAULT_TABLE, sqlTableParameter);
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, parameterValue);
            resultSet = preparedStatement.executeQuery();
            List<String[]> data = ResultSetManager.getObjectsDataCollection(resultSet);
            return extractManyModels(data);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    protected List<T> extractManyModels(List<String[]> dataCollection) {
        List<T> collection = new ArrayList<>();
        for (String [] record : dataCollection) {
            T object = extractModel(record);
            collection.add(object);
        }
        return collection;
    }

    protected abstract T extractModel(String[] data);

    public abstract void save(T t);

    public int saveObjectAndGetId(T t){
        try {
            String[] idsBefore = getCurrentIdCollection();
            save(t);
            String[] idsAfter = getCurrentIdCollection();
            String id = getNewId(idsBefore, idsAfter);
            if(id != null) return Integer.parseInt(id);
        } catch(SQLException ex) {
        System.out.println(ex.getMessage());
        }
        return -1;
    }

    private String[] getCurrentIdCollection() throws SQLException {
        
        int idIndex = 0;
        String[] currentIds = new String[0];
        String query = String.format("Select %s from %s", "id", DEFAULT_TABLE);
        preparedStatement = connection.prepareStatement(query);
        resultSet = preparedStatement.executeQuery();
        List<String[]> currentIdsCollection = ResultSetManager.getObjectsDataCollection(resultSet);

        if (currentIdsCollection != null) {
            currentIds = new String[currentIdsCollection.size()];
            for (int i = 0; i < currentIdsCollection.size(); i++) {

                currentIds[i] = currentIdsCollection.get(i)[idIndex];
            }
        }
        return currentIds;
    }

    private String getNewId(String[] oldIdCollection, String[] newIdCollection){
        for(String id : newIdCollection){
            // compare collections: old collection doesn't contain new id:
            if(! Arrays.asList(oldIdCollection).contains(id)){
                return id;
            }
        }
        return null;
    }

    protected abstract void setDefaultTable();

}
