package com.callbus.community.repository;

import com.callbus.community.domain.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HeartRepository extends JpaRepository<Like, Long> {
}
