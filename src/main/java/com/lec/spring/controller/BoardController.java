package com.lec.spring.controller;

import com.lec.spring.domain.Post;
import com.lec.spring.domain.PostValidator;
import com.lec.spring.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
// ↑ 롬복(Lombok) 어노테이션으로, 클래스에 선언된 final이나 @NonNull인 필드에 대한 생성자를 자동으로 생성합니다.
@RestController
// ↑ 해당 클래스가 RESTful 웹 서비스의 컨트롤러임을 나타내는 어노테이션입니다.
// 각 메서드의 반환 값은 HTTP 응답으로 변환되어 클라이언트에게 전송됩니다.
@CrossOrigin
// ↑ CORS(Cross-Origin Resource Sharing)를 허용하기 위한 어노테이션으로, 다른 도메인에서의 요청을 허용합니다.
@RequestMapping("/board")
public class BoardController {

    private final PostService postService;
    // ↑ PostService 인터페이스를 주입받는 생성자를 생성하고, 해당 필드를 불변(final)으로 선언합니다.


    @GetMapping("/")
    public ResponseEntity<?> checkResponse() {
        return new ResponseEntity<String>("정상적으로 응답! HttpStatus는? => " + HttpStatus.OK + " 좋았어!", HttpStatus.OK); // Postman으로 요청결과를 확인하였다!
    }

    // 새로운 글 작성
    @PostMapping("/write")
    public ResponseEntity<?> 새글작성(@RequestBody Post post, BindingResult bindingResult) {
        return new ResponseEntity<>(postService.작성(post), HttpStatus.CREATED);
    }

    // 목록 가져오기~
    @GetMapping("/list")
    public ResponseEntity<?> 목록() {
        return new ResponseEntity<>(postService.전체목록(), HttpStatus.OK);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<?> 특정글(@PathVariable Long id) {
        return new ResponseEntity<>(postService.글한개(id), HttpStatus.OK);
    }

    @PutMapping("update")
    public ResponseEntity<?> 글수정(@RequestBody Post post) {
        return new ResponseEntity<>(postService.수정(post), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> 글삭제(@PathVariable Long id) {
        return new ResponseEntity<>(postService.삭제(id), HttpStatus.OK);
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        System.out.println("initBinder() 호출");
        binder.setValidator(new PostValidator());
    }


}
