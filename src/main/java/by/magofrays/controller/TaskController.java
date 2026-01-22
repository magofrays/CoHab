package by.magofrays.controller;

import by.magofrays.dto.CreateUpdateTaskDto;
import by.magofrays.dto.ReadTaskDto;
import by.magofrays.security.MemberPrincipal;
import by.magofrays.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @PreAuthorize("""
        hasAuthority('USER') && hasPermission(#createTaskDto.familyId, 'family', 'CREATE_TASK')
        && (#createTaskDto.issuedTo == null || hasPermission(#createTaskDto.familyId, 'family', 'ASSIGN_TASK'))
    """)
    public ReadTaskDto createTask(@AuthenticationPrincipal MemberPrincipal principal,
                           @RequestBody CreateUpdateTaskDto createTaskDto){
        createTaskDto.setCreatedBy(principal.getId());
        return taskService.createTask(createTaskDto);
    }

    @PutMapping("/update")
    @PreAuthorize(
            """
                hasAuthority('USER') && (hasPermission(#updateTaskDto.familyId, 'family', 'UPDATE_TASK')
                || principal.id.equals(updateTaskDto.createdBy))
                && (#updateTaskDto.issuedTo == null || hasPermission(#updateTaskDto.familyId, 'family', 'ASSIGN_TASK'))
            """
    )
    public ReadTaskDto changeTask(@AuthenticationPrincipal MemberPrincipal principal,
                                  @RequestBody CreateUpdateTaskDto updateTaskDto) {
        return taskService.updateTask(updateTaskDto);
    }
}
