package com.juego.patrones.strategy;

import com.juego.model.Personaje;

import java.util.Random;

/**
 * Estrategia de ataque fuerte (ConcreteStrategy del patrón Strategy).
 *
 * Inflige un daño alto pero con algo de variabilidad.
 * Ideal para guerreros que priorizan el poder bruto sobre la velocidad.
 *
 * Rango de daño: 25 — 40 puntos.
 */
public class AtaqueFuerte implements EstrategiaAtaque {

    private static final int DANO_MINIMO = 25;
    private static final int DANO_MAXIMO = 40;
    private static final String NOMBRE = "Ataque Fuerte";

    private final Random random;

    /** Constructor estándar con generador de números aleatorios propio. */
    public AtaqueFuerte() {
        this.random = new Random();
    }

    /**
     * Constructor que permite inyectar un {@link Random} específico.
     * Usado en pruebas unitarias para obtener resultados deterministas.
     *
     * @param random Generador de números aleatorios a utilizar.
     */
    public AtaqueFuerte(Random random) {
        this.random = random;
    }

    /**
     * Calcula el daño del ataque fuerte.
     * El atacante no influye en el cálculo; el daño es fijo por tipo de estrategia.
     *
     * @param atacante Personaje que ataca (no utilizado en este cálculo).
     * @return Daño aleatorio entre {@value #DANO_MINIMO} y {@value #DANO_MAXIMO}.
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

