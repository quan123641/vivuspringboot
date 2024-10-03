package com.congdinh.vivuspringboot.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.congdinh.vivuspringboot.entities.Member;
import com.congdinh.vivuspringboot.services.ContentService;
import com.congdinh.vivuspringboot.services.MemberService;

@Controller
@SessionAttributes("member") 
public class controllerMember {

    @Autowired
    private MemberService memberService;
    @Autowired
    private ContentService contentService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String login() {
        return "login";
    }



//    @PostMapping("/login")
//    public String login(@RequestParam("email") String email,
//            @RequestParam("password") String password,
//            RedirectAttributes redirectAttributes, Model model) {
//        if (email.isEmpty() || password.isEmpty()) {
//            redirectAttributes.addFlashAttribute("message", "Please enter email and password.");
//            return "redirect:/login";
//        }
//        // Kiểm tra thông tin đăng nhập
//        Member member = memberService.login(email, password);
//        if (member == null) {
//            redirectAttributes.addFlashAttribute("message", "Invalid email or password.");
//            return "redirect:/login"; // Redirect để tránh lặp lại thông tin
//        }
//
//        System.out.println("Login successful");
//        System.out.println(member.getEmail() + "aaa ");
//
//        redirectAttributes.addFlashAttribute("message", "Login success");
//        model.addAttribute("member", member);
//        return "redirect:/edit-profile";
//    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("member", new Member());
        return "register";
    }

    @PostMapping("/register")
    public String register(
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("confirmpassword") String confirmpassword,
            @RequestParam("username") String username,
            Model model,
            RedirectAttributes redirectAttributes) {
        if (email.isEmpty() || password.isEmpty() || confirmpassword.isEmpty() || username.isEmpty()) {
            model.addAttribute("message", "Please fill in all fields");
            return "register";
        }
        String[] nameParts = username.split(" ");
        String firstname = nameParts[0]; // Giả định rằng firstname là phần đầu tiên
        String lastname = nameParts.length > 1 ? nameParts[nameParts.length - 1] : "";

        // Kiểm tra mật khẩu có khớp hay không
        if (!password.equals(confirmpassword)) {
            model.addAttribute("message", "Password not match");
            return "register";
        }

        System.out.println(email + " " + password + " " + confirmpassword + " " + username);
        // Kiểm tra xem email đã tồn tại chưa
        if (memberService.existsByEmail(email)) {
            model.addAttribute("message", "Email already exists");
            return "register";
        }

        Member member = new Member();
        member.setFirstname(firstname); // Hoặc để null nếu cho phép
        member.setLastname(lastname); // Hoặc để null nếu cho phép
        member.setEmail(email);
        member.setPassword(passwordEncoder.encode(password));
        member.setPhone(null);
        member.setDescription(null);
        memberService.save(member);

        // Đăng ký thành công
        redirectAttributes.addFlashAttribute("message", "Register success");
        return "/login";
        // Logic lưu người dùng ở đây

    }

    @GetMapping("/edit-profile")
    public String showEditProfileForm(HttpSession httpSession, Model model) {

        Member member = (Member) httpSession.getAttribute("member");
        if (member == null) {
            System.out.println("view edit" );
            return "redirect:/login"; // Nếu không có thông tin người dùng, chuyển hướng đến trang đăng nhập
        }
        model.addAttribute("member", member); // Thêm thông tin người dùng vào model
        return "edit-profile"; // Trả về trang chỉnh sửa
    }

    @PostMapping("/edit-profile")
    public String editProfileForm(@ModelAttribute("member") Member member, RedirectAttributes redirectAttributes, Model model) {
        System.out.println("Edit profile successfully");
        System.out.println(member.getId()+"aaaaaaaaaaaaaaaaa");
        memberService.update(member);
        model.addAttribute("member", member);
        var email = member.getEmail();
        redirectAttributes.addFlashAttribute("email", "email");
        redirectAttributes.addFlashAttribute("message", "Profile updated successfully");
        return "redirect:/edit-profile";
    }
}
