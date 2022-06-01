package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createForm(Model model) {
        // html form 객체를 넘겨주어야 함 (일반 페이지에)
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    // BindingResult --> @Valid 어노테이션에서 오류가 있는 경우 원래 whitelabel 페이지로 튕겨버림
    // Validate 다음에 Binding 이 있으면 에러 내용이 BindingResult 에 담겨서 실행할 수 있게 됨.
    public String create(@Valid MemberForm memberForm, BindingResult result) {
        // result에 Validate 한 결과 중 error 객체가 담겨있니? 있으면 이 페이지 렌더링 해줘.
        if (result.hasErrors()) {
            return "members/createMemberForm";
        }

        Address address = new Address(memberForm.getCity(), memberForm.getStreet(), memberForm.getZipcode());
        Member member = new Member();
        member.setName(memberForm.getName());
        member.setAddress(address);

        memberService.join(member);
        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);

        // API 를 만들 때는 이유를 불문하고 -- 절대 Entity 를 넘기면 안된다 --
        // API 라는 것은 Spec
        // Member Entity 를 반환한다고 했을 때, Member Entity 를 변경해버리면 API Spec이 그대로 변해버림
        // 그리고 필요없는, 민감한 정보들도 같이 반환될 수 있음.
        // Template engine 의 경우 어차피 서버사이드에서 돌고 있으므로 괜찮다.
        return "/members/memberList";
    }


}
