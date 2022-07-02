package cmc.bobpossible.store;

import cmc.bobpossible.category.Category;
import cmc.bobpossible.category.CategoryRepository;
import cmc.bobpossible.config.BaseException;
import cmc.bobpossible.config.auth.SecurityUtil;
import cmc.bobpossible.member.Address;
import cmc.bobpossible.member.MemberRepository;
import cmc.bobpossible.member.entity.Member;
import cmc.bobpossible.menu_image.MenuImage;
import cmc.bobpossible.mission.Mission;
import cmc.bobpossible.operation_time.OperationTime;
import cmc.bobpossible.review.ReviewRepository;
import cmc.bobpossible.review_image.ReviewImage;
import cmc.bobpossible.review_image.ReviewImageRepository;
import cmc.bobpossible.store.dto.*;
import cmc.bobpossible.utils.DistanceCalculator;
import cmc.bobpossible.utils.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static cmc.bobpossible.config.BaseResponseStatus.*;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class StoreService {

    private final StoreRepository storeRepository;
    private final CategoryRepository categoryRepository;
    private final S3Uploader s3Uploader;
    private final MemberRepository memberRepository;

    @Transactional
    public void createStore(PostStoreReq postStoreReq) throws BaseException, IOException {

        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .orElseThrow(() -> new BaseException(CHECK_QUIT_USER));

        Category category = categoryRepository.findById(postStoreReq.getStoreTypeId())
                .orElseThrow(() -> new BaseException(INVALID_CATEGORY_ID));

        // 최대 3번
//        List<String> imageURL = new ArrayList<>();
//        for (int i = 0; i < representativeMenuImages.size() || i < 3; i++) {
//            imageURL.add( s3Uploader.upload(representativeMenuImages.get(i), "menuImage"));
//        }

        // 메뉴 이미지 엔티티
//        List<MenuImage> menuImages = imageURL.stream()
//                .map(i -> MenuImage.builder().image(i).build())
//                .collect(Collectors.toList());


        List<OperationTime> operationTimes = postStoreReq.getOperationTimeVO().stream()
                .map(
                        o -> OperationTime.builder()
                                .dayOfWeek(o.getDayOfWeek())
                                .startTime(o.getStartTime())
                                .endTime(o.getEndTime())
                                .breakStartTime(o.getBreakStartTime())
                                .breakEndTime(o.getBreakEndTime())
                                .build())
                .collect(Collectors.toList());

        Store store = Store.builder()
                .member(member)
                .name(postStoreReq.getStoreName())
                .intro(postStoreReq.getIntro())
                .address(new StoreAddress(postStoreReq.getAddressStreet(), postStoreReq.getAddressDetail(),  postStoreReq.getAddressDong(), postStoreReq.getX(), postStoreReq.getY()))
                .category(category)
                .tableNum(postStoreReq.getTableNum())
                .representativeMenuName(postStoreReq.getRepresentativeMenuName())
 //               .menuImages(menuImages)
                .operationTimes(operationTimes)
                .build();

        store.getMember().completeRegister();

        storeRepository.save(store);
    }

    public List<GetStoreMapRes> getStoreMap() throws BaseException {

        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .orElseThrow(() -> new BaseException(CHECK_QUIT_USER));

        List<Store> stores = storeRepository.findByAddressDong(member.getAddress().getDong());

        return stores.stream().map(
                store-> {
                    for (Mission mission : member.getMissions()) {
                        if(mission.getStore() == store){
                            return GetStoreMapRes.builder()
                                        .isMission(true)
                                        .point(mission.getPoint())
                                        .name(store.getName())
                                        .distance(DistanceCalculator.distance(member.getAddress().getX(), member.getAddress().getY(), store.getAddress().getX(), store.getAddress().getY()))
                                        .x(store.getAddress().getX())
                                        .y(store.getAddress().getY())
                                        .category(store.getCategory().getName())
                                        .imageUrl("")
                                        .storeId(store.getId())
                                        .build();
                        }
                    }
                    return GetStoreMapRes.builder()
                                .isMission(false)
                                .name(store.getName())
                                .distance(DistanceCalculator.distance(member.getAddress().getX(), member.getAddress().getY(), store.getAddress().getX(), store.getAddress().getY()))
                                .x(store.getAddress().getX())
                                .y(store.getAddress().getY())
                                .category(store.getCategory().getName())
                                .imageUrl("")
                                .storeId(store.getId())
                                .build();
                }
        ).collect(Collectors.toList());

    }

    public GetStoreRes getStore(Long storeId) throws BaseException {

        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new BaseException(INVALID_STORE_ID));

        return new GetStoreRes(store);
    }
}
