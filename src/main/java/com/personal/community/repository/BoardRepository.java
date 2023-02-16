package com.personal.community.repository;

import com.personal.community.domain.Board;
import com.personal.community.domain.util.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {
    Optional<Board> findByBoardId(Long boardId);

    List<Board> findByStatus(Status status);
}
