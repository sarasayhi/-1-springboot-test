package com.Marissa.FAQ.repository;

import com.Marissa.FAQ.repository.po.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public interface TagRepository extends PagingAndSortingRepository<Tag, Integer> {
    @Query(value = "select d from Tag d where d.deleted = 0")
    List<Tag> getAll(Pageable pageable);

    @Modifying
    @Query("update Tag d set d.deleted = 1 where d.id in :ids")
    void deleteBatch(@Param("ids") List<Integer> ids);

    @Query(value = "select count(d) from Tag d where d.deleted = 0")
    int getTotal();

//    @Query(value = "select d.id from Tag d where d.deleted = 0 and d.name = :name")
//    int getId(@Param("name") String name);

    @Query(value = "select count(d) from Tag d where d.deleted = 0 and d.name = :name")
    int checkExist(@Param("name") String name);

    @Query(value = "select d from Tag d where d.deleted = 0 and d.name = :name")
    Tag findByName(@Param("name") String name);

//    @Query(value = "select * from Tag d where d.deleted = 0 order by d.searchCnt desc limit 5",nativeQuery=true)
//    List<Tag> top5(@Param("limit") int limit);

    @Modifying
    @Query("update Tag d set d.docCnt = d.docCnt + 1 where d.name in :names")
    void updateDocCnt(@Param("names") List<String> names);

    @Modifying
    @Query("update Tag d set d.searchCnt = d.searchCnt + 1 where d.name like %:name%")
    void updateSearchCnt(@Param("name") String name);

}