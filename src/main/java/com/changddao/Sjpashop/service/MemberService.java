package com.changddao.Sjpashop.service;

import com.changddao.Sjpashop.entity.Member;
import com.changddao.Sjpashop.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    //회원가입
    public Long join(Member member) {
        validateDuplicateMember(member);// 중복회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMember = memberRepository.findByUsername(member.getUsername());
        if (!findMember.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    //회원 전체 조회
    @Transactional(readOnly = true)
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }
    //회원 단건 조회
    @Transactional(readOnly = true)
    public Member findOne(Long memberId) {
        Member findMember = memberRepository.findById(memberId).orElse(null);
        return findMember;
    }
    @Transactional
    public void update(Long id, String name) {
        Member findMember = memberRepository.findById(id).orElse(null);
        findMember.setUsername(name);
    }
}
