package com.changddao.Sjpashop.repository;

import com.changddao.Sjpashop.entity.Address;
import com.changddao.Sjpashop.entity.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class MemberRepositoryTest {
    @Autowired
    EntityManager em;
    @Autowired
    MemberRepository memberRepository;
    @Test
    public void simpleTest() {
        memberRepository.save(new Member("changho", new Address("daegu", "청수로", "261")));
        memberRepository.save(new Member("jongho", new Address("daegu", "청수로", "261")));
        List<Member> members = memberRepository.findAll();
        assertThat(members).extracting("username")
                .containsExactly("changho", "jongho");


    }
}