package by.magofrays.service;

import by.magofrays.dto.CreateTaskDto;
import by.magofrays.dto.ReadTaskDto;
import by.magofrays.mapper.TaskMapper;
import by.magofrays.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public List<ReadTaskDto> getTasksByIssuedToId(UUID id){
        return taskRepository.getTasksByIssuedToId(id).stream().map(taskMapper::toDto).toList();
    }

    public UUID createTask(CreateTaskDto taskDto){
        return null;
    }
}
