package com.geradorAleatorio.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ACTIVITY")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RandomActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "ACTIVITY")
    private String activity;

    @Column(name = "AVAILABILITY")
    private int availability;

    @Column(name = "ACTIVITY_TYPE")
    private String type;

    @Column(name = "PARTICIPANTS")
    private int participants;

    @Column(name = "PRICE")
    private double price;

    @Column(name = "ACCESSIBILITY")
    private String accessibility;

    @Column(name = "DURATION")
    private String duration;

    @Column(name = "KID_FRIENDLY")
    private boolean kidFriendly;

    @Column(name = "LINK")
    private String link;

    @Column(name = "ACTIVITY_KEY")
    private String key;
}


