package app.entities.dao.interfaces;

import app.entities.Genre;

import java.util.Collection;

public interface GenreDAO {
    boolean add(Genre genre);
    boolean delete (int genreId);
    boolean update(Genre genre, int genreId);
    Genre getGenre(int genreId);
    Collection<Genre> getAllGenres();
}
