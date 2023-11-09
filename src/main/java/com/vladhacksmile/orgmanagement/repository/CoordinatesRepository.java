package com.vladhacksmile.orgmanagement.repository;

import com.vladhacksmile.orgmanagement.model.entity.Coordinates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoordinatesRepository extends JpaRepository<Coordinates, Long> {
    
}