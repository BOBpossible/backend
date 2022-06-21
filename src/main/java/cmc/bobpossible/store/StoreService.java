package cmc.bobpossible.store;

import cmc.bobpossible.category.Category;
import cmc.bobpossible.category.CategoryRepository;
import cmc.bobpossible.config.BaseException;
import cmc.bobpossible.member.Address;
import cmc.bobpossible.menu_image.MenuImage;
import cmc.bobpossible.operation_time.OperationTime;
import cmc.bobpossible.store.dto.PostStoreReq;
import cmc.bobpossible.utils.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static cmc.bobpossible.config.BaseResponseStatus.CHECK_QUIT_USER;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class StoreService {

    private final StoreRepository storeRepository;
    private final CategoryRepository categoryRepository;
    private final S3Uploader s3Uploader;

    @Transactional
    public void createStore(PostStoreReq postStoreReq, List<MultipartFile> representativeMenuImages) throws BaseException, IOException {

        Category category = categoryRepository.findById(postStoreReq.getStoreTypeId())
                .orElseThrow(() -> new BaseException(CHECK_QUIT_USER));

        // 최대 3번
        List<String> imageURL = new ArrayList<>();
        for (int i = 0; i < representativeMenuImages.size() || i < 3; i++) {
            imageURL.add( s3Uploader.upload(representativeMenuImages.get(i), "menuImage"));
        }

        // 메뉴 이미지 엔티티
        List<MenuImage> menuImages = imageURL.stream()
                .map(i -> MenuImage.builder().image(i).build())
                .collect(Collectors.toList());


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

        Store store = Store.create(
                postStoreReq.getStoreName(),
                new Address(postStoreReq.getAddressStreet(), postStoreReq.getAddressDetail(), postStoreReq.getAddressDong()),
                category,
                postStoreReq.getTableNum(),
                postStoreReq.getRepresentativeMenuName(),
                menuImages,
                operationTimes
        );

        storeRepository.save(store);
    }
}
