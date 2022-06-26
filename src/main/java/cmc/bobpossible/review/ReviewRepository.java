package cmc.bobpossible.review;

import cmc.bobpossible.store.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Slice<Review> findByStoreOrderByIdDesc(Store store, Pageable pageable);
}
