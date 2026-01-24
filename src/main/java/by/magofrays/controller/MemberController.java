package by.magofrays.controller;

import by.magofrays.dto.*;
import by.magofrays.exception.BusinessException;
import by.magofrays.exception.ErrorCode;
import by.magofrays.security.MemberPrincipal;
import by.magofrays.service.MemberService;
import by.magofrays.validation.RegistrationGroup;
import jakarta.validation.groups.Default;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    @GetMapping("/")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<ReadMemberDto> findByUsername(@AuthenticationPrincipal MemberPrincipal principal){
        return ResponseEntity.ok(memberService.findByUsername(principal.getUsername())
                .orElseThrow(() ->
                        new BusinessException(ErrorCode.NOT_FOUND, "Пользователь с никнеймом: "+ principal.getUsername() +" не существует.")));
    }

    @GetMapping("/families")
    @PreAuthorize("hasAuthority('USER')")
    public List<ReadFamilyMemberDto> getFamilies(@AuthenticationPrincipal MemberPrincipal principal){
        return memberService.getFamilyMembers(principal.getId());
    }

    @GetMapping("/hasFamily")
    @PreAuthorize("hasAuthority('USER')")
    public boolean hasFamily(@AuthenticationPrincipal MemberPrincipal principal){
        return memberService.memberHasFamily(principal.getId());
    }

}
