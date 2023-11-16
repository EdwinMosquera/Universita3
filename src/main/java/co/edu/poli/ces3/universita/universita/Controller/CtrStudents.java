package co.edu.poli.ces3.universita.universita.Controller;

import co.edu.poli.ces3.universita.universita.Mode.Students;
import co.edu.poli.ces3.universita.universita.dto.DtoStudents;

import java.sql.SQLException;
import java.util.ArrayList;

public class CtrStudents {

    private Students modelStudent;

    public CtrStudents(){
        modelStudent = new Students();
    }

    public ArrayList<DtoStudents> allStudents(){
        try {
            return modelStudent.all();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public DtoStudents addStudents(DtoStudents students){
        try{
            Students newStudent = modelStudent.create(students);
            return new DtoStudents(newStudent.getDocumento(), newStudent.getNombre());
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public DtoStudents findById(int id){
        try {
            Students newStudent = modelStudent.findById(id);
            return new DtoStudents(newStudent.getId(), newStudent.getDocumento(), newStudent.getNombre());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public DtoStudents updateStudents(DtoStudents students){
        try{
            Students newStudent = modelStudent.update(students);
            return new DtoStudents(newStudent.getId(), newStudent.getDocumento(), newStudent.getNombre());
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public int deleteStudents(int id){
        try{
            int delete = modelStudent.delete(id);
            return delete;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
