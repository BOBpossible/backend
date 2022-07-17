package cmc.bobpossible.mission;

import cmc.bobpossible.Status;
import cmc.bobpossible.member.entity.Member;
import cmc.bobpossible.mission_group.MissionGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface MissionRepository extends JpaRepository<Mission, Long> {
    List<Mission> findByMember(Member member);

    List<Mission> findByMemberAndMissionStatus(Member member, MissionStatus progress);

    List<Mission> findByMissionGroup(MissionGroup missionGroup);

    List<Mission> findByMissionGroupAndMissionStatus(MissionGroup missionGroup, MissionStatus progress);

    List<Mission> findByMemberAndOnProgress(Member member, boolean onProgress);

    @Query(value = "select m from Mission m where m.member = ?1 and m.missionStatus = ?2 ")
    List<Mission> findByMemberAndMissionStatusAndStatus(Member member, MissionStatus done, Status expired);
}
