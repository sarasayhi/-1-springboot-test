package com.Marissa.FAQ.repository;

import com.Marissa.FAQ.repository.po.Doc;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;
import java.util.List;

@Transactional
@Repository
public interface DocRepository extends PagingAndSortingRepository<Doc, Integer> {
    @Query(value = "select d from Doc d where d.deleted = 0")
    List<Doc> getDocByPage(Pageable pageable);

    @Modifying
    @Query("update Doc d set d.deleted = 1 where d.id in :ids")
    void deleteBatch(@Param("ids") List<Integer> ids);

    @Query(value = "select d.id from Doc d where d.deleted = 0 and d.name = :name")
    int getId(@Param("name") String name);

    @Query(value = "select d from Doc d where d.deleted = 0 and d.name = :name")
    Doc getByName(@Param("name") String name);

    @Query(value = "select count(d) from Doc d where d.deleted = 0 and d.name = :name")
    int checkExist(@Param("name") String name);
}