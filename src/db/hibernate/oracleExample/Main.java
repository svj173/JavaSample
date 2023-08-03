package db.hibernate.oracleExample;

import java.sql.SQLException;
import java.util.List;

/**
 * <BR/>  RU info - http://javaxblog.ru/article/java-hibernate-2/
 * <BR/>
 * Связи в таблицах
 *
 * 1) Связь еаблиц Statistics и Test - прописываем в них связь друг на друга. В одном ManyToOne, а в другом OneToMany.
 *
 * класс Test
 * private Statistics stat;

 @ManyToOne
 @JoinTable(name = "id")
 public Statistics getStat(){
         return stat;
     }

 В классе Statistics аннотируем связь один ко многим с классом Test:
 private Set<Test> tests = new HashSet<Test>(0);

 @OneToMany
 @JoinTable(name = "id")
 public Set<Test> getTests() {
         return tests;
     }
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 19.12.2013 15:32
 */
public class Main
{
    public static void main(String[] args) throws SQLException
    {
            //Создадим двух студентов
            Student s1 = new Student();
            Student s2 = new Student();

            //Проинициализируем их
            s1.setName("Ivanov Ivan");
            s1.setAge(21l);
            s2.setName("Petrova Alisa");
            s2.setAge(24l);

            //Сохраним их в бд, id будут сгенерированы автоматически
            Factory.getInstance().getStudentDAO().addStudent(s1);
            Factory.getInstance().getStudentDAO().addStudent(s2);

            //Выведем всех студентов из бд
            List<Student> studs = Factory.getInstance().getStudentDAO().getAllStudents();
            System.out.println("========Все студенты=========");
            for(int i = 0; i < studs.size(); ++i)
            {
                    System.out.println("Имя студента : " + studs.get(i).getName() + ", Возраст : " + studs.get(i).getAge() +",  id : " + studs.get(i).getId());
                    System.out.println("=============================");
            }
        }
}
