package com.morales.dog_social_network.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.morales.dog_social_network.models.Post;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByUser_Username(String username);
}
