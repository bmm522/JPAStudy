package com.callbus.community.domain;

import com.callbus.community.service.dto.response.ServiceLikeResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Likes")
@Getter
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likeId;

    @CreationTimestamp
    private Timestamp likeCreateDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public ServiceLikeResponseDto toDto(Board board){
        return ServiceLikeResponseDto.builder()
                .boardId(board.getBoardId())
                .likeCount(board.getLikes().size()-1)
                .build();
    }

    public void addMember(Member member){
        this.member = member;
        member.addLike(this);
    }

    public void addBoard(Board board){
        this.board = board;
        board.addLike(this);
    }



}
