package com.basic.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

@Data
@Entity
@Table(name = "stored_demo_row")
@EqualsAndHashCode
public class StoredDemoRow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_inc;

    @Column(name = "id", unique = true, nullable = false)
    private String id;

    @Column(name = "value", nullable = false)
    private String value;

    @Version
    @Column(name = "version")
    private Long version;
}
