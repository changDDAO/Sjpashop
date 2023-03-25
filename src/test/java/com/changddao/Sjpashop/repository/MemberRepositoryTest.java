package com.changddao.Sjpashop.repository;

import com.changddao.Sjpashop.entity.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;

    @Test
    public void simpleTest() {
        Member member = new Member("changho");
        memberRepository.save(member);
        Member findMember = memberRepository.findById(member.getId()).orElse(null);
        assertThat(findMember.getId()).isEqualTo(member.getId());
    }


}