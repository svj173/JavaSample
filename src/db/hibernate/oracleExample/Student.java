package db.hibernate.oracleExample;


/**
 * <BR/>
 * <BR/>  CREATE TABLE Student(id NUMBER(10) NOT NULL,name varchar2(100) NOT NULL, age NUMBER(3) NOT NULL, CONSTRAINT pk_Student PRIMARY KEY(id));
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 19.12.2013 15:23
 */
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name="Student")
public class Student
{
    private Long id;
        private String name;
        private Long age;

        public Student(){
            name = null;
        }

        public Student(Student s){
            name = s.getName();
        }

        @Id
        @GeneratedValue(generator="increment")
        @GenericGenerator(name="increment", strategy = "increment")
        @Column(name="id")
        public Long getId() {
            return id;
        }

        @Column(name="name")
        public String getName(){
            return name;
        }

        @Column(name="age")
        public Long getAge(){
            return age;
        }

        public void setId(Long i){
            id = i;
        }

        public void setName(String s){
            name = s;
        }

        public void setAge(Long l){
            age = l;
        }
}
