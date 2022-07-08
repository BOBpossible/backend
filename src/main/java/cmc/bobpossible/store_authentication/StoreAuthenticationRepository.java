package cmc.bobpossible.store_authentication;

import cmc.bobpossible.store_image.StoreImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreAuthenticationRepository extends JpaRepository<StoreAuthentication, Long> {
}
