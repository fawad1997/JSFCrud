package bean;

import db.DbConnection;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@SessionScoped
public class StudentBean {
    private int id;
    private String firstName;
    private String lastName;
    private String email;

    public StudentBean() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String addStudent(){
        try {
            DbConnection connection = new DbConnection();
            connection.insertRecord(this.firstName,this.lastName,this.email);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "index";
    }

    public List<StudentBean> getAllStudents(){
        List<StudentBean> students = new ArrayList<StudentBean>();
        try {
            DbConnection connection = new DbConnection();
            ResultSet rs = connection.getRecords();
            while (rs.next()) {
                StudentBean student = new StudentBean();
                student.setId(rs.getInt("id"));
                student.setFirstName(rs.getString("first_name"));
                student.setLastName(rs.getString("last_name"));
                student.setEmail(rs.getString("email"));
                students.add(student);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return students;
    }

}
