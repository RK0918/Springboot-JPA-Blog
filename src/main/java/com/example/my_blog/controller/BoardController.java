package com.example.my_blog.controller;


import com.example.my_blog.config.auth.PrincipalDetail;
import com.example.my_blog.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//
@Controller
public class BoardController {


    // 글목록 보기를 위해 BoardService 연결
    @Autowired
    private BoardService boardService;

    // spring에선 data를 가져갈 때, model이 필요 ->Model model
    // model.addAttribute("boards", boardServiice)

    // @PageableDefault의 경우 DummyControlle 참고
    @GetMapping({"", "/"}) // 아무것도 안적을 때, 슬래쉬(/) 두 가지 경우
    public String index(Model model, @PageableDefault(size = 3, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) { // 컨트롤러에서 세션을 어떻게 찾는지 ?
        // WEB-INF/views/index.jsp -> yml 참고  prefix, suffix , 글목록 최신순으로 오게 DESC
        model.addAttribute("boards", boardService.글목록(pageable)); // service 가서 만들어야됨. -> 마찬가지로 BoardService에서 글목록() 수정 (@Pageable 이 변수로 더 들어가니깐)
        // 메인페이지로 갈 때 데이터를 가져가
        return "index"; // viewResolver 작동 -> application.yml에서 설정한 prefix + index + suffix -> /WEB-INF/views + index + .jsp
    } // index 라는 페이지로 저 위에 boards 가 날아감 -> index에서 jstl을 통해 "$boards" 로 boards 데이터를 받음

    // 컨트롤러에서 /board
    @GetMapping("/board/{id}")
    public String findById(@PathVariable int id, Model model) {
        model.addAttribute("board", boardService.글상세보기(id));
        return "board/detail";
    }

    @GetMapping("/board/{id}/updateForm")
    public String updateForm(@PathVariable int id, Model model) {
        model.addAttribute("board", boardService.글상세보기(id));
        return "board/updateForm";
    }

    // USER 권한이 필요
    @GetMapping("/board/saveForm")
    public String saveForm() {
        return "board/saveForm";
    }


}
