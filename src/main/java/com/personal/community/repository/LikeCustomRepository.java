package com.personal.community.repository;

import com.personal.community.domain.Like;

import java.util.Optional;

public interface LikeCustomRepository {
    Optional<Like> findByBoardIdAndMemberId(Long boardId, Long memberId);
}
