package cmc.bobpossible;

import cmc.bobpossible.favorite.Favorite;
import cmc.bobpossible.favorite.FavoriteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class InitDb {
    
    private final InitService initService;
    
    @PostConstruct
    public void init() {
        initService.dbInit1();

    }
    

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;


        public void dbInit1() {

            Favorite favorite1 = Favorite.create("한식");
            em.persist(favorite1);

            Favorite favorite2 = Favorite.create("일식");
            em.persist(favorite1);

            Favorite favorite3 = Favorite.create("중식");
            em.persist(favorite1);

            Favorite favorite4 = Favorite.create("양식");
            em.persist(favorite1);

            Favorite favorite5 = Favorite.create("치킨");
            em.persist(favorite1);

            Favorite favorite6 = Favorite.create("분식");
            em.persist(favorite1);

            Favorite favorite7 = Favorite.create("고기/구이");
            em.persist(favorite1);

            Favorite favorite8 = Favorite.create("도시락");
            em.persist(favorite1);

            Favorite favorite9 = Favorite.create("야식");
            em.persist(favorite1);

            Favorite favorite10 = Favorite.create("패스트푸드");
            em.persist(favorite1);

            Favorite favorite11 = Favorite.create("디저트");
            em.persist(favorite1);

            Favorite favorite12 = Favorite.create("아시안푸트");
            em.persist(favorite1);

        }




    }
}
