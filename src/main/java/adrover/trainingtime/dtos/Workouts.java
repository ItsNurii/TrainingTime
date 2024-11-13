/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adrover.trainingtime.dtos;


/**
 *
 * @author nuria
 */
public class Workouts {

    private int id;
    private int userId;
    private String forDate;
    private String comments;

    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getForDate() {
        return forDate;
    }

    public void setForDate(String forDate) {
        this.forDate = forDate;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "Workouts{"
                + "id=" + id
                + ", userId=" + userId
                + ", forDate='" + forDate + '\''
                + ", comments='" + comments + '\''
                + '}';
    }
}
