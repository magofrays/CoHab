package by.magofrays.controller;
import by.magofrays.dto.CreateFamilyDto;
import by.magofrays.dto.ReadFamilyDto;
import by.magofrays.dto.ReadMemberDto;
import by.magofrays.security.MemberPrincipal;
import by.magofrays.service.FamilyService;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/family")
public class FamilyController {
    private final FamilyService familyService;

    @GetMapping("/members")
    public List<ReadMemberDto> findAllFamilyMembers(@AuthenticationPrincipal MemberPrincipal principal){
        return familyService.getFamilyMembersByMemberId(principal.getId());

    }

    @PostMapping("/create")
    public ReadFamilyDto createFamily(@AuthenticationPrincipal MemberPrincipal principal,
                                      @Validated @RequestBody CreateFamilyDto createFamilyDto){
        createFamilyDto.setCreatedBy(principal.getId());
        return familyService.createFamily(createFamilyDto);
    }
}
