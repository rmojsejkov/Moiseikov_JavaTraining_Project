package app.entities.dao.interfaces;

import app.entities.Date;

import java.time.LocalDate;
import java.util.Collection;
//import java.util.Date;

public interface DateDAO {
    boolean add(Date date);
    boolean delete (int dateId);
    boolean update(Date date, int dateId);
    Date getDate(int dateId);
    Collection<Date> getAllDates();
}
