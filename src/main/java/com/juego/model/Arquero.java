package com.juego.model;

import com.juego.patrones.strategy.AtaqueRapido;
import com.juego.patrones.strategy.EstrategiaAtaque;

/**
 * Personaje de tipo Arquero.
 *
 * El Arquero tiene resistencia media (100 HP) y se especializa en ataques
 * rápidos y a distancia. Su ventaja es la velocidad: múltiples golpes leves
 * acumulan daño significativo. Estrategia predeterminada: {@link AtaqueRapido}.
 *
 * Creado por {@link com.juego.patrones.factory.PersonajeFactory} usando Factory Method.
 */
public class Arquero extends Personaje {

    /** Vida máxima del Arquero — equilibrado entre resistencia y velocidad. */
    private static final int VIDA_MAXIMA = 100;

    /**
     * Constructor con nombre y estrategia personalizada.
     *
     * @param nombre          Nombre del arquero.
     * @param estrategiaAtaque Estrategia de ataque inicial.
     */
    public Arquero(String nombre, EstrategiaAtaque estrategiaAtaque) {
        super(nombre, VIDA_MAXIMA, estrategiaAtaque);
    }

    /**
     * Constructor con estrategia de ataque rápido por defecto.
     *
     * @param nombre Nombre del arquero.
     */
    public Arquero(String nombre) {
        super(nombre, VIDA_MAXIMA, new AtaqueRapido());
    }

    /**
     * Devuelve el tipo de personaje.
     *
     * @return "Arquero"
     */
    @Override
    public String getTipo() {
        return "Arquero";
    }
}

