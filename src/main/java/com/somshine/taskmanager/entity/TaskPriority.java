package com.somshine.taskmanager.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "task_priorities")
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TaskPriority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "NAME", nullable = false, length = 600)
    private String name;

    @ColumnDefault("1")
    @Column(name = "IS_ACTIVE", nullable = false)
    private Boolean isActive = false;

    @Column(name = "CREATED_ON")
    private Instant createdOn;

}