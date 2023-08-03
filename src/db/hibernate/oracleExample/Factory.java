package db.hibernate.oracleExample;


/**
 * <BR/> создадим класс Factory в пакете DAO, к которому будем обращаться за нашими реализациями DAO, от которых и будем вызывать необходимые нам методы:
 * <BR/>
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 19.12.2013 15:30
 */
public class Factory
{
    private static StudentDAO studentDAO = null;
          private static Factory instance = null;

          public static synchronized Factory getInstance(){
                if (instance == null){
                  instance = new Factory();
                }
                return instance;
          }

          public StudentDAO getStudentDAO(){
                if (studentDAO == null){
                  studentDAO = new StudentDAOImpl();
                }
                return studentDAO;
          }
}
