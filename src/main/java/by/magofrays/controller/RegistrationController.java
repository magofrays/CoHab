package by.magofrays.controller;


import by.magofrays.dto.PersonalInfoDto;
import by.magofrays.dto.SmallMemberDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RegistrationController {
    @GetMapping("/register")
    public String registration(Model model, @ModelAttribute("member") SmallMemberDto createMemberDto,
                               @ModelAttribute("personalInfo") PersonalInfoDto personalInfoDto){
        model.addAttribute("member", createMemberDto);
        model.addAttribute("personalInfo", personalInfoDto);
        return "member/registration";
    }

    @GetMapping("/login")
    public String login(Model model, @ModelAttribute("member") SmallMemberDto loginMemberDto){
        model.addAttribute("member", loginMemberDto);
        return "member/login";
    }

}
