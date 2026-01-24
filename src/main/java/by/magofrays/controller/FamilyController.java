package by.magofrays.controller;
import by.magofrays.dto.*;
import by.magofrays.security.MemberPrincipal;
import by.magofrays.service.FamilyService;
import lombok.RequiredArgsConstructor;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/family")
public class FamilyController {
    private final FamilyService familyService;

    @GetMapping("{familyId}/members")
    @PreAuthorize("hasAuthority('USER') && hasPermission(#familyId, 'family', 'SHOW_MEMBERS')")
    public List<ReadFamilyMemberDto> getFamilyMembers(@PathVariable UUID familyId){
        return familyService.getFamilyMembersByMemberId(familyId);
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('USER')")
    public ReadFamilyDto createFamily(@AuthenticationPrincipal MemberPrincipal principal,
                                      @Validated @RequestBody CreateFamilyDto createFamilyDto){
        createFamilyDto.setCreatedBy(principal.getId());
        return familyService.createFamily(createFamilyDto);
    }

    @PostMapping("/createInvitation")
    @PreAuthorize("hasAuthority('USER') && hasPermission(#request.familyId, 'family', 'CREATE_INVITATION')")
    public String createInvitation(CreateInvitation request){
        return null;
    }

    @PutMapping("/changeName")
    @PreAuthorize("hasAuthority('User') && hasPermission()}")
    public ReadFamilyDto update(CreateFamilyDto createFamilyDto){
        return null;
    }
}
