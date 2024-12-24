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

            Person person = new Person("Maks", 21);
            Person person1 = new Person("Dima" ,22);
            Person person2 = new Person("Vadim", 23);
            // сохранение сущностей
            session.save(person);
            session.save(person1);
            session.save(person2);

            session.getTransaction().commit();

        }finally {
            // закрываем транзакцию
            sessionFactory.close();
        }
    }
}
