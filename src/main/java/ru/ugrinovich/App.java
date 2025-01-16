package ru.ugrinovich;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import ru.ugrinovich.model.Actor;
import ru.ugrinovich.model.Movie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class App {
    public static Configuration configuration;

    // создаем сессию

    public static Session session;

    public static void main(String[] args) {
        // создаем конфигурацию и передаем в нее нашу сущность
        configuration = new Configuration().addAnnotatedClass(Actor.class).addAnnotatedClass(Movie.class);



        //создаем транзакцию
        try(SessionFactory sessionFactory = configuration.buildSessionFactory()) {
            session = sessionFactory.getCurrentSession();
            Transaction transaction = session.beginTransaction();

            Movie movie = new Movie("Reservoir Dogs", 1992);
            Actor actor = session.get(Actor.class, 1);

            movie.setActors(new ArrayList<>(Collections.singletonList(actor)));

            actor.getMovies().add(movie);

            session.persist(movie);

            transaction.commit();
        }

    }
}
