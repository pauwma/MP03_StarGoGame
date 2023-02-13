package com.example.stargo;

public class NivelController {
    boolean juego;
    int nNivel;
    int nAsteroides;
    int nPuntos;
    int puntosPorSegundo;
    int vida;
    int minSpawnTime;
    int maxSpawnTime;
    float velocidad;
    float minSize;
    float maxSize;

    public NivelController(){
        this.juego = true;
        this.nNivel = 1;
        this.nPuntos = 0;
        this.puntosPorSegundo = 10;
        this.vida = 100;
        this.minSpawnTime = 500;
        this.maxSpawnTime = 800;
        this.nAsteroides = 300;
        this.velocidad = 2;
        this.minSize = 0.40f;
        this.maxSize = 0.18f;
    }
}
