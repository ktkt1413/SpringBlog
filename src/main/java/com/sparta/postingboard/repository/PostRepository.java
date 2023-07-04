package com.sparta.postingboard.repository;


import com.sparta.postingboard.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
public interface PostRepository extends JpaRepository<Post,Long> {
    List<Post> findAllByOrderByCreateAtDesc();
}
