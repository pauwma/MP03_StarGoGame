package com.example.stargo;

public class NivelController {
    boolean juego;
    int nNivel;
    int nAsteroides;
    int nPuntos;
    int vida;
    int minSpawnTime;
    int maxSpawnTime;

    public NivelController(){
        this.juego = true;
        this.nNivel = 1;
        this.nPuntos = 0;
        this.vida = 100;
        this.minSpawnTime = 1000;
        this.maxSpawnTime = 2000;
        this.nAsteroides = 10;
    }
}
