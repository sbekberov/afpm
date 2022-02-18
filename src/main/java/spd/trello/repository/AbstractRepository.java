package spd.trello.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import spd.trello.domain.common.Domain;
import spd.trello.domain.common.Resource;

import java.util.UUID;

@NoRepositoryBean
public interface AbstractRepository<E extends Resource> extends JpaRepository<E, UUID>,
        JpaSpecificationExecutor<E> {
}