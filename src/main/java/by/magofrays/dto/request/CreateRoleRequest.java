package by.magofrays.dto.request;

import by.magofrays.entity.Access;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record CreateRoleRequest(
        @NotNull
        UUID familyId,
        @NotBlank
        String roleName,
        List<Access> accesses
) {

}
