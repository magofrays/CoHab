package by.magofrays.controller;

import by.magofrays.dto.ReadTaskDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/tasks")
public class TaskController {
    @GetMapping("")
    public List<ReadTaskDto> getAllTasks(){
        return null;
    }
}
