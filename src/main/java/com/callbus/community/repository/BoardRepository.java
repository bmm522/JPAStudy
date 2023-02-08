package com.callbus.community.repository;

import com.callbus.community.domain.Board;
import com.callbus.community.domain.util.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {
    Optional<Board> findByBoardId(Long boardId);

    List<Board> findByStatus(Status status);
}
