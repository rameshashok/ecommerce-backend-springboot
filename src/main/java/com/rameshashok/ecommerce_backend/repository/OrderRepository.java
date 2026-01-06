package com.rameshashok.ecommerce_backend.repository;

import com.rameshashok.ecommerce_backend.entity.Order;
import com.rameshashok.ecommerce_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);
    List<Order> findByUserOrderByCreatedAtDesc(User user);
    Optional<Order> findByIdAndUser(Long id, User user);
}