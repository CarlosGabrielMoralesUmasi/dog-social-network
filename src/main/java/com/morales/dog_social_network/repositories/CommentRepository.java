package com.morales.dog_social_network.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.morales.dog_social_network.models.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
