package by.magofrays.controller;

import by.magofrays.dto.PersonalInfoDto;
import by.magofrays.dto.ReadMemberDto;
import by.magofrays.dto.SmallMemberDto;
import by.magofrays.exception.BusinessException;
import by.magofrays.exception.ErrorCode;
import by.magofrays.service.MemberService;
import by.magofrays.validation.RegistrationGroup;
import jakarta.validation.groups.Default;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("find/{username}")
    public ResponseEntity<ReadMemberDto> findByUsername(@PathVariable String username){
        return ResponseEntity.ok(memberService.findByUsername(username).orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND, "NOT FOUND")));
    }


}
