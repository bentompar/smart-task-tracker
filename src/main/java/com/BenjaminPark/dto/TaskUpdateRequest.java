package com.BenjaminPark.dto;

import com.BenjaminPark.model.Status;

public class TaskUpdateRequest {
    private final String title;
    private final String description;
    private final Status status;

    public TaskUpdateRequest(String title, String description, Status status) {
        this.title = title;
        this.description = description;
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Status getStatus() {
        return status;
    }
}

