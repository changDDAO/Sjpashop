package com.changddao.Sjpashop.service;

import com.changddao.Sjpashop.entity.Member;
import com.changddao.Sjpashop.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
class MemberServiceTest {
    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    public void 회원가입() throws Exception {
        //given
        Member member = new Member("kim");
        //when
        Long id = memberService.join(member);
        //then
        assertThat(member.getId()).isEqualTo(id);
    }

    @Test
    public void 중복_회원_예외() throws Exception {
        //given
        Member member1 = new Member("kim");
        Member member2 = new Member("kim");
        memberService.join(member1);
        //when
        try {
            memberService.join(member2);

        } catch (IllegalStateException e) {
            return;
        }


        //then

    }

}