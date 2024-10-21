package com.dctechlabs.accounts.repository;

import com.dctechlabs.accounts.entity.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransferRepository extends JpaRepository<Transfer, Long> {
}
