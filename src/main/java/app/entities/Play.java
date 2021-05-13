package app.entities;

import app.entities.dao.factory.DAOFactory;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

public class Play {

    private int id;
    private String name;
    private int authorId;
    private int genreId;

    private Author author;
    private Genre genre;

    private Collection<Date> dates;

    public Play() {

    }

    public Play(int id, String name, int authorId, int genreId) {
        this.id = id;
        this.name = name;
        this.authorId = authorId;
        this.genreId = genreId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public int getGenreId() {
        return genreId;
    }

    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }

    public Author getAuthor() {
        if (author == null) {
            author = DAOFactory.getDb().getAuthorDAO()
                    .getAuthor(authorId);
        }
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Genre getGenre() {
        if (genre == null) {
            genre = DAOFactory.getDb().getGenreDAO()
                    .getGenre(genreId);
        }
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public Collection<Date> getDates() {
        if (dates == null) {
            dates = DAOFactory.getDb().getDateDAO()
                    .getAllDates()
                    .stream()
                    .filter(date -> date.getPlayId() == getId())
                    .collect(Collectors.toSet());
        }
        return dates;
    }

    public void setDates(Collection<Date> dates) {
        this.dates = dates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Play play = (Play) o;
        return id == play.id &&
                authorId == play.authorId &&
                genreId == play.genreId &&
                Objects.equals(name, play.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, authorId, genreId);
    }

    @Override
    public String toString() {
        return "Play{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", authorId=" + authorId +
                ", genreId=" + genreId +
                '}';
    }
}
