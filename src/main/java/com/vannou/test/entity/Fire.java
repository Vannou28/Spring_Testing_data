package com.vannou.test.entity;

import java.time.Instant;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;

@Entity
@Table(name = "fire")
public class Fire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Min(value = 0, message = "severity must be positive")
    private int severity;
    
    private Instant date ;   
    @ManyToOne
    @JoinColumn(name = "fireman_id", nullable = true)
    private Fireman fireman;

    public Fire() {
    }
    public Fire( int severity, Instant date) {

        this.severity = severity;
        this.date = date;

    }

    public Fire( Long id, int severity, Instant date) {

        this.severity = severity;
        this.date = date;

    }

    public Fire(Long id, int severity, Instant date, Fireman fireman) {
        this.id = id;
        this.severity = severity;
        this.date = date;
        this.fireman = fireman;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getSeverity() {
        return this.severity;
    }

    public void setSeverity(int severity) {
        this.severity = severity;
    }

    public Instant getDate() {
        return this.date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Fireman getfireman() {
        return this.fireman;
    }

    public void setfireman(Fireman fireman) {
        this.fireman = fireman;
    }

}
