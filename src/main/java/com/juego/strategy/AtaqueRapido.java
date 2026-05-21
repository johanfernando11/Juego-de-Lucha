package com.juego.patrones.strategy;

import com.juego.model.Personaje;

import java.util.Random;

/**
 * Estrategia de ataque rápido (ConcreteStrategy del patrón Strategy).
 *
 * Inflige daño moderado pero con alta frecuencia. La idea es que el atacante
 * golpea varias veces en el mismo turno. Para simplificar la simulación,
 * el daño total representa la suma de múltiples golpes rápidos.
 *
 * Rango de daño: 10 — 20 puntos (menor por golpe, mayor cadencia conceptual).
 */
public class AtaqueRapido implements EstrategiaAtaque {

    private static final int DANO_MINIMO = 10;
    private static final int DANO_MAXIMO = 20;
    private static final String NOMBRE = "Ataque Rápido";

    private final Random random;

    /** Constructor estándar. */
    public AtaqueRapido() {
        this.random = new Random();
    }

    /**
     * Constructor con {@link Random} inyectable para tests deterministas.
     *
     * @param random Generador de números aleatorios.
     */
    public AtaqueRapido(Random random) {
        this.random = random;
    }

    /**
     * Calcula el daño del ataque rápido.
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

