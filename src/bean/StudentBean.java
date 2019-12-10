package bean;

import db.DbConnection;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@ManagedBean
@SessionScoped
public class StudentBean {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private Map<String,Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();


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

    public String editStudent(){
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
        int id = Integer.parseInt(params.get("id"));
        DbConnection connection = new DbConnection();
        ResultSet rs = connection.getRecord(id);
        try {
            StudentBean student = new StudentBean();
            while (rs.next()) {
                student.setId(rs.getInt("id"));
                student.setFirstName(rs.getString("first_name"));
                student.setLastName(rs.getString("last_name"));
                student.setEmail(rs.getString("email"));
            }
            sessionMap.put("editStudent",student);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "edit.xhtml?faces-redirect=true";
    }

    public String updateStudent(){
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
        StudentBean student = (StudentBean) sessionMap.get("editStudent");
        int id = Integer.parseInt(params.get("id"));
        DbConnection connection = new DbConnection();
        connection.updateRecord(id,student.firstName,student.lastName,student.email);
        return "index.xhtml?faces-redirect=true";
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
