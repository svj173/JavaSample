package db.hibernate.oracleExample;


import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Expression;

import java.util.List;

/**
 * <BR/>
 * <BR/>
 Запросы возвращают набор данных из базы данных, удовлетворяющих заданному условию. Библиотека Hibernate предлагает три вида запросов к БД:
 1) Criteria
 2) SQL
 3) HQL

 * <BR/>
 * <BR/>  http://javaxblog.ru/article/java-hibernate-3/  -- Отношения в таблицах (ссылки на поля других таблиц).
 * <BR/>
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 19.12.2013 15:39
 */
public class RequestExample
{
    public void criteriaExample ( Session session )
    {
        List studs, results;

        Criteria crit = session.createCriteria(Student.class); //создаем критерий запроса
        crit.setMaxResults(50);//ограничиваем число результатов
        studs = crit.list();//помещаем результаты в список

        // Сужение выборки
        studs = session.createCriteria(Student.class)
            .add ( Expression.like ( "name", "Ivanov%" ) )
            .add ( Expression.between ( "age", 18, 25 ) )
            .list ();

        studs = session.createCriteria(Student.class)
            .add ( Expression.like ( "name", "_van%" ) )
            .add ( Expression.or (
                    Expression.eq ( "age", new Integer ( 20 ) ),
                    Expression.isNull ( "age" )
            ) )
            .list ();

        // Expression.disjunction, Expression.or — дизъюнкция (OR) — объединяет в себе несколько других выражений оператором ИЛИ.

        studs = session.createCriteria(Student.class)
            .add ( Expression.in ( "name", new String[] { "Ivanov Ivan", "Petrov Petia", "Zubin Egor" } ) )
            .add ( Expression.disjunction () )
                           .add ( Expression.isNull ( "age" ) )
                           .add ( Expression.eq ( "age", new Integer ( 20 ) ) )
                           .add ( Expression.eq ( "age", new Integer ( 21 ) ) )
                           .add ( Expression.eq ( "age", new Integer ( 22 ) ) )
            //) )
            .list ();

        // сортировка
        /*
        studs = session.createCriteria(Student.class)
            .add( Expression.like ( "name", "Iv%" )
                          .addOrder ( Order.asc ( "name" ) )//по возрастанию
                          .addOrder ( Order.desc ( "age" ) )//по убыванию
                          .list ();
        */

        // возможность запроса по данным экземпляра класса
        Student s = new Student();
        s.setName("Ivanov Ivan");
        s.setAge(20l);
        results = session.createCriteria(Student.class)
            .add( Example.create ( s ) )
            .list ();

        // Поля объекта, имеющие значение null или являющиеся идентификаторами, будут игнорироваться. Example также можно настраивать:
        Example example = Example.create(s)
            .excludeZeroes ()           //исключает поля с нулевыми значениями
            .excludeProperty ( "name" )  //исключает поле "name"
            .ignoreCase ()              //задает независимое от регистра сравнение строк
            .enableLike ();             //использует like для сравнения строк
        results = session.createCriteria(Student.class)
            .add(example)
            .list();
}

    /**
     * Применяется язык SQL - специальный hibernate.
     * @param session
     */
    public void sqlExample ( Session session )
    {
        List result;

        session.createSQLQuery("select * from Student").addEntity(Student.class).list();
        session.createSQLQuery("select id, name, age from Student").addEntity(Student.class).list();

        Query query = session.createSQLQuery("select * from Student where name like ?").addEntity(Student.class);
        result = query.setString(0, "Ivan%").list();

        // :name - это элемент для замены настоящим значением.
        query = session.createSQLQuery("select * from Student where name like :name").addEntity(Student.class);
        result = query.setString("name", "Ivan%").list();
    }

    /**
     * Hibernate позволяет производить запросы на HQL(The Hibernate Query Language — Язык запросов Hibernate), который во многом похож на язык SQL, с той разницей, что является полностью объектно-ориентированным.

     Если запрос с помощью SQL производился методом createSQLQuery, то в HQL будет просто createQuery.

     * @param session
     */
    public void hqlExample ( Session session )
    {
        List<Student> studs = (List<Student>)session.createQuery("from Student order by name").list();
        // Как видите select в начале запроса можно не указывать. Поскольку HQL — объектно-ориентированный язык, то значение полей можно выбрать и так:
        List<String> names = (List<String>)session.createQuery("select stud.name from Student stud order by name").list();
        List result = session.createQuery("select new list(stud, name, stud.age) from Student as stud").list();

    }
}
