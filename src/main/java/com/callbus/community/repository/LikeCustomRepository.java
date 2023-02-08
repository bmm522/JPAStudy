package com.callbus.community.repository;

import com.callbus.community.domain.Like;

import java.util.Optional;

public interface LikeCustomRepository {
    Optional<Like> findByBoardIdAndMemberId(Long boardId, Long memberId);
}
