package ru.ugrinovich;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.ugrinovich.model.Person;

import java.util.List;


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

            List<Person> personList = session.createQuery("FROM Person WHERE age < 30 ").getResultList();
            for (Person person: personList){
                System.out.println(person);
            }

            session.getTransaction().commit();

        }finally {
            // закрываем транзакцию
            sessionFactory.close();
        }
    }
}
