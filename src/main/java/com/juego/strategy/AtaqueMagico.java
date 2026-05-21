package com.juego.patrones.strategy;

import com.juego.model.Personaje;

import java.util.Random;

/**
 * Estrategia de ataque mágico (ConcreteStrategy del patrón Strategy).
 *
 * El ataque mágico tiene el mayor potencial de daño pero también mayor variabilidad.
 * Representa hechizos de alto impacto que pueden fallar o acertar con fuerza variable.
 *
 * Rango de daño: 15 — 50 puntos (amplia variabilidad: alto riesgo, alta recompensa).
 */
public class AtaqueMagico implements EstrategiaAtaque {

    private static final int DANO_MINIMO = 15;
    private static final int DANO_MAXIMO = 50;
    private static final String NOMBRE = "Ataque Mágico";

    private final Random random;

    /** Constructor estándar. */
    public AtaqueMagico() {
        this.random = new Random();
    }

    /**
     * Constructor con {@link Random} inyectable para tests deterministas.
     *
     * @param random Generador de números aleatorios.
     */
    public AtaqueMagico(Random random) {
        this.random = random;
    }

    /**
     * Calcula el daño del ataque mágico.
     *
     * @param atacante Personaje que ataca.
     * @return Daño entre {@value #DANO_MINIMO} y {@value #DANO_MAXIMO}.
     */
    @Override
    public int calcularDano(Personaje atacante) {
        return DANO_MINIMO + random.nextInt(DANO_MAXIMO - DANO_MINIMO + 1);
    }

    @Override
    public String getNombre() {
        return NOMBRE;
    }
}

