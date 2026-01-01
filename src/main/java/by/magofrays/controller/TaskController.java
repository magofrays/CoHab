package by.magofrays.controller;

import by.magofrays.dto.CreateTaskDto;
import by.magofrays.dto.ReadTaskDto;
import by.magofrays.security.MemberPrincipal;
import by.magofrays.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @GetMapping()
    public List<ReadTaskDto> getTasksByMember(@AuthenticationPrincipal MemberPrincipal principal){
        return taskService.getTasksByIssuedToId(principal.getId());
    }

    @PostMapping("/create")
    public UUID createTask(@AuthenticationPrincipal MemberPrincipal principal,
                           CreateTaskDto createTaskDto){
        createTaskDto.setCreatedBy(principal.getId());
        return taskService.createTask(createTaskDto);
    }
}
