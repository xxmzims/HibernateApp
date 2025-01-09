package ru.ugrinovich;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.ugrinovich.model.Item;
import ru.ugrinovich.model.Person;

import java.util.ArrayList;
import java.util.Collections;
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
        Configuration configuration = new Configuration().addAnnotatedClass(Person.class).addAnnotatedClass(Item.class);

        // создаем сессию
        SessionFactory sessionFactory = configuration.buildSessionFactory();

        Session session = sessionFactory.getCurrentSession();

        //создаем транзакцию
        try {
            session.beginTransaction();

            // получаем человека из таблицы по его id
            Person person = session.get(Person.class, 3);
            System.out.println(person);

            // Hibernate автоматически сформирует на запрос к БД, и мы получим товары человека
            List<Item> items = person.getItems();

            System.out.println(items);

            // получаем наш товар по id
            Item item = session.get(Item.class, 5);
            System.out.println(item);

            // получаем владельца товара с помощью Hibernate(так как мы все прописали в модели
            // с помощью аннотаций Hibernate автоматически делает sql запрос к нашей БД)
            Person personOwner = item.getOwner();

            System.out.println(personOwner);

            // Создаем новый товар с владельцем person
            Item newItem = new Item("Item from Hibernate", person);

            // хорошая практика "обновить" наш список товаров в самой Java(hibernate не всегда получает данные с базы данных,
            // он кэширует многие данные для оптимизации работы с ними, поэтому хоть и в бд наш список 100% добавиться,
            // но может быть такое, что hibernate выведет нам старые данные, так как он кэшировал объект.
            // Для этого мы и обновляем наш список.
            person.getItems().add(newItem);

            // сохраняем наш товар в базу данных
            session.save(newItem);

            // создаем объект нового человека
            Person person1 = new Person("Test person", 30);

            // и новый товар
            Item newItem1 = new Item("Item from Hibernate 2", person1);

            // опять же хорошая практика
            person1.setItems(new ArrayList<>(Collections.singletonList(newItem1)));

            // сохраняем наши сущности
            session.save(person1);
            session.save(newItem1);

            // получаем сущность по id
            Person person2 = session.get(Person.class, 3);

            // получаем его товары
            List<Item> items1 = person2.getItems();

            // итерируемся по списку и удаляем товары в БД
            for(Item itemb: items1)
                session.remove(itemb);

            // опять хорошая практика очищаем список, чтобы кэш был верным
            person2.getItems().clear();
            // подтверждаем нашу транзакцию
            session.getTransaction().commit();

        }finally {
            // закрываем транзакцию
            sessionFactory.close();
        }
    }
}
