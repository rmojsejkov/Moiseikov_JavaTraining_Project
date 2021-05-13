package app.entities;

import java.time.LocalDate;
import java.util.Objects;

public class Date {

    private int id;
    private LocalDate playsDate;
    private int playId;
    private int ticketsNum;

    public Date(int id, LocalDate playsDate, int playId, int ticketsNum) {
        this.id = id;
        this.playsDate = playsDate;
        this.playId = playId;
        this.ticketsNum = ticketsNum;
    }

    public Date() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getPlaysDate() {
        return playsDate;
    }

    public void setPlaysDate(LocalDate playsDate) {
        this.playsDate = playsDate;
    }

    public int getPlayId() {
        return playId;
    }

    public void setPlayId(int playId) {
        this.playId = playId;
    }

    public int getTicketsNum() {
        return ticketsNum;
    }

    public void setTicketsNum(int ticketsNum) {
        this.ticketsNum = ticketsNum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Date date = (Date) o;
        return id == date.id &&
                playId == date.playId &&
                ticketsNum == date.ticketsNum &&
                Objects.equals(playsDate, date.playsDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, playsDate, playId, ticketsNum);
    }

    @Override
    public String toString() {
        return "Date{" +
                "id=" + id +
                ", playsDate=" + playsDate +
                ", playId=" + playId +
                ", ticketsNum=" + ticketsNum +
                '}';
    }
}
