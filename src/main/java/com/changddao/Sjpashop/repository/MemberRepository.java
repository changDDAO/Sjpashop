package com.changddao.Sjpashop.repository;

import com.changddao.Sjpashop.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {
    @Query("select m from Member m where m.username = :username")
    List<Member> findByUsername(@Param("username")String username);
}
