package com.Marissa.FAQ.repository;

import com.Marissa.FAQ.repository.po.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /*@Modifying
    @Query ( "update DeviceDO d set d.deleted = true where d.id in :ids" )
    void deleteBatch(@Param ( "ids" ) List<Long> ids);

    @Modifying
    @Query ( value = "update DeviceDO d set d.num = :num, d.name = :name, d.userName = :userName, d.password = :password where d.id = :id" )
    void update(@Param ( "num" ) String num, @Param ( "name" ) String name, @Param ( "userName" ) String userName, @Param ( "password" ) String password, @Param ( "id" ) Long id);

    //    检查设备ids是否属于同一个组织id
    @Query ( value = "select count(d) from DeviceDO d where d.deleted = false and d.areaId = :areaId and d.id in :ids" )
    int isBelongOrg(@Param ( "ids" ) List<Long> ids, @Param ( "areaId" ) Long areaId);

    @Query ( value = "select count(d) from DeviceDO d where d.deleted = false and d.id = :id" )
    Long cntById(@Param ( "id" ) Long id);

    @Query ( value = "select count(d) from DeviceDO d where d.deleted = false and d.areaId = :areaId" )
    Long cntByAreaId(@Param ( "areaId" ) Long areaId);

    @Query ( value = "select count(d) from DeviceDO d where d.deleted = false and d.name like %:name% or d.IPAddress like %:name% and d.areaId = :areaId" )
    Long cntByKey(@Param ( "name" ) String name, @Param ( "areaId" ) Long areaId);

    // for pointService
    @Query ( value = "select d.id from DeviceDO d where d.deleted = false and d.areaId = :areaId" )
    List<Long> findByAreaId(@Param ( "areaId" ) Long areaId);

    // for pointService
    @Query ( value = "select d.id from DeviceDO d where d.deleted = false and d.areaId in :areaIds" )
    List<Long> findByAreaIds(@Param ( "areaIds" ) List<Long> areaIds);

    @Query ( value = "select d from DeviceDO d where d.deleted = false and d.areaId = :areaId" )
    List<DeviceDO> pageListByAreaId(@Param ( "areaId" ) Long areaId, Pageable pageable);

    @Query ( value = "select d from DeviceDO d where d.deleted = false and d.name like %:name% or d.IPAddress like %:name% and d.areaId = :areaId" )
    List<DeviceDO> pageListByKeyAndAreaId(@Param ( "name" ) String name, @Param ( "areaId" ) Long areaId, Pageable pageable);

    //判断有无资源
    @Query ( value = "select count(d) from DeviceDO d where d.deleted = false and d.areaId in :areaIds" )
    public Long cntByAreaIds(@Param ( "areaIds" ) List<Long> areaIds);

    // for pointService and api
    @Query ( value = "select d from DeviceDO d where d.deleted = false and d.areaId = :areaId" )
    List<DeviceDO> listByAreaId(@Param ( "areaId" ) Long areaId);

    @Query ( value = "select d from DeviceDO d where d.deleted = false and d.areaId = :areaId" )
    List<DeviceDO> getByAreaId(@Param ( "areaId" ) Long areaId);

    @Query ( value = "select d from DeviceDO d where d.deleted = false and d.id = :id" )
    DeviceDO findById(@Param ( "id" ) Long id);
*/
}
