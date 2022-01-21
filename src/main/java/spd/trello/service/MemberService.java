package spd.trello.service;

import spd.trello.domain.Member;
import spd.trello.domain.Role;
import spd.trello.domain.User;
import spd.trello.repository.CRUDRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class MemberService extends AbstractService<Member> {

    public MemberService(CRUDRepository<Member> memberRepository){
        super(memberRepository);
    }


    public Member create(User user, Role memberRole)  {
        Member member = new Member();
        member.setId(UUID.randomUUID());
        member.setCreatedBy(user.getEmail());
        member.setCreatedDate(Date.valueOf(LocalDate.now()));
        member.setUsersId(user.getId());
        if (memberRole != null){
            member.setRole(memberRole);
        }
        repository.create(member);
        return repository.findById(member.getId());
    }


    public Member update(User user, Member entity) {
        entity.setUpdatedBy(user.getEmail());
        entity.setUpdatedDate(Date.valueOf(LocalDate.now()));
        return repository.update(entity);
    }

    public List<Member> getAll() {
        return repository.getAll();
    }

    public Member findById(UUID id) {
        return repository.findById(id);
    }

    public boolean delete(UUID id) {
        return repository.delete(id);
    }
}
