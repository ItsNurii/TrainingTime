/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adrover.trainingtime.dataaccess;

import adrover.trainingtime.dtos.Exercise;
import adrover.trainingtime.dtos.ExerciseWorkout;
import adrover.trainingtime.dtos.Usuari;
import adrover.trainingtime.dtos.Workouts;
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

    public Usuari getUsuari(String Email) {
        Usuari user = null;
        String sql = "SELECT * FROM Usuaris WHERE Email=?";

        Connection connection = getConnection();
        try {
            PreparedStatement selectStatement = connection.prepareStatement(sql);
            selectStatement.setString(1, Email);
            ResultSet resultSet = selectStatement.executeQuery();
            while (resultSet.next()) {
                user = new Usuari();
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

    public ArrayList<Usuari> getUsuaris() {
        ArrayList<Usuari> usuaris = new ArrayList<>();
        String sql = "SELECT * FROM Usuaris";

        Connection connection = getConnection();
        try {
            PreparedStatement selectStatement = connection.prepareStatement(sql);
            ResultSet resultSet = selectStatement.executeQuery();
            while (resultSet.next()) {
                Usuari user = new Usuari();
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

    public int registerUser(Usuari u) {
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

    // Método actualizado para obtener los usuarios entrenados por un instructor
    public ArrayList<Usuari> getUsuarisByInstructor(int instructorId) {
        ArrayList<Usuari> usuaris = new ArrayList<>();
        String sql = "SELECT * FROM Usuaris WHERE Instructor = 0 AND AssignedInstructor = ?";

        Connection connection = getConnection();
        try {
            PreparedStatement selectStatement = connection.prepareStatement(sql);
            selectStatement.setInt(1, instructorId);  // Filtra por el ID del instructor
            ResultSet resultSet = selectStatement.executeQuery();

            while (resultSet.next()) {
                Usuari user = new Usuari();
                user.setId(resultSet.getInt("Id"));
                user.setNom(resultSet.getString("Nom"));
                user.setEmail(resultSet.getString("Email"));
                user.setPasswordHash(resultSet.getString("PasswordHash"));
                //user.setFoto(resultSet.getBytes("Foto"));
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

    public ArrayList<Workouts> getWorkoutsPerUser(int userId) {
        ArrayList<Workouts> workout = new ArrayList<>();
        String sql = "SELECT Id, UserId, ForDate, Comments FROM Workouts WHERE UserId = ?";

        try (
                Connection connection = getConnection(); PreparedStatement selectStatement = connection.prepareStatement(sql)) {
            selectStatement.setInt(1, userId);
            ResultSet resultSet = selectStatement.executeQuery();
            while (resultSet.next()) {
                Workouts workouts = new Workouts();
                workouts.setUserId(resultSet.getInt("UserId"));
                workouts.setId(resultSet.getInt("Id"));
                workouts.setForDate(resultSet.getString("ForDate"));
                workouts.setComments(resultSet.getString("Comments"));
                workout.add(workouts);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
        return workout;
    }

    public ArrayList<Exercise> getExercicisPerWorkout(int workoutId) {
        ArrayList<Exercise> exercicis = new ArrayList<>();
        String sql = "SELECT ExercicisWorkouts.IdExercici,"
                + " Exercicis.NomExercici, Exercicis.Descripcio, Exercicis.DemoFoto"
                + " FROM ExercicisWorkouts INNER JOIN Exercicis ON ExercicisWorkouts.IdExercici=Exercicis.Id"
                + " WHERE ExercicisWorkouts.IdWorkout=?";
        try (Connection connection = getConnection(); PreparedStatement selectStatement = connection.prepareStatement(sql);) {
            selectStatement.setInt(1, workoutId);
            ResultSet resultSet = selectStatement.executeQuery();

            while (resultSet.next()) {
                Exercise exercici = new Exercise();
                exercici.setId(resultSet.getInt("IdExercici"));
                exercici.setNomExercici(resultSet.getString("NomExercici"));
                exercici.setDescripcio(resultSet.getString("Descripcio"));
                exercici.setDemoFoto(resultSet.getString("DemoFoto"));

                exercicis.add(exercici);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exercicis;
    }

    public ArrayList<Exercise> getAllExercicis() {
        ArrayList<Exercise> exercicis = new ArrayList<>();
        String sql = "SELECT * FROM Exercicis";
        try (Connection connection = getConnection(); PreparedStatement selectStatement = connection.prepareStatement(sql);) {

            ResultSet resultSet = selectStatement.executeQuery();

            while (resultSet.next()) {
                Exercise exercici = new Exercise();
                exercici.setId(resultSet.getInt("Id"));
                exercici.setNomExercici(resultSet.getString("NomExercici"));
                exercici.setDescripcio(resultSet.getString("Descripcio"));

                exercicis.add(exercici);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exercicis;
    }

    public void insertToWorkoutTable(Workouts w) {
        String sql = "INSERT INTO dbo.Workouts (ForDate, UserId, Comments)"
                + " VALUES (?,?,?)";
        try (Connection conn = getConnection(); PreparedStatement insertStatement = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);) {
            insertStatement.setString(1, w.getForDate());
            insertStatement.setInt(2, w.getUserId());
            insertStatement.setString(3, w.getComments());

            int affectedRows = insertStatement.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = insertStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        w.setId(generatedKeys.getInt(1));  // Asignamos el ID generado al objeto Workout
                    }
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void insertToExercice(Exercise exercise) {
        String sql = "INSERT INTO Exercicis (NomExercici, Descripcio) VALUES (?, ?)";
        Connection connection = getConnection();

        try {
            PreparedStatement insertStatement = connection.prepareStatement(sql);
            insertStatement.setString(1, exercise.getNomExercici());
            insertStatement.setString(2, exercise.getDescripcio());

            int rowsAffected = insertStatement.executeUpdate();
            insertStatement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void getUpdateModifiedExercici(int id, String newName, String newDescripcio) {
        String sql = "UPDATE Exercicis SET NomExercici = ?, Descripcio = ? WHERE Id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newName);
            stmt.setString(2, newDescripcio);
            stmt.setInt(3, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getDeleteEjercicio(int idEjercicio) {
        // Primero, eliminamos los registros relacionados en ExercicisWorkouts
        String deleteReferencesSql = "DELETE FROM ExercicisWorkouts WHERE IdExercici = ?";
        String deleteEjercicioSql = "DELETE FROM Exercicis WHERE Id = ?";

        try (Connection conn = getConnection()) {
            // Desactivar auto-commit para realizar una transacción
            conn.setAutoCommit(false);

            try (PreparedStatement stmt1 = conn.prepareStatement(deleteReferencesSql); PreparedStatement stmt2 = conn.prepareStatement(deleteEjercicioSql)) {

                // Eliminar referencias en ExercicisWorkouts
                stmt1.setInt(1, idEjercicio);
                stmt1.executeUpdate();

                // Ahora eliminar el ejercicio de Exercicis
                stmt2.setInt(1, idEjercicio);
                stmt2.executeUpdate();

                // Confirmar la transacción
                conn.commit();
            } catch (SQLException e) {
                // Revertir la transacción en caso de error
                conn.rollback();
                throw e;  // Relanzar la excepción para manejarla en otro lugar
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertExToWork(ExerciseWorkout exercicisWorkout) {
        //Primero, verificar si los IDs existen en sus respectivas tablas
        if (!existeId("Exercicis", exercicisWorkout.getIdExerciseWork()) || !existeId("Workouts", exercicisWorkout.getIdWorkout())) {
            System.out.println("Error: Uno de los IDs no existe en la base de datos.");
            return;  // No continuar con la inserción
        }

        // Si ambos IDs existen, proceder con la inserción en la tabla ExercicisWorkouts
        String sql = "INSERT INTO ExercicisWorkouts (IdExercici, IdWorkout) VALUES (?, ?)";

        try (Connection connection = getConnection(); PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, exercicisWorkout.getIdExerciseWork());  // Set exerciciId
            pstmt.setInt(2, exercicisWorkout.getIdWorkout());  // Set workoutId
            pstmt.executeUpdate();  // Ejecutar la inserción
        } catch (SQLException ex) {
            Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, "Error al insertar la relación entre ejercicios y entrenamientos", ex);
        }
    }

    private boolean existeId(String tableName, int id) {
        String sql = "SELECT COUNT(*) FROM " + tableName + " WHERE Id = ?";
        try (Connection connection = getConnection(); PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;  // Si hay al menos una fila, significa que el ID existe
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, "Error al verificar existencia del ID en la tabla " + tableName, ex);
        }
        return false;
    }

}
