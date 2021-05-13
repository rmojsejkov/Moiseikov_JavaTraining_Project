package app.entities.dao.interfaces;

import app.entities.Play;

import java.util.Collection;

public interface PlayDAO {
    boolean add(Play play);
    boolean delete (int playId);
    boolean update(Play play, int playId);
    Play getPlay(int playId);
    Collection<Play> getAllPlay();
}
