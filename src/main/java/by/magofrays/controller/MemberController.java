package by.magofrays.controller;

import by.magofrays.dto.PersonalInfoDto;
import by.magofrays.dto.ReadMemberDto;
import by.magofrays.dto.SmallMemberDto;
import by.magofrays.service.MemberService;
import by.magofrays.service.PersonalInfoService;
import by.magofrays.validation.LoginGroup;
import by.magofrays.validation.RegistrationGroup;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.groups.Default;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    @PostMapping("/create")
    public String create(@ModelAttribute @Validated({RegistrationGroup.class, Default.class}) SmallMemberDto createMemberDto,
                         BindingResult bindingResultMemberInfo,
                         @ModelAttribute @Validated PersonalInfoDto personalInfoDto,
                         BindingResult bindingResultPersonalInfo,
                         RedirectAttributes redirectAttributes) {
        if (bindingResultMemberInfo.hasErrors() || bindingResultPersonalInfo.hasErrors()) {
            redirectAttributes.addFlashAttribute("personalInfo", personalInfoDto);
            redirectAttributes.addFlashAttribute("memberInfo", createMemberDto);
            List<ObjectError> allErrors = new ArrayList<>();
            if (bindingResultMemberInfo.hasErrors()) {
                allErrors.addAll(bindingResultMemberInfo.getAllErrors());
            }
            if (bindingResultPersonalInfo.hasErrors()) {
                allErrors.addAll(bindingResultPersonalInfo.getAllErrors());
            }
            redirectAttributes.addFlashAttribute("errors", allErrors);
            return "redirect:/registration";
        }
        var member = memberService.createMember(createMemberDto, personalInfoDto);
        return "redirect:/member/" + member.getUsername();
    }

    @GetMapping("/{username}")
    public String findByUsername(@PathVariable("username") String username, Model model){
        var member = memberService.findByUsername(username).get(); // fix
        model.addAttribute("member", member);

        return "member/member";
    }


}
