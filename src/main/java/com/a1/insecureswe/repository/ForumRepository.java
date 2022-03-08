package com.a1.insecureswe.repository;

import com.a1.insecureswe.model.Forum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ForumRepository extends JpaRepository<Forum, Long> {
}
