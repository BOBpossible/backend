package cmc.bobpossible.review_image;

import cmc.bobpossible.store.Store;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewImageRepository extends JpaRepository<ReviewImage, Long> {
    Slice<ReviewImage> findByStoreOrderByIdDesc(Store store, Pageable pageable);
}
