/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adrover.trainingtime.dtos;

/**
 *
 * @author nuria
 */
public class ExerciseWorkout {

    private int id;
    private int idWorkout;
    private int idExerciseWork;

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the idWorkout
     */
    public int getIdWorkout() {
        return idWorkout;
    }

    /**
     * @param idWorkout the idWorkout to set
     */
    public void setIdWorkout(int idWorkout) {
        this.idWorkout = idWorkout;
    }

    /**
     * @return the idExerciseWork
     */
    public int getIdExerciseWork() {
        return idExerciseWork;
    }

    /**
     * @param idExerciseWork the idExerciseWork to set
     */
    public void setIdExerciseWork(int idExerciseWork) {
        this.idExerciseWork = idExerciseWork;
    }
}
