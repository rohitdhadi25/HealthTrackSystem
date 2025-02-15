package com.htps.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "progress")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Progress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)  // Foreign key reference to User table
    private User user;

    private double weight;
    private double bodyFatPercentage;
    private String status;

    // ✅ Add this method to return the userId directly
    public Long getUserId() {
        return (user != null) ? user.getId() : null;
    }
}
