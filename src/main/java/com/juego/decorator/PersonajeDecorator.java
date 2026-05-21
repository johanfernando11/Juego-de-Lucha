package com.juego.patrones.decorator;

import com.juego.model.Personaje;
import com.juego.patrones.strategy.EstrategiaAtaque;

/**
 * Clase abstracta base del patrón Decorator para personajes.
 *
 * Permite agregar comportamientos y atributos a un personaje en tiempo de ejecución
 * sin modificar su clase. Cada decorador envuelve un personaje y delega las
 * operaciones base, añadiendo su propia lógica.
 *
 * Patrón Decorator:
 * - Component: {@link Personaje}
 * - ConcreteComponent: {@link com.juego.model.Guerrero}, {@link com.juego.model.Mago}, etc.
 * - Decorator: esta clase abstracta
 * - ConcreteDecorator: {@link EspadaDecorator}, {@link ArmaduraDecorator}
 *
 * Ejemplo de uso:
 * <pre>
 *   Personaje guerrero = new Guerrero("Arthas");
 *   guerrero = new EspadaDecorator(guerrero);     // +15 daño
 *   guerrero = new ArmaduraDecorator(guerrero);   // +30 HP extra
 * </pre>
 */
public abstract class PersonajeDecorator extends Personaje {

    /**
     * El personaje decorado (el que recibirá las mejoras).
     * Mantiene la referencia al componente envuelto.
     */
    protected final Personaje personajeDecorado;

    /**
     * Constructor del decorador base.
     *
     * @param personaje El personaje al que se le añadirán mejoras.
     * @throws IllegalArgumentException si el personaje es nulo.
     */
    protected PersonajeDecorator(Personaje personaje) {
        // Delegamos al constructor de Personaje con los datos del personaje decorado
        super(
            personaje.getNombre(),
            personaje.getVidaMaxima(),
            personaje.getEstrategiaAtaque()
        );
        if (personaje == null) {
            throw new IllegalArgumentException("El personaje a decorar no puede ser nulo.");
        }
        this.personajeDecorado = personaje;
    }

    /**
     * Devuelve el tipo del personaje decorado (no el del decorador).
     * Preserva la identidad del personaje original.
     *
     * @return Tipo del personaje base.
     */
    @Override
    public String getTipo() {
        return personajeDecorado.getTipo();
    }

    /**
     * Permite cambiar la estrategia en el personaje decorado también.
     *
     * @param nuevaEstrategia La nueva estrategia de ataque.
     */
    @Override
    public void setEstrategiaAtaque(EstrategiaAtaque nuevaEstrategia) {
        super.setEstrategiaAtaque(nuevaEstrategia);
        personajeDecorado.setEstrategiaAtaque(nuevaEstrategia);
    }

    /**
     * Devuelve una descripción del equipamiento añadido por este decorador.
     * Cada decorador concreto debe describir su equipamiento.
     *
     * @return Descripción del equipamiento.
     */
    public abstract String getDescripcionEquipamiento();
}

