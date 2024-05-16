package com.microservice.classservice.reponsitories;

import com.microservice.classservice.models.OfficialClass;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfficialClassRepository extends JpaRepository<OfficialClass, String> {
}