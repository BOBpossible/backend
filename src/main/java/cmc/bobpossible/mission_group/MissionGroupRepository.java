package cmc.bobpossible.mission_group;

import cmc.bobpossible.Status;
import cmc.bobpossible.store.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MissionGroupRepository extends JpaRepository<MissionGroup, Long> {
    List<MissionGroup> findByStore(Store store);

    @Query(value = "select m from MissionGroup mg join mg.store s where s.id = ?1 and mg.status <> ?2")
    List<MissionGroup> findByStoreAndStatus(Long storeId, Status deleted);
}
