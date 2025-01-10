package ru.ugrinovich;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.ugrinovich.model.Director;
import ru.ugrinovich.model.Movie;




/**
 * Hello world!
 *
 */


public class App 
{
    public static void main( String[] args )
    {
        // создаем конфигурацию и передаем в нее нашу сущность
        Configuration configuration = new Configuration().addAnnotatedClass(Director.class).addAnnotatedClass(Movie.class);

        // создаем сессию
        SessionFactory sessionFactory = configuration.buildSessionFactory();

        Session session = sessionFactory.getCurrentSession();

        //создаем транзакцию
        try {
            session.beginTransaction();

            Director director = session.get(Director.class, 5);



            session.getTransaction().commit();

        }finally {
            // закрываем транзакцию
            sessionFactory.close();
        }
    }
}
