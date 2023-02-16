package com.example.stargo;

import java.util.Random;

public class NivelController {
    boolean juego;
    int nNivel;
    int nAsteroides;
    int nPuntos;
    int puntosPorSegundo;
    int vida;
    int spawnTime;
    float velocidad;
    float minSize;
    float maxSize;
    Ufo ufo;

    public NivelController(Ufo ufo){
        this.juego = true;
        this.nNivel = 1;
        this.nPuntos = 0;
        this.puntosPorSegundo = 10;
        this.vida = 30;
        this.spawnTime = 1200;
        this.nAsteroides = 300;
        this.velocidad = 2;
        this.minSize = 0.20f;
        this.maxSize = 0.22f;
        this.ufo = ufo;
    }


    public int cambiosNivel() {
        switch (nNivel){
            case 2: // Cambio de ratios de spawn
                spawnTime = 1100;
                break;
            case 3: // Cambio de spawn + tamaño
                spawnTime = 900;
                minSize = 0.19f;
                maxSize = 0.20f;
                break;
            case 4: // velocidad + ufo 1.5
                velocidad = velocidad + 0.5f;
                break;
            case 5: // Cambios de spawn
                spawnTime = 700;
                break;
            case 6: // Cambios de tamaño + ufo 2
                velocidad = velocidad + 0.5f;
                minSize = 0.16f;
                maxSize = 0.20f;
                spawnTime = 600;
                break;
            case 7:
                spawnTime = 550;
                break;
            case 8:
                velocidad = velocidad + 0.5f;
                spawnTime = 800;
                minSize = 0.16f;
                maxSize = 0.18f;
                break;
            case 9:
                spawnTime = 500;
                break;
            case 10:
                velocidad = velocidad + 0.5f;
                minSize = 0.14f;
                maxSize = 0.20f;
                spawnTime = 450;
                break;
        }
        return nNivel;
    }
}
