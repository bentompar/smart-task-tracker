package com.BenjaminPark.dto;

import com.BenjaminPark.model.Status;

public class TaskUpdateRequest {
    private String title;
    private String description;
    private Status status;

    public String getTitle() {return this.title;}

    public void setTitle(String title) {this.title = title;}

    public String getDescription() {return this.description;}

    public void setDescription(String description) {this.description = description;}

    public Status getStatus() {return this.status;}

    public void setStatus(Status status) {this.status = status;}
}
