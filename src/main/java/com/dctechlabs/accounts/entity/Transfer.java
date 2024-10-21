package com.dctechlabs.accounts.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "transfers")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Transfer extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long transferId;

    private double amount;

    private LocalDateTime scheduledTime;

    @Column(name = "sender_account_number", nullable = false)
    private String fromAccountNumber;  // Sender's account number

    @Column(name = "receiver_account_number", nullable = false)
    private String toAccountNumber;     // Receiver's account number
}
