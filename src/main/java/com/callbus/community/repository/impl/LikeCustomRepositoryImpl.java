package com.callbus.community.repository.impl;

import com.callbus.community.domain.Like;
import com.callbus.community.repository.LikeCustomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.Optional;

@Repository
public class LikeCustomRepositoryImpl implements LikeCustomRepository {
    @Autowired
    EntityManager entityManager;


    @Override
    public Optional<Like> findByBoardIdAndMemberId(Long boardId, Long memberId) {
        Optional<Like> like = null;
        try{
            like = Optional.ofNullable(entityManager.createQuery("SELECT l FROM  Like l WHERE l.board.boardId = :boardId AND l.member.memberId = :memberId", Like.class)
                    .setParameter("boardId", boardId)
                    .setParameter("memberId", memberId)
                    .getSingleResult());
        } catch (NoResultException e){
            like = Optional.empty();
        } finally {
            return like;
        }

    }
}
