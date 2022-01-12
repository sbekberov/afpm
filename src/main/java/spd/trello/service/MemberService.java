package spd.trello.service;

import spd.trello.domain.Member;
import spd.trello.domain.Role;
import spd.trello.domain.User;
import spd.trello.repository.MemberRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.UUID;

public class MemberService extends AbstractService<Member> {

    MemberRepository memberRepository = new MemberRepository();

    public Member create(User user, Role memberRole) throws IllegalAccessException {
        Member member = new Member();
        member.setId(UUID.randomUUID());
        member.setCreatedBy(user.getEmail());
        member.setCreatedDate(Date.valueOf(LocalDate.now()));
        member.setUsersId(user.getId());
        if (memberRole != null){
            member.setRole(memberRole);
        }
        memberRepository.create(member);
        return memberRepository.findById(member.getId());
    }


    public void update(Member member) throws IllegalAccessException {
        memberRepository.update(member);
    }
    public void getAll() {
        memberRepository.getAll();
    }

    public Member findById(UUID id) {
        Member member = null;
        try {
            member = memberRepository.findById(id);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return member;
    }

    public boolean delete(UUID id) {
        return memberRepository.delete(id);
    }
}
