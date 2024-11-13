/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adrover.trainingtime.dtos;

/**
 *
 * @author nuria
 */
public class Exercise {

    private int id;
    private String nomExercici;
    private String descripcio;
    private String demoFoto;

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomExercici() {
        return nomExercici;
    }

    public void setNomExercici(String nomExercici) {
        this.nomExercici = nomExercici;
    }

    public String getDescripcio() {
        return descripcio;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    public String getDemoFoto() {
        return demoFoto;
    }

    public void setDemoFoto(String demoFoto) {
        this.demoFoto = demoFoto;
    }

    // Opcional: Método toString para mostrar información del ejercicio
    @Override
    public String toString() {
        return "Exercise{"
                + "id=" + id
                + ", nameExercici='" + nomExercici + '\''
                + ", description='" + descripcio + '\''
                + ", demoFoto='" + demoFoto + '\''
                + '}';
    }
}
