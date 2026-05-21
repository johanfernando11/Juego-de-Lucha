package com.juego.model;

import com.juego.patrones.strategy.EstrategiaAtaque;

/**
 * Clase base que representa un personaje del juego de lucha.
 *
 * Aplica el patrón Strategy: cada personaje tiene una estrategia de ataque
 * intercambiable en tiempo de ejecución, desacoplando el comportamiento de ataque
 * de la clase personaje.
 *
 * Principios SOLID aplicados:
 * - SRP: solo gestiona estado y comportamiento del personaje.
 * - OCP: abierto a extensión (subclases) sin modificar esta clase.
 * - DIP: depende de la abstracción EstrategiaAtaque, no de implementaciones concretas.
 */
public abstract class Personaje {

    /** Nombre identificador del personaje. */
    private final String nombre;

    /** Puntos de vida actuales del personaje (0 = muerto). */
    private int puntosDeVida;

    /** Puntos de vida máximos del personaje. */
    private final int vidaMaxima;

    /**
     * Estrategia de ataque actual del personaje.
     * Puede cambiarse en tiempo de ejecución (patrón Strategy).
     */
    private EstrategiaAtaque estrategiaAtaque;

    /**
     * Constructor base para todos los personajes.
     *
     * @param nombre          Nombre del personaje (no puede ser nulo ni vacío).
     * @param vidaMaxima      Puntos de vida iniciales y máximos.
     * @param estrategiaAtaque Estrategia de ataque inicial.
     * @throws IllegalArgumentException si el nombre es nulo/vacío o vidaMaxima <= 0.
     */
    protected Personaje(String nombre, int vidaMaxima, EstrategiaAtaque estrategiaAtaque) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del personaje no puede ser nulo o vacío.");
        }
        if (vidaMaxima <= 0) {
            throw new IllegalArgumentException("La vida máxima debe ser mayor a 0.");
        }
        if (estrategiaAtaque == null) {
            throw new IllegalArgumentException("La estrategia de ataque no puede ser nula.");
        }
        this.nombre = nombre.trim();
        this.vidaMaxima = vidaMaxima;
        this.puntosDeVida = vidaMaxima;
        this.estrategiaAtaque = estrategiaAtaque;
    }

    /**
     * Ejecuta un ataque sobre el oponente utilizando la estrategia actual.
     * El daño causado depende de la implementación de {@link EstrategiaAtaque}.
     *
     * @param oponente El personaje que recibirá el daño.
     * @throws IllegalArgumentException si el oponente es nulo.
     */
    public void atacar(Personaje oponente) {
        if (oponente == null) {
            throw new IllegalArgumentException("El oponente no puede ser nulo.");
        }
        int dano = estrategiaAtaque.calcularDano(this);
        oponente.recibirDano(dano);
        System.out.printf("[ATAQUE] %s usa '%s' sobre %s causando %d puntos de daño.%n",
                this.nombre, estrategiaAtaque.getNombre(), oponente.getNombre(), dano);
    }

    /**
     * Reduce los puntos de vida del personaje en la cantidad indicada.
     * Los puntos de vida nunca bajan de cero.
     *
     * @param dano Cantidad de daño a recibir (valores negativos son ignorados).
     */
    public void recibirDano(int dano) {
        if (dano < 0) return;
        this.puntosDeVida = Math.max(0, this.puntosDeVida - dano);
    }

    /**
     * Restaura los puntos de vida al máximo.
     * Útil para iniciar nuevas rondas sin recrear el personaje.
     */
    public void restaurarVida() {
        this.puntosDeVida = this.vidaMaxima;
    }

    /**
     * Indica si el personaje sigue vivo (puntos de vida > 0).
     *
     * @return {@code true} si está vivo, {@code false} si fue derrotado.
     */
    public boolean estaVivo() {
        return this.puntosDeVida > 0;
    }

    /**
     * Cambia la estrategia de ataque del personaje en tiempo de ejecución.
     * Este método es el corazón del patrón Strategy.
     *
     * @param nuevaEstrategia La nueva estrategia a utilizar.
     * @throws IllegalArgumentException si la estrategia es nula.
     */
    public void setEstrategiaAtaque(EstrategiaAtaque nuevaEstrategia) {
        if (nuevaEstrategia == null) {
            throw new IllegalArgumentException("La estrategia de ataque no puede ser nula.");
        }
        this.estrategiaAtaque = nuevaEstrategia;
    }

    /**
     * Devuelve el tipo de personaje (Guerrero, Mago, Arquero).
     * Cada subclase debe implementar este método.
     *
     * @return Cadena que describe el tipo de personaje.
     */
    public abstract String getTipo();

    // ──────────────────────────── Getters ────────────────────────────

    public String getNombre()                    { return nombre; }
    public int getPuntosDeVida()                 { return puntosDeVida; }
    public int getVidaMaxima()                   { return vidaMaxima; }
    public EstrategiaAtaque getEstrategiaAtaque(){ return estrategiaAtaque; }

    @Override
    public String toString() {
        return String.format("%s [%s] — HP: %d/%d — Estrategia: %s",
                nombre, getTipo(), puntosDeVida, vidaMaxima, estrategiaAtaque.getNombre());
    }
}

