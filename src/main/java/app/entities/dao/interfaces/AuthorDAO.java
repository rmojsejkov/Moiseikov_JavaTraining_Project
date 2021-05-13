package app.entities.dao.interfaces;

import app.entities.Author;

import java.util.Collection;

public interface AuthorDAO {
    boolean add(Author author);
    boolean delete (int authorId);
    boolean update(Author author, int authorId);
    Author getAuthor(int authorId);
    Collection<Author> getAllAuthors();
}
