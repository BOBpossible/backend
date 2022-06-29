package cmc.bobpossible.point;

import cmc.bobpossible.member.entity.Member;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointRepository extends JpaRepository<Point, Long> {
    Slice<Point> findByMemberOrderByIdDesc(Member member, Pageable pageable);
}
