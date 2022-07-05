package cmc.bobpossible.mission_group;

import cmc.bobpossible.store.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MissionGroupRepository extends JpaRepository<MissionGroup, Long> {
    List<MissionGroup> findByStore(Store store);
}
