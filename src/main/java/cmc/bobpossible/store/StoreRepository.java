package cmc.bobpossible.store;

import cmc.bobpossible.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Member, Long> {
}
