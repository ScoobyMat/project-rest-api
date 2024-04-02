package com.project.rest.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name="zadanie")
public class Zadanie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "zadanie_id")
    private Integer zadanieId;

    @ManyToOne
    @JoinColumn(name = "projekt_id")
    private Projekt projekt;

    @Column(nullable = false, length = 50)
    private String nazwa;

    private Integer kolejnosc;

    @Column(length = 1000)
    private String opis;

    @CreationTimestamp
    @Column(nullable = false, name = "dataczas_dodania")
    private LocalDateTime dataCzasDodania;

    public Integer getZadanieId() {
        return zadanieId;
    }

    public void setZadanieId(Integer zadanieId) {
        this.zadanieId = zadanieId;
    }

    public Projekt getProjekt() {
        return projekt;
    }

    public void setProjekt(Projekt projekt) {
        this.projekt = projekt;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public Integer getKolejnosc() {
        return kolejnosc;
    }

    public void setKolejnosc(Integer kolejnosc) {
        this.kolejnosc = kolejnosc;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public LocalDateTime getDataCzasDodania() {
        return dataCzasDodania;
    }

    public void setDataCzasDodania(LocalDateTime dataCzasDodania) {
        this.dataCzasDodania = dataCzasDodania;
    }

    public Zadanie() {
    }
    public Zadanie(Integer zadanieId) {
        this.zadanieId = zadanieId;
    }

    public Zadanie(String nazwa, String opis, Integer kolejnosc) {
        this.nazwa = nazwa;
        this.opis = opis;
        this.kolejnosc = kolejnosc;
    }





}
