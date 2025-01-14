package ru.ugrinovich;

import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import ru.ugrinovich.model.Principal;
import ru.ugrinovich.model.School;

import java.util.List;
import java.util.Optional;


public class App {
    public static Configuration configuration;

    // создаем сессию
    public static SessionFactory sessionFactory;

    public static Session session;

    public static void main(String[] args) {
        // создаем конфигурацию и передаем в нее нашу сущность
        configuration = new Configuration().addAnnotatedClass(School.class).addAnnotatedClass(Principal.class);
        sessionFactory = configuration.buildSessionFactory();
        session = sessionFactory.getCurrentSession();

        //создаем транзакцию
        try {
            List<Principal> principals = getAllPrincipals();
            for (Principal principal : principals) {
                System.out.println(principal);
            }
            System.out.println(getPrincipal(1));

            System.out.println();

            Optional optional = findAnySchoolOrNull();

            if (optional.isPresent()) {
                System.out.println(optional.get());
            }
//            int changedRows = updatePrincipal("dima", 1);
//            if(changedRows > 0){
//                System.out.println("Success Update");
//            }
            ScrollingPrincipal();

        } finally {
            // закрываем транзакцию
            sessionFactory.close();
        }
    }

    // Работа с query в Hibernate

    public static List<Principal> getAllPrincipals() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Query<Principal> query = session.createQuery("from Principal", Principal.class);
            // Результирующий список(аналог JPA - getResultList())
            List<Principal> result = query.list();
            transaction.commit();
            return result;
        }
    }

    public static Principal getPrincipal(int id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Query<Principal> query = session.createQuery("from Principal where id = :id", Principal.class).setParameter("id", id);
            // Если запрос подразумевает что результат будет в единственном экземпляре
            // (аналог JPA - getSingleResult())
            Principal principal = query.uniqueResult();
            transaction.commit();
            return principal;
        }
    }

    public static int updatePrincipal(String newName, int idPrincipal) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Query query = session.createQuery("update Principal set name=:newName where id=:idPrincipal").setParameter("newName", newName).setParameter("idPrincipal", idPrincipal);

            // если запрос подразумевает собой какое-то изменение БД(Возвращает количеством измененных строк)
            int countRowsChanged = query.executeUpdate();

            transaction.commit();

            return countRowsChanged;
        }
    }

    public static Optional<Principal> findAnySchoolOrNull() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            String HQL = "from School";
            Query<Principal> query = session.createQuery(HQL);

            // Также можно использовать метод stream()(Аналог JPA - getResultStream())
            Optional<Principal> optional = query.stream().findAny();

            transaction.commit();

            return optional;
        }
    }
    public static void ScrollingPrincipal(){
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Query<Principal> query = session.createQuery("from Principal", Principal.class);

            //Этот метод чем-то похож на метод stream(). Только он позволяет перемещаться по
            // списку результатов, не вытаскивая результаты вообще. То есть ты можешь выполнить запрос,
            // потом проскролить его на миллионную строку результата и начать читать оттуда данные.
            // если удалось переместиться возвращает true
            ScrollableResults<Principal> scroll = query.scroll();
            System.out.println(scroll.get());
            scroll.scroll(3);
            System.out.println(scroll.get());
            System.out.println(scroll.previous());
            System.out.println(scroll.last());
            System.out.println(scroll.first());
        }
    }
}
