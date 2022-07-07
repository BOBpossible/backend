package cmc.bobpossible.mission;

import cmc.bobpossible.member.entity.Member;
import cmc.bobpossible.mission_group.MissionGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface MissionRepository extends JpaRepository<Mission, Long> {
    List<Mission> findByMember(Member member);

    List<Mission> findByMemberAndMissionStatus(Member member, MissionStatus progress);

    List<Mission> findByMissionGroup(MissionGroup missionGroup);

    List<Mission> findByMissionGroupAndMissionStatus(MissionGroup missionGroup, MissionStatus progress);
}
