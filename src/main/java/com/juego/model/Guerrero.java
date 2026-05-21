package com.juego.model;

import com.juego.patrones.strategy.AtaqueFuerte;
import com.juego.patrones.strategy.EstrategiaAtaque;

/**
 * Personaje de tipo Guerrero.
 *
 * El Guerrero es un combatiente cuerpo a cuerpo con alta resistencia (120 HP)
 * y estrategia de ataque fuerte por defecto. Su especialidad es el combate
 * directo absorbiendo y repartiendo grandes cantidades de daño.
 *
 * Creado por {@link com.juego.patrones.factory.PersonajeFactory} usando Factory Method.
 */
public class Guerrero extends Personaje {

    /** Vida máxima del Guerrero — mayor que la de otros personajes. */
    private static final int VIDA_MAXIMA = 120;

    /**
     * Constructor que acepta nombre y estrategia personalizada.
     * Utilizado principalmente en tests y composición con Decorator.
     *
     * @param nombre          Nombre del guerrero.
     * @param estrategiaAtaque Estrategia de ataque a utilizar.
     */
    public Guerrero(String nombre, EstrategiaAtaque estrategiaAtaque) {
        super(nombre, VIDA_MAXIMA, estrategiaAtaque);
    }

    /**
     * Constructor con nombre y estrategia de ataque fuerte por defecto.
     * Es el constructor más usado cuando la Factory crea un Guerrero.
     *
     * @param nombre Nombre del guerrero.
     */
    public Guerrero(String nombre) {
        super(nombre, VIDA_MAXIMA, new AtaqueFuerte());
    }

    /**
     * Devuelve el tipo de personaje.
     *
     * @return "Guerrero"
     */
    @Override
    public String getTipo() {
        return "Guerrero";
    }
}

