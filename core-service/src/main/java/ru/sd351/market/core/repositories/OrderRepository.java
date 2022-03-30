package ru.sd351.market.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sd351.market.core.entities.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
