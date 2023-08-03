package db.hibernate.oracleExample;

import java.sql.SQLException;
import java.util.List;

/**
 * <BR/> Теперь нам осталось разобраться со взаимодействием нашего приложения с базой данных.
 * Тогда для класса-сущности, определим интерфейс StudentDAO из пакета DAO, содержащий набор необходимых методов:
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 19.12.2013 15:27
 */
public interface StudentDAO
{
    public void addStudent(Student student) throws SQLException;   //добавить студента
    public void updateStudent(Student student) throws SQLException;//обновить студента
    public Student getStudentById(Long id) throws SQLException;    //получить стедента по id
    public List getAllStudents() throws SQLException;              //получить всех студентов
    public void deleteStudent(Student student) throws SQLException;//удалить студента
}
