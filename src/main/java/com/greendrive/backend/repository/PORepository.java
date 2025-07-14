package com.greendrive.backend.repository;

import com.greendrive.backend.model.order.PO;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PORepository extends JpaRepository<PO, Long> {

    Optional<PO> findByEmailAndOrderNumber(String email, String orderNumber);
}
