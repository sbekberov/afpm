package spd.trello.repository;

import org.springframework.stereotype.Repository;
import spd.trello.domain.CheckList;
import spd.trello.domain.CheckableItem;

@Repository
public interface CheckableItemRepository extends AbstractRepository<CheckableItem>{
}
