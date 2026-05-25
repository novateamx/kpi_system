package uz.java.kpisystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
    private T data;
    private boolean success;
    private String message;

    public ApiResponse(T data) {
        this.data = data;
        this.success = true;
    }
}
