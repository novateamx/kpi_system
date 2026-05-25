package uz.java.kpisystem.service;

import uz.java.kpisystem.dto.task.TaskRequest;

public interface ITaskService {

    Long create(TaskRequest request);
}
