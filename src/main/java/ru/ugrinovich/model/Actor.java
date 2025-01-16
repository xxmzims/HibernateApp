package ru.ugrinovich.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Actor")
public class Actor {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private int age;

    @ManyToMany
    // для связи со связующей таблицей используем аннотацию JoinTable
    @JoinTable(
            // имя связывающей таблицы
            name = "Actor_Movie",
            // имя текущей сущности
            joinColumns = @JoinColumn(name = "actor_id"),
            // имя сущности с которой связываемся
            inverseJoinColumns = @JoinColumn(name = "movie_id")
     // P.S Аннотацию JoinTable достаточно использовать на одной стороне
     // так как таблицы равноправной и одну из них нельзя назвать owning side
    )
    private List<Movie> movies;

    public Actor() {
    }

    public Actor(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Actor{" +
                "age=" + age +
                ", name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
