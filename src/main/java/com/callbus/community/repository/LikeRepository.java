package com.callbus.community.repository;

import com.callbus.community.domain.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long>, LikeCustomRepository {
    void deleteByLikeId(Long likeId);
}
