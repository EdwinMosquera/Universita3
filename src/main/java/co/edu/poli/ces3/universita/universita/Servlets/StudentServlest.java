package co.edu.poli.ces3.universita.universita.Servlets;

import co.edu.poli.ces3.universita.universita.Controller.CtrStudents;
import co.edu.poli.ces3.universita.universita.dto.DtoStudents;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "studentServlet", value = "/student")
public class StudentServlest extends MyServlet {
    private String message;

    private GsonBuilder gsonBuilder;
    private Gson gson;
    private ArrayList<DtoStudents> student = new ArrayList<>();

    public void init() {

        gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();

        DtoStudents student1 = new DtoStudents();
        student1.setId(12);
        student1.setNombre("Edwin");
        student1.setDocumento("1234567");

        DtoStudents student2 = new DtoStudents();
        student2.setId(15);
        student2.setNombre("Nicolas");
        student2.setDocumento("9876543");

        student.add(student1);
        student.add(student2);

        for (int i=0; i<student.size(); i++){

            System.out.println(student.get(i));
        }

        message = "student";
    }


    public StudentServlest(){
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        String id = request.getParameter("studentId");
        CtrStudents ctr = new CtrStudents();
        if (id == null){
            out.println(gson.toJson(ctr.allStudents()));
        }else {
            DtoStudents studentsId = ctr.findById(Integer.parseInt(id));
            out.println(gson.toJson(studentsId));
        }
        // Hello

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletOutputStream out = resp.getOutputStream();
        resp.setContentType("application/json");

        JsonObject body = this.getParamsFromPost(req);
        CtrStudents ctr = new CtrStudents();
        DtoStudents students = new DtoStudents (
                body.get("documento").getAsString(),
                body.get("nombre").getAsString()
        );

        DtoStudents newStudent = ctr.addStudents(students);

        out.print(gson.toJson(newStudent));
        out.flush();
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletOutputStream out = resp.getOutputStream();
        resp.setContentType("application/json");
        JsonObject body = this.getParamsFromPost(req);
        CtrStudents ctr = new CtrStudents();

        if (body != null){
            DtoStudents students = new DtoStudents (
                    body.get("id").getAsInt(),
                    body.get("documento").getAsString(),
                    body.get("nombre").getAsString()
            );
            DtoStudents updateStudent = ctr.updateStudents(students);
            out.println(gson.toJson(updateStudent));
        }else {
            out.println(gson.toJson(student));
        }

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletOutputStream out = resp.getOutputStream();
        resp.setContentType("application/json");
        JsonObject body = this.getParamsFromPost(req);
        CtrStudents ctr = new CtrStudents();

        if (body != null){
            int deleteStudent = ctr.deleteStudents(body.get("id").getAsInt());
            out.println(gson.toJson(deleteStudent));
        }
    }

    public void destroy() {
    }
}