package co.edu.poli.ces3.universita.universita.Mode;

import co.edu.poli.ces3.universita.universita.dto.DtoStudents;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Students extends Conexion implements CRUD{

    private int id;
    private String nombre;
    private String documento;

    public Students(int id, String documento, String nombre ) {
        this.id = id;
        this.nombre = nombre;
        this.documento = documento;
    }

    public Students (String documento){
        this.documento = documento;
    }
    public Students(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String toString() {
        return "El estudiante se llama: " +
                this.nombre + " su documento es: " +
                this.documento;
    }

    @Override
    public Students create(DtoStudents students) throws SQLException {

        Connection conn = this.getConexion();
        if (conn != null){
            String sql = "INSERT INTO user (documento, nombre) VALUES (?, ?)";
            this.documento = students.getDocumento();
            this.nombre = students.getNombre();
            try{
                PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                stmt.setString(1,students.getDocumento());
                stmt.setString(2,students.getNombre());
                stmt.executeUpdate();
                ResultSet rs = stmt.getGeneratedKeys();
                rs.next();
                this.id = rs.getInt(1);
            }catch (SQLException e){
                throw new RuntimeException(e);
            }finally {
                conn.close();
            }
        }
        return this;
    }

    @Override
    public ArrayList<DtoStudents> all() throws SQLException {

        ArrayList<DtoStudents> list = new ArrayList<>();
        String sql = "SELECT id, documento, nombre FROM user ORDER BY id";
        Connection conn = this.getConexion();
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                list.add(new DtoStudents(
                   rs.getInt("id"),
                   rs.getString("documento"),
                   rs.getString( "nombre")
                ));
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            conn.close();
        }

    }

    @Override
    public Students findById(int id) throws SQLException {
        Students stuId = null;
        String sql = "SELECT * FROM user WHERE id=?";
        Connection conn = this.getConexion();
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1,id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                stuId = new Students(
                        rs.getInt("id"),
                        rs.getString("documento"),
                        rs.getString( "nombre"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            conn.close();
        }
        return stuId;
    }

    @Override
    public Students update(DtoStudents students) throws SQLException {

        String sql = "UPDATE user SET documento=?, nombre=? WHERE id = ? ";
        Connection conn = this.getConexion();
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1,students.getDocumento());
            stmt.setString(2,students.getNombre());
            stmt.setInt(3,students.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            conn.close();
        }
        return this;
    }

    @Override
    public int delete(int id) throws SQLException {

        String sql = "DELETE FROM user  WHERE id = ? ";
        Connection conn = this.getConexion();
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1,id);
            stmt.executeUpdate();
            return stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            conn.close();
        }

    }
}
