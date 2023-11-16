package co.edu.poli.ces3.universita.universita.Mode;

import co.edu.poli.ces3.universita.universita.dto.DtoStudents;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CRUD {

    Students create (DtoStudents students) throws SQLException;

    public ArrayList<DtoStudents> all() throws SQLException;

    public Students findById(int id) throws SQLException;

    public Students update(DtoStudents students) throws SQLException;

    public int delete(int id) throws SQLException;
}
