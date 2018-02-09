package dao;

import enums.Table;
import model.Quest;


public class QuestDAO extends FactoryDAO {

    public QuestDAO(){this.DEFAULT_TABLE = Table.QUESTS.getName();}

    public Quest getOneObject(String[] record) {

        final int ID_INDEX = 0;
        final int NAME_INDEX = 1;
        final int TYPE_INDEX = 2;
        final int DESCRIPTION_INDEX = 3;
        final int REWARD_INDEX = 4;
        final int STATUS_INDEX = 5;

        int id = Integer.parseInt(record[ID_INDEX]);
        char itemType = record[TYPE_INDEX].charAt(0);
        String itemName = record[NAME_INDEX];
        String itemDescription = record[DESCRIPTION_INDEX];
        int reward = Integer.parseInt(record[REWARD_INDEX]);
        String status = record[STATUS_INDEX];
        return new Quest(id, itemType, itemName, itemDescription, reward, status);
    }

    public <T> void saveObject(T t){
        Quest quest = (Quest) t;
        String itemId = String.valueOf(quest.getId());
        String itemType = String.valueOf(quest.getType());
        String itemName = quest.getName();
        String itemDescription = quest.getDescription();
        String reward = String.valueOf(quest.getReward());
        String status = quest.getStatus();

        String query;
        if (itemId.equals("-1")) {
            query = String.format(
                    "INSERT INTO %s " +
                            "VALUES(null, '%s', '%s', '%s', %s, '%s');",
                    DEFAULT_TABLE, itemName, itemType, itemDescription, reward, status);
        } else {
            query = String.format("UPDATE %s SET name='%s' , type='%s', description='%s', reward=%s , status='%s'" +
                    "WHERE id=%s;", DEFAULT_TABLE, itemName, itemType, itemDescription, reward, status, itemId);
        }
        DbManagerDAO dao = new DbManagerDAO();
        dao.inputData(query);
    }
}