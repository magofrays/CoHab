package by.magofrays.controller;
import by.magofrays.dto.CreateFamilyDto;
import by.magofrays.dto.ReadFamilyDto;
import by.magofrays.dto.ReadFamilyMemberDto;
import by.magofrays.dto.ReadMemberDto;
import by.magofrays.security.MemberPrincipal;
import by.magofrays.service.FamilyService;
import lombok.RequiredArgsConstructor;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/family")
public class FamilyController {
    private final FamilyService familyService;

    @GetMapping("{name}/members")
    @PreAuthorize("hasAuthority('USER') && hasPermission(#name, 'family', SHOW_MEMBERS)")
    public List<ReadFamilyMemberDto> findAllFamilyMembers(@AuthenticationPrincipal MemberPrincipal principal){
        return familyService.getFamilyMembersByMemberId(principal.getId());

    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('USER')")
    public ReadFamilyDto createFamily(@AuthenticationPrincipal MemberPrincipal principal,
                                      @Validated @RequestBody CreateFamilyDto createFamilyDto){
        createFamilyDto.setCreatedBy(principal.getId());
        return familyService.createFamily(createFamilyDto);
    }
}
