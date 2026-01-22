package by.magofrays.service;

import by.magofrays.dto.CreateUpdateTaskDto;
import by.magofrays.dto.ReadTaskDto;
import by.magofrays.exception.BusinessException;
import by.magofrays.exception.ErrorCode;
import by.magofrays.mapper.MemberMapper;
import by.magofrays.mapper.TaskMapper;
import by.magofrays.repository.MemberRepository;
import by.magofrays.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final MemberRepository memberRepository;
    private final TaskMapper taskMapper;

    public List<ReadTaskDto> getTasksByIssuedToId(UUID id){
        return taskRepository.getTasksByIssuedToId(id).stream().map(taskMapper::toDto).toList();
    }

    public ReadTaskDto createTask(CreateUpdateTaskDto taskDto){
        var task = taskMapper.toEntity(taskDto);
        if(taskDto.getIssuedTo() != null){
            task.setIssuedTo(memberRepository
                    .findById(taskDto.getIssuedTo())
                    .orElseThrow(() ->
                            new BusinessException(ErrorCode.NOT_FOUND, "Пользователь с id: "+ taskDto.getIssuedTo() +" не существует.")));
        }
        else{
            task.setIssuedTo(memberRepository
                    .findById(taskDto.getCreatedBy())
                    .orElseThrow(() ->
                            new BusinessException(ErrorCode.NOT_FOUND, "Пользователь с id: "+ taskDto.getCreatedBy() +" не существует.")));
        }
        task.setCreatedBy(memberRepository
                .findById(taskDto.getCreatedBy())
                .orElseThrow(() ->
                        new BusinessException(ErrorCode.NOT_FOUND, "Пользователь с id: "+ taskDto.getCreatedBy() +" не существует.")));
        task.setId(UUID.randomUUID());
        task.setCreatedDate(LocalDate.now());
        return taskMapper.toDto(taskRepository.save(task));
    }

    public ReadTaskDto updateTask(CreateUpdateTaskDto taskDto){
        var task = taskRepository.findById(taskDto.getTaskId())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND, "Задачи с id: " + taskDto.getTaskId() + " не существует"));
        if(taskDto.getTaskName() != null){
            task.setTaskName(taskDto.getTaskName());
        }
        if(taskDto.getDescription() != null){
            task.setDescription(taskDto.getDescription());
        }
        if(taskDto.getDueDate() != null){
            task.setDueDate(taskDto.getDueDate());
        }
        if(taskDto.getIssuedTo() != null){
            task.setIssuedTo(memberRepository
                    .findById(taskDto.getIssuedTo())
                    .orElseThrow(() ->
                            new BusinessException(ErrorCode.NOT_FOUND, "Пользователь с id: "+ taskDto.getCreatedBy() +" не существует.")));
        }
        return taskMapper.toDto(taskRepository.save(task));
    }
}
