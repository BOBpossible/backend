package cmc.bobpossible.store;

import cmc.bobpossible.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoreRepository extends JpaRepository<Store, Long> {
    List<Store> findByAddressDong(String dong);

    List<Store> findByCategory(Category category);
}
