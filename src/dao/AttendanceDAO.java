package dao;

import enums.Table;
import model.Attendance;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AttendanceDAO implements PassiveObjDAO<Attendance> {

    private String ATTENDANCE_TABLE;
    private DbManagerDAO dao;

    public AttendanceDAO() {

        ATTENDANCE_TABLE = Table.ATTENDANCE.getName();
        dao = new DbManagerDAO();
    }

    public Map<LocalDate,Boolean> load(int ownerId) {
        LocalDate date;
        Boolean wasPresent;
        int DATE_INDEX = 0;
        int ATTENDANCE_INDEX = 1;
        Map<LocalDate,Boolean> attendance = new HashMap<>();
        final String query = String.format("SELECT date, attendance FROM %s WHERE owner_id=%s;",
                ATTENDANCE_TABLE, ownerId);
        List<String[]> dataCollection = dao.getData(query);
        for(String[] data : dataCollection){
            date = LocalDate.parse(data[DATE_INDEX]);
            wasPresent = data[ATTENDANCE_INDEX].equals("1");
            attendance.put(date, wasPresent);
        }
        return attendance;
    }

    public void save(Attendance attendance) {
        int ownerId = attendance.getOwnerId();
        String clearQuery = String.format("DELETE FROM %s WHERE owner_id=%s;", ATTENDANCE_TABLE, ownerId);
        dao.inputData(clearQuery);
        Map<LocalDate,Boolean> datesWithAttendance = attendance.getAttendance();
        if(datesWithAttendance.size() > 0) {
            Set<LocalDate> dates = datesWithAttendance.keySet();
            Boolean[] presences = datesWithAttendance.values().toArray(new Boolean[0]);
            String date;
            int presence;
            int index = 0;
            for(LocalDate localDate : dates) {
                date = localDate.toString();
                if(presences[index]) {
                    presence = 1;
                } else {
                    presence = 0;
                }
                String query = String.format("INSERT INTO %s VALUES(null, '%s', %s, %s);",
                        ATTENDANCE_TABLE, date, presence, ownerId);
                dao.inputData(query);
                index++;
            }
        }
    }
}
