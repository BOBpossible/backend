package cmc.bobpossible.point_conversion;

import cmc.bobpossible.config.BaseException;
import cmc.bobpossible.config.auth.SecurityUtil;
import cmc.bobpossible.member.MemberRepository;
import cmc.bobpossible.member.entity.Member;
import cmc.bobpossible.point.Point;
import cmc.bobpossible.point_conversion.dto.CreatePointConversion;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static cmc.bobpossible.config.BaseResponseStatus.CHECK_QUIT_USER;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class PointConversionService {

    private final MemberRepository memberRepository;
    private final PointConversionRepository pointConversionRepository;

    @Transactional
    public void createPointConversion(CreatePointConversion createPointConversion) throws BaseException {

        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .orElseThrow(() -> new BaseException(CHECK_QUIT_USER));

        member.addPoint(Point.builder()
                .point(-(createPointConversion.getPoint()))
                .title("포인트 전환")
                .subtitle(String.valueOf(createPointConversion.getPoint())+"포인트 전환")
                .build());

        PointConversion pointConversion = PointConversion.builder()
                .member(member)
                .point(createPointConversion.getPoint())
                .name(createPointConversion.getName())
                .bank(createPointConversion.getBank())
                .accountNumber(createPointConversion.getAccountNumber())
                .build();

        pointConversionRepository.save(pointConversion);
    }
}
