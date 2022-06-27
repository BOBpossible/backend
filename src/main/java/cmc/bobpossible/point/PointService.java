package cmc.bobpossible.point;

import cmc.bobpossible.config.BaseException;
import cmc.bobpossible.config.auth.SecurityUtil;
import cmc.bobpossible.member.MemberRepository;
import cmc.bobpossible.member.entity.Member;
import cmc.bobpossible.point.dto.GetMyPoints;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static cmc.bobpossible.config.BaseResponseStatus.CHECK_QUIT_USER;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class PointService {

    private final MemberRepository memberRepository;
    private final PointRepository pointRepository;

    public GetMyPoints getMyPoints(Pageable pageable) throws BaseException {

        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .orElseThrow(() -> new BaseException(CHECK_QUIT_USER));

        Slice<Point> points = pointRepository.findByMemberOrderByIdDesc(member, pageable);

        return new GetMyPoints(points, member.getTotalPoints());
    }
}
