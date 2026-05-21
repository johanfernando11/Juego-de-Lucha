package com.juego.patrones.strategy;

import com.juego.model.Personaje;

/**
 * Interfaz del patrón Strategy para los comportamientos de ataque.
 *
 * Define el contrato que todas las estrategias de ataque deben cumplir.
 * Esto permite que los personajes cambien su comportamiento de ataque
 * en tiempo de ejecución sin modificar su código (principio OCP de SOLID).
 *
 * Patrón Strategy:
 * - Context: {@link com.juego.model.Personaje}
 * - Strategy: esta interfaz
 * - ConcreteStrategy: {@link AtaqueFuerte}, {@link AtaqueRapido}, {@link AtaqueMagico}
 */
public interface EstrategiaAtaque {

    /**
     * Calcula el daño que inflige el atacante con esta estrategia.
     *
     * @param atacante El personaje que está ejecutando el ataque.
     *                 Puede usarse para calcular daño basado en atributos del personaje.
     * @return Puntos de daño a infligir (siempre >= 0).
     */
    int calcularDano(Personaje atacante);

    /**
     * Devuelve el nombre descriptivo de la estrategia.
     * Se usa en los mensajes de combate para indicar el tipo de ataque.
     *
     * @return Nombre de la estrategia (ej: "Ataque Fuerte", "Ataque Rápido").
     */
    String getNombre();
}

