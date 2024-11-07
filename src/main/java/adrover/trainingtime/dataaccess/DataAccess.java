/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adrover.trainingtime.dataaccess;

import adrover.trainingtime.dtos.Usuaris;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nuria
 */
public class DataAccess {

    private Connection getConnection() {
        Connection connection = null;
        String connectionString = "jdbc:sqlserver://localhost;database=simulabdb;user=sa;" + "password=152110;encrypt=false;";
        try {
            connection = DriverManager.getConnection(connectionString);
        } catch (SQLException ex) {
            Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
        }

        return connection;
    }

    // Método actualizado para obtener los usuarios entrenados por un instructor
    public ArrayList<Usuaris> getUsuarisByInstructor(int instructorId) {
        ArrayList<Usuaris> usuaris = new ArrayList<>();
        String sql = "SELECT * FROM Usuaris WHERE Instructor = 1 AND InstructorId = ?";

        Connection connection = getConnection();
        try {
            PreparedStatement selectStatement = connection.prepareStatement(sql);
            selectStatement.setInt(1, instructorId);  // Filtra por el ID del instructor
            ResultSet resultSet = selectStatement.executeQuery();

            while (resultSet.next()) {
                Usuaris user = new Usuaris();
                user.setId(resultSet.getInt("Id"));
                user.setNom(resultSet.getString("Nom"));
                user.setEmail(resultSet.getString("Email"));
                user.setPasswordHash(resultSet.getString("PasswordHash"));
                user.setFoto(resultSet.getBytes("Foto"));
                user.setInstructor(resultSet.getBoolean("Instructor"));
                usuaris.add(user);  // Añadir el usuario a la lista
            }
            selectStatement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
        }

        return usuaris;
    }

    public Usuaris getUsuari(String Email) {
        Usuaris user = null;
        String sql = "SELECT * FROM Usuaris WHERE Email=?";

        Connection connection = getConnection();
        try {
            PreparedStatement selectStatement = connection.prepareStatement(sql);
            selectStatement.setString(1, Email);
            ResultSet resultSet = selectStatement.executeQuery();
            while (resultSet.next()) {
                user = new Usuaris();
                user.setId(resultSet.getInt("Id"));
                user.setNom(resultSet.getString("Nom"));
                user.setEmail(resultSet.getString("Email"));
                user.setPasswordHash(resultSet.getString("PasswordHash"));
                //user.setFoto(resultSet.getBytes("Foto"));
                user.setInstructor(resultSet.getBoolean("Instructor"));
            }

            selectStatement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
        }

        return user;
    }

    public int registerUser(Usuaris u) {
        int newUserId = 0;
        Connection connection = getConnection();
        String sql = "INSERT INTO Usuaris(Nom, Email, PasswordHash, Instructor)" + "VALUES(?,?,?,?)";

        try {
            PreparedStatement insertStatement = connection.prepareStatement(sql);
            insertStatement.setString(1, u.getNom());
            insertStatement.setString(2, u.getEmail());
            insertStatement.setString(3, u.getPasswordHash());
            insertStatement.setBoolean(4, u.isInstructor());
            newUserId = insertStatement.executeUpdate();

            insertStatement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
        }

        return newUserId;

    }

    public ArrayList<Usuaris> getUsuaris() {
        ArrayList<Usuaris> usuaris = new ArrayList<>();
        String sql = "SELECT * FROM Usuaris";

        Connection connection = getConnection();
        try {
            PreparedStatement selectStatement = connection.prepareStatement(sql);
            ResultSet resultSet = selectStatement.executeQuery();
            while (resultSet.next()) {
                Usuaris user = new Usuaris();
                user.setId(resultSet.getInt("Id"));
                user.setNom(resultSet.getString("Nom"));
                user.setEmail(resultSet.getString("Email"));
                user.setPasswordHash(resultSet.getString("PasswordHash"));
                user.setFoto(resultSet.getBytes("Foto"));
                user.setInstructor(resultSet.getBoolean("Instructor"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
        }

        return usuaris;

    }

    public ArrayList<String> getEntrenamientos(int instructorId) {
        ArrayList<String> entrenamientos = new ArrayList<>();
        // Aquí iría la lógica para obtener los entrenamientos asociados con el instructor
        // Por ejemplo, ejecutar una consulta SQL para obtener los entrenamientos
        String sql = "SELECT nombre FROM Entrenamientos WHERE InstructorId = ?";
        try (Connection connection = getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, instructorId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                entrenamientos.add(rs.getString("nombre"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
        return entrenamientos;
    }

}
