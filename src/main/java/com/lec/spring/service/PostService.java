package com.lec.spring.service;

import com.lec.spring.domain.Post;
import com.lec.spring.repository.PostRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class PostService {

    private final PostRepository postRepository;

    public Post 작성(@Valid Post post) {
        post.setRegDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        return postRepository.save(post);
    }

    //    @Transactional(readOnly = true)
    public List<Post> 전체목록() {
        return postRepository.findAll(Sort.by(Sort.Order.desc("id")));
    }

    //    @Transactional(readOnly = true) // 이것 때문에 조회수 증가가 안되고 있었네~ㅋ
    public Post 글한개(Long id) {
        Post detailPost = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("글의 번호를 확인하세요."));
        if (detailPost != null) {
            // 조회수 증가
            detailPost.setViewCnt(detailPost.getViewCnt() + 1);
            System.out.println("조회수 증가됐나? " + detailPost);

            postRepository.save(detailPost);
        }
        return detailPost;
    }

    public Post 수정(Post post) {
        Post updatedPost = postRepository
                .findById(post.getId())
                .orElseThrow(() -> new IllegalArgumentException("수정할 글의 번호를 확인하세요."));

        System.out.println("업데이트할 post는? => " + updatedPost);

        updatedPost.setSubject(post.getSubject());
        updatedPost.setContent(post.getContent());

        return updatedPost;
    }

    public int 삭제(Long id) {
        boolean existence = postRepository.existsById(id);

        if (!existence) return 0;

        postRepository.deleteById(id);
        return 1;
    }
}
