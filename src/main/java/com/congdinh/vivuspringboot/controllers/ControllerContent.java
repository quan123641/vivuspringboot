package com.congdinh.vivuspringboot.controllers;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.congdinh.vivuspringboot.entities.Content;
import com.congdinh.vivuspringboot.entities.Member;
import com.congdinh.vivuspringboot.services.ContentService;
import com.congdinh.vivuspringboot.services.MemberService;

import jakarta.validation.Valid;

@SessionAttributes("member")
@Controller
public class ControllerContent {

    private final ContentService contentService;
    private final MemberService memberService;

    public ControllerContent(ContentService contentService, MemberService memberService) {
        this.contentService = contentService;
        this.memberService = memberService;
    }

    @GetMapping("/add-content/{memberId}")
    public String showEditProfileForm(@Valid Model model, @PathVariable("memberId") Integer memberId) {
        Content content = new Content();
        Member member = memberService.getById(memberId);
        content.setMember(member);
        model.addAttribute("contents", content);
        return "add-content";
    }

    @PostMapping("/add-content/{memberId}")
    public String addContent(@Valid @ModelAttribute("contents") Content content,
            BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes
            ,@PathVariable("memberId") Integer memberId) {

        // if (content.getDescription().length() < 10 || content.getDescription().length() > 5
        //         || content.getTitle().length() < 10
        //         || content.getTitle().length() > 5 || content.getDescription().isEmpty()
        //         || content.getTitle().isEmpty()) {
        //     model.addAttribute("message", "Description or title is not valid");
        //     model.addAttribute("member", member);
        //     return "add-content";
        // }

        if (bindingResult.hasErrors()) {
            return "add-content";
        }

        Member member = memberService.getById(memberId);
        content.setMember(member);
        var result = contentService.save(content);
        if (result == null) {
            model.addAttribute("message", "Content is existed");
            return "add-content";
        }
        redirectAttributes.addFlashAttribute("message", "add successfully");
        return "redirect:/view-content";
    }

    @GetMapping("/view-content")
    public String index(
            @RequestParam(defaultValue = "createLocalDate") String sort, // Sắp xếp theo thời gian tạo
            @RequestParam(defaultValue = "asc") String order, // Thứ tự sắp xếp (tăng dần/giảm dần)
            Model model, @ModelAttribute("member") Member member) {
        System.out.println("member: " + member);
        Sort sorting = Sort.by(Sort.Direction.fromString(order), sort);
        List<Content> contents = contentService.findAll(sorting);  // Lấy danh sách Content đã sắp xếp

        // model.addAttribute("member", member);
        model.addAttribute("contents", contents);  // Đưa danh sách vào model
        return "view-content";  // Tên template của bạn
    }

    @GetMapping("/view-content/delete/{id}")
    public String viewContent(@PathVariable("id") Integer id) {
        contentService.delete(id);
        return "redirect:/view-content";
    }

//    @PostMapping("/api/edit/{id}")
//    public String edit(@PathVariable("id") Integer id, @RequestBody Content data, Model model) {
//        data.setId(id);
//        contentService.save(data);
//        List<Content> contents = contentService.findAll(Sort.by("createLocalDate"));
//        model.addAttribute("contents", contents);
//        return "view-content-table.html";
//    }
  @PostMapping("/api/edit/{id}")
public ResponseEntity<?> edit(@PathVariable("id") Integer id, @RequestBody Content data) {
    // Lấy content từ DB
    var content = contentService.getById(id);
    
    // Kiểm tra content có tồn tại hay không
    if (content == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Content not found");
    }
    
    // Cập nhật các trường từ data
    content.setTitle(data.getTitle());
    content.setBrief(data.getBrief());
    content.setDescription(data.getDescription());
    
    // Không cần cập nhật createLocalDate vì bạn muốn giữ nguyên giá trị này từ lần tạo đầu tiên
    // content.setCreateLocalDate(content.getCreateLocalDate());  // giữ nguyên
    
    // Nếu bạn không thay đổi Member, giữ nguyên member hiện có
    content.setMember(content.getMember());
    
    // Lưu đối tượng content đã được cập nhật
    contentService.save(content);
    
    // Trả về danh sách content sau khi cập nhật
    List<Content> contents = contentService.findAll(Sort.by("createLocalDate"));
    return ResponseEntity.ok(contents);
}


}
