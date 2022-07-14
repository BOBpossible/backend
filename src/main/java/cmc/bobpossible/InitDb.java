package cmc.bobpossible;

import cmc.bobpossible.category.Category;
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
        //initService.dbInit1();

    }
    

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;


        public void dbInit1() {

            Category category1 = Category.create("한식");
            em.persist(category1);

            Category category2 = Category.create("일식");
            em.persist(category2);

            Category category3 = Category.create("중식");
            em.persist(category3);

            Category category4 = Category.create("양식");
            em.persist(category4);

            Category category5 = Category.create("치킨");
            em.persist(category5);

            Category category6 = Category.create("분식");
            em.persist(category6);

            Category category7 = Category.create("고기/구이");
            em.persist(category7);

            Category category8 = Category.create("도시락");
            em.persist(category8);

            Category category9 = Category.create("야식");
            em.persist(category9);

            Category category10 = Category.create("패스트푸드");
            em.persist(category10);

            Category category11 = Category.create("디저트");
            em.persist(category11);

            Category category12 = Category.create("아시안푸트");
            em.persist(category12);

        }




    }
}
