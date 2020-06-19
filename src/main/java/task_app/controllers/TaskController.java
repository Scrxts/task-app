package task_app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import task_app.dao.TaskDao;
import task_app.model.Task;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class TaskController {


    private TaskDao taskDao;

    @Autowired
    public TaskController(TaskDao taskDao) {
        this.taskDao = taskDao;
    }


    @PostMapping("/saveTask")
    ResponseEntity<Task> saveTask(@RequestBody Task task) {
        Task result = taskDao.save(task);
        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
    }

    @GetMapping("/readTask")
    ResponseEntity<List<Task>> readAllTasks(){
        return ResponseEntity.ok(taskDao.findAll());
    }

    @PutMapping("/updateTask/{id}")
    ResponseEntity<Task> updateTask(@PathVariable(value = "id") int taskId, @RequestBody Task taskDetails) throws ResourceNotFoundException {
        Task task = taskDao.findById(taskId).orElseThrow(() -> new ResourceNotFoundException("Task not found with this id :: " + taskId));
        task.setTitle(taskDetails.getTitle());
        task.setDescription(taskDetails.getDescription());
        task.setLocalDateTime(taskDetails.getLocalDateTime());
        final Task updatedTask = taskDao.save(task);
        return ResponseEntity.ok(updatedTask);
    }
    @DeleteMapping("/deleteTask/{id}")
    public Map<String,Boolean> deleteTask(@PathVariable(value = "id") int taskId) throws ResourceNotFoundException{
        Task task = taskDao.findById(taskId).orElseThrow(() -> new ResourceNotFoundException("Task not found with this id :: " + taskId));
        taskDao.delete(task);
        Map<String,Boolean> response = new HashMap<>();
        response.put("deleted",Boolean.TRUE);
        return response;
    }
}