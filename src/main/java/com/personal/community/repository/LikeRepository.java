package com.personal.community.repository;

import com.personal.community.repository.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long>, LikeCustomRepository {
    void deleteByLikeId(Long likeId);
}
