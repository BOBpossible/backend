package cmc.bobpossible.push.firebase;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FirebaseTokenRepository extends JpaRepository<FirebaseToken, Long> {
    Optional<FirebaseToken> findByKey(Long id);
}
