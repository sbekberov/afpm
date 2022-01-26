package spd.trello.service;

import org.springframework.stereotype.Service;
import spd.trello.domain.Member;
import spd.trello.repository.CRUDRepository;

@Service
public class MemberService extends AbstractService<Member> {

    public MemberService(CRUDRepository<Member> memberRepository) {
        super(memberRepository);
    }

}
