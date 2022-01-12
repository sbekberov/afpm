package spd.trello.service;

import spd.trello.domain.Member;
import spd.trello.repository.MemberBoardRepository;

import java.util.List;
import java.util.UUID;

public class MemberBoardService {
        private final MemberBoardRepository repository;

        public MemberBoardService(MemberBoardRepository repository) {
            this.repository = repository;
        }

        public boolean findById(UUID memberId, UUID boardId) {
            return repository.findById(memberId, boardId);
        }

        public List<Member> findMembersByBoardId(UUID boardId) {
            return repository.findMembersByBoardId(boardId);
        }

        public boolean create(UUID memberId, UUID boardId) {
            return repository.create(memberId, boardId);
        }

        public boolean delete(UUID boardId) {
            return repository.delete(boardId);
        }
    }


