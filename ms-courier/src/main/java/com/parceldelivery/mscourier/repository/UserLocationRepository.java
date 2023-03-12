package com.parceldelivery.mscourier.repository;


import com.parceldelivery.mscourier.entity.User;
import com.parceldelivery.mscourier.entity.UserLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserLocationRepository extends JpaRepository<UserLocation, Long> {

    Optional<UserLocation> findByUser(User user);

    @Query(value = "select * from user_location ul where ul.user_id = :id order by ul.id desc limit 1",
            nativeQuery = true)
    Optional<UserLocation> findByLastLocationByUserId(Integer id);

}
