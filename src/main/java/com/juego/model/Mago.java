package com.juego.model;

import com.juego.patrones.strategy.AtaqueMagico;
import com.juego.patrones.strategy.EstrategiaAtaque;

/**
 * Personaje de tipo Mago.
 *
 * El Mago posee menor resistencia física (80 HP) pero compensa con ataques
 * mágicos de alto daño. Su estrategia predeterminada es {@link AtaqueMagico}.
 *
 * Creado por {@link com.juego.patrones.factory.PersonajeFactory} usando Factory Method.
 */
public class Mago extends Personaje {

    /** Vida máxima del Mago — menor que la del Guerrero pero estratégicamente poderoso. */
    private static final int VIDA_MAXIMA = 80;

    /**
     * Constructor con nombre y estrategia personalizada.
     *
     * @param nombre          Nombre del mago.
     * @param estrategiaAtaque Estrategia de ataque inicial.
     */
    public Mago(String nombre, EstrategiaAtaque estrategiaAtaque) {
        super(nombre, VIDA_MAXIMA, estrategiaAtaque);
    }

    /**
     * Constructor con estrategia mágica por defecto.
     *
     * @param nombre Nombre del mago.
     */
    public Mago(String nombre) {
        super(nombre, VIDA_MAXIMA, new AtaqueMagico());
    }

    /**
     * Devuelve el tipo de personaje.
     *
     * @return "Mago"
     */
    @Override
    public String getTipo() {
        return "Mago";
    }
}

