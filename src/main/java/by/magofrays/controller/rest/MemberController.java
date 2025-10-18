package by.magofrays.controller.rest;

import by.magofrays.dto.PersonalInfoDto;
import by.magofrays.dto.ReadMemberDto;
import by.magofrays.dto.SmallMemberDto;
import by.magofrays.exception.BusinessException;
import by.magofrays.exception.ErrorCode;
import by.magofrays.service.MemberService;
import by.magofrays.service.PersonalInfoService;
import by.magofrays.validation.LoginGroup;
import by.magofrays.validation.RegistrationGroup;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.groups.Default;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/create")
    public ResponseEntity<ReadMemberDto> create(
            @ModelAttribute @Validated({RegistrationGroup.class, Default.class}) SmallMemberDto createMemberDto,
                                 @ModelAttribute @Validated PersonalInfoDto personalInfoDto) {
        var member = memberService.createMember(createMemberDto, personalInfoDto);
        return ResponseEntity.ok(member);
    }

    @GetMapping("/{username}")
    public ResponseEntity<ReadMemberDto> findByUsername(@ModelAttribute("username") String username){
        return ResponseEntity.ok(memberService.findByUsername(username).orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND, "NOT FOUND")));
    }


}
