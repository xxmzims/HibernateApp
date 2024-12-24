package ru.ugrinovich;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.ugrinovich.model.Person;


/**
 * Hello world!
 *
 */


public class App 
{
    public static void main( String[] args )
    {
        // создаем конфигурацию и передаем в нее нашу сущность
        Configuration configuration = new Configuration().addAnnotatedClass(Person.class);

        // создаем сессию
        SessionFactory sessionFactory = configuration.buildSessionFactory();

        Session session = sessionFactory.getCurrentSession();

        //создаем транзакцию
        try {
            session.beginTransaction();

            Person person = session.get(Person.class, 1);
            System.out.println(person.getName());
            System.out.println(person.getAge());

            session.getTransaction().commit();

        }finally {
            // закрываем транзакцию
            sessionFactory.close();
        }
    }
}
