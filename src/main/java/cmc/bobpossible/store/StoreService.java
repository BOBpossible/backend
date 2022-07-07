package cmc.bobpossible.store;

import cmc.bobpossible.category.Category;
import cmc.bobpossible.category.CategoryRepository;
import cmc.bobpossible.config.BaseException;
import cmc.bobpossible.config.auth.SecurityUtil;
import cmc.bobpossible.member.MemberRepository;
import cmc.bobpossible.member.entity.Member;
import cmc.bobpossible.menu_image.MenuImage;
import cmc.bobpossible.menu_image.MenuImageRepository;
import cmc.bobpossible.mission.Mission;
import cmc.bobpossible.mission_group.MissionGroup;
import cmc.bobpossible.mission_group.MissionGroupRepository;
import cmc.bobpossible.operation_time.OperationTImeRepository;
import cmc.bobpossible.operation_time.OperationTime;
import cmc.bobpossible.store.dto.*;
import cmc.bobpossible.store_image.StoreImage;
import cmc.bobpossible.store_image.StoreImageRepository;
import cmc.bobpossible.utils.DistanceCalculator;
import cmc.bobpossible.utils.S3Uploader;
import lombok.RequiredArgsConstructor;
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
    private final MissionGroupRepository missionGroupRepository;
    private final MenuImageRepository menuImageRepository;
    private final StoreImageRepository storeImageRepository;
    private final OperationTImeRepository operationTImeRepository;

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

        List<MissionGroup> missionGroups = new ArrayList<>();

        missionGroups.add(MissionGroup.builder()
                .missionContent("10,000원 이상")
                .point(500)
                .store(store)
                .build());

        missionGroups.add(MissionGroup.builder()
                .missionContent("대표메뉴 " + store.getRepresentativeMenuName())
                .point(500)
                .store(store)
                .hasImage(true)
                .build());

        store.getMember().completeRegister();

        missionGroupRepository.saveAll(missionGroups);

        storeRepository.save(store);
    }

    public List<GetStoreMapRes> getStoreMap(Long userId) throws BaseException {

        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new BaseException(CHECK_QUIT_USER));

        List<Store> stores = storeRepository.findByAddressDong(member.getAddress().getDong());

        List<GetStoreMapRes> res = new ArrayList<>();

        stores.forEach(store -> {
            //기본값
            GetStoreMapRes value = GetStoreMapRes.builder()
                    .isMission(false)
                    .name(store.getName())
                    .distance(DistanceCalculator.distance(member.getAddress().getX(), member.getAddress().getY(), store.getAddress().getX(), store.getAddress().getY()))
                    .addressStreet(store.getAddress().getStreet())
                    .addressDetail(store.getAddress().getDetail())
                    .category(store.getCategory().getName())
                    .imageUrl("")
                    .storeId(store.getId())
                    .userX(member.getAddress().getX())
                    .userY(member.getAddress().getY())
                    .build();
            for (Mission mission : member.getMissions()) {
                if(mission.getMissionGroup().getStore() == store){
                    value.changeToHasMission(mission);
                }
            }
            res.add(value);
        });

        return res;
    }

    public GetStoreRes getStore(Long storeId) throws BaseException {

        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new BaseException(INVALID_STORE_ID));

        return new GetStoreRes(store);
    }

    @Transactional
    public void postRepresentativeMenuImages(List<MultipartFile> representativeMenuImages, Long storeId) throws IOException, BaseException {

        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new BaseException(INVALID_STORE_ID));

        List<String> imageURL = new ArrayList<>();
        for (int i = 0; i < representativeMenuImages.size() || i < 3; i++) {
            imageURL.add( s3Uploader.upload(representativeMenuImages.get(i), "menuImage"));
        }

        // 메뉴 이미지 엔티티
        List<MenuImage> menuImages = imageURL.stream()
                .map(i -> MenuImage.builder().image(i).build())
                .collect(Collectors.toList());

        store.addMenuImages(menuImages);
    }

    @Transactional
    public void postStoreImages(List<MultipartFile> storeImages, Long storeId) throws BaseException, IOException {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new BaseException(INVALID_STORE_ID));

        List<String> imageURL = new ArrayList<>();
        for (int i = 0; i < storeImages.size() || i < 3; i++) {
            imageURL.add( s3Uploader.upload(storeImages.get(i), "storeImage"));
        }

        // 이미지 엔티티
        List<StoreImage> storeImage = imageURL.stream()
                .map(i -> StoreImage.builder().image(i).build())
                .collect(Collectors.toList());

        store.addStoreImages(storeImage);
    }

    @Transactional
    public void updateStore(UpdateStoreReq updateStoreReq) throws BaseException {

        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .orElseThrow(() -> new BaseException(CHECK_QUIT_USER));

        Category category = categoryRepository.findById(updateStoreReq.getStoreTypeId())
                .orElseThrow(() -> new BaseException(INVALID_CATEGORY_ID));

        member.getStore().update(updateStoreReq.getStoreName(), updateStoreReq.getIntro(), new StoreAddress(updateStoreReq.getAddressStreet(), updateStoreReq.getAddressDetail() ,updateStoreReq.getAddressDong(), updateStoreReq.getX(), updateStoreReq.getY()), updateStoreReq.getTableNum(), updateStoreReq.getRepresentativeMenuName(), category);
    }

    @Transactional
    public void deleteMenuImage(Long menuImageId) throws BaseException {

        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .orElseThrow(() -> new BaseException(CHECK_QUIT_USER));

        MenuImage menuImage = menuImageRepository.findById(menuImageId)
                .orElseThrow(() -> new BaseException(INVALID_MENU_IMAGE_ID));

        member.getStore().deleteMenuImage(menuImage);
    }

    @Transactional
    public void deleteStoreImage(Long storeImageId) throws BaseException {

        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .orElseThrow(() -> new BaseException(CHECK_QUIT_USER));

        StoreImage storeImage = storeImageRepository.findById(storeImageId)
                .orElseThrow(() -> new BaseException(INVALID_STORE_IMAGE_ID));

        member.getStore().deleteStoreImage(storeImage);
    }

    @Transactional
    public void updateOperationTime(OperationTimeVO operationTime, Long operationTimeId) throws BaseException {

        OperationTime ot = operationTImeRepository.findById(operationTimeId)
                .orElseThrow(() -> new BaseException(INVALID_OPERATION_TIME_ID));

        ot.update(operationTime.getDayOfWeek(), operationTime.getStartTime(), operationTime.getEndTime(), operationTime.getBreakStartTime(), operationTime.getBreakEndTime(), operationTime.isHasOperationTime(), operationTime.isHasBreak());

    }
}
