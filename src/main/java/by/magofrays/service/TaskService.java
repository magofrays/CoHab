package by.magofrays.service;

import by.magofrays.dto.CreateUpdateTaskDto;
import by.magofrays.dto.DeleteTaskDto;
import by.magofrays.dto.MarkCheckTaskDto;
import by.magofrays.dto.ReadTaskDto;
import by.magofrays.exception.BusinessException;
import by.magofrays.exception.ErrorCode;
import by.magofrays.mapper.MemberMapper;
import by.magofrays.mapper.TaskMapper;
import by.magofrays.repository.FamilyMemberRepository;
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
    private final FamilyMemberRepository familyMemberRepository;
    private final TaskMapper taskMapper;


    public Boolean isCreatedBy(UUID memberId, UUID taskId){
        return taskRepository.isCreatedBy(memberId, taskId);
    }

    public List<ReadTaskDto> getTasksByIssuedToId(UUID id){
        return taskRepository.getTasksByIssuedToId(id).stream().map(taskMapper::toDto).toList();
    }

    public ReadTaskDto createTask(CreateUpdateTaskDto taskDto){
        var createdBy = familyMemberRepository.findByMember_IdAndFamily_Id(taskDto.getCreatedBy(), taskDto.getFamilyId())
                .orElseThrow(() ->
                        new BusinessException(ErrorCode.NOT_FOUND,
                                "Пользователь с id: "+ taskDto.getCreatedBy() +" не состоит в семье: " + taskDto.getFamilyId()));

        var task = taskMapper.toEntity(taskDto);
        if(taskDto.getIssuedTo() != null){
            var issuedTo = createdBy
                    .getFamily()
                    .getMembers()
                    .stream()
                    .filter(member ->
                            member.getId().equals(taskDto.getIssuedTo()))
                    .findFirst()
                    .orElseThrow(() ->
                            new BusinessException(ErrorCode.NOT_FOUND,
                                    "Пользователь с id: "+ taskDto.getIssuedTo() +" не состоит в семье: " + taskDto.getFamilyId()));
            task.setIssuedTo(issuedTo);
        }
        else{
            task.setIssuedTo(createdBy);
        }
        task.setCreatedBy(createdBy);
        task.setId(UUID.randomUUID());
        task.setCreatedDate(LocalDate.now());
        task.setIsChecked(false);
        task.setIsMarked(false);
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
            task.setIssuedTo(familyMemberRepository
                    .findById(taskDto.getIssuedTo())
                    .orElseThrow(() ->
                            new BusinessException(ErrorCode.NOT_FOUND, "Пользователь с id: "+ taskDto.getCreatedBy() +" не существует!")));
        }
        return taskMapper.toDto(taskRepository.save(task));
    }

    public void markOrCheckTask(MarkCheckTaskDto markTaskDto, UUID memberId){
        var task = taskRepository.findById(markTaskDto.getTaskId())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND, "Задачи с id: " + markTaskDto.getTaskId() + " не существует!"));
        if(markTaskDto.getTaskMarked() != null){
            if(!task.getIssuedTo().getMember().getId().equals(memberId)){

                throw new BusinessException(ErrorCode.BAD_REQUEST,
                        "На пользователя с id: " + memberId + " задача с id: " + markTaskDto.getTaskId() + " не назначена!");
            }
            else{
                if(task.getIsChecked() != null && task.getIsChecked()){
                    throw new BusinessException(ErrorCode.BAD_REQUEST,
                            "Задача с id: " + markTaskDto.getTaskId() + " уже проверена!");
                }
                task.setIsMarked(markTaskDto.getTaskMarked());
            }
        }
        else{
            if(markTaskDto.getTaskChecked() != null && !task.getCreatedBy().getMember().getId().equals(memberId)){
                throw new BusinessException(ErrorCode.BAD_REQUEST,
                        "Пользователь с id: " + memberId + " не создавал задачу с id: " + markTaskDto.getTaskId() + "!");
            }else{
                if(task.getIsMarked() != null && !task.getIsMarked()){
                    task.setIsMarked(markTaskDto.getTaskChecked());
                }
                task.setIsChecked(markTaskDto.getTaskChecked());
            }
        }
        taskRepository.save(task);
    }

    public List<ReadTaskDto> getFamilyTasks(UUID familyId, UUID memberId){
        if(!familyMemberRepository.memberInFamily(memberId, familyId)){
                throw new BusinessException(ErrorCode.NOT_FOUND,
                        "Пользователь с id: " + memberId + " не состоит в семье, либо семьи с id: " + familyId + "не существует!");
        }
        return taskRepository
                .getTasksByCreatedBy_Family_Id(familyId)
                .stream()
                .map(taskMapper::toDto)
                .toList();
    }

    public void deleteTask(DeleteTaskDto taskDto){
        var task = taskRepository.findById(taskDto.getTaskId()).orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND,
                "Задачи с id: " + taskDto.getTaskId() + " не существует!"));
        taskRepository.delete(task);
    }
}
