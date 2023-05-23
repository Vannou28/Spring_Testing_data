package com.vannou.test.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vannou.test.entity.Fire;

@Repository
public interface FireRepository extends JpaRepository<Fire, Long> {
    
}
