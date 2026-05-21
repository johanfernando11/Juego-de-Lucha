package com.juego.patrones.decorator;

import com.juego.model.Personaje;

/**
 * Decorador que equipa al personaje con una Armadura Legendaria.
 *
 * Reduce el daño recibido en cada ataque, representando la protección física.
 * La reducción tiene un mínimo para garantizar que siempre se reciba al menos 1 punto.
 *
 * Efecto: Reduce el daño recibido en {@value #REDUCCION_DANO} puntos por ataque.
 *
 * Uso:
 * <pre>
 *   Personaje guerrero = new Guerrero("Arthas");
 *   guerrero = new ArmaduraDecorator(guerrero);
 *   // Ahora cada ataque le causa 10 puntos menos de daño (mínimo 1)
 * </pre>
 */
public class ArmaduraDecorator extends PersonajeDecorator {

    /** Puntos de daño que absorbe la armadura por cada ataque recibido. */
    private static final int REDUCCION_DANO = 10;

    /** Daño mínimo que siempre pasa la armadura (evita invulnerabilidad total). */
    private static final int DANO_MINIMO_RECIBIDO = 1;

    /** Nombre del equipamiento. */
    private static final String NOMBRE_ARMADURA = "Armadura Legendaria";

    /**
     * Crea un decorador de armadura para el personaje dado.
     *
     * @param personaje El personaje al que se le equipará la armadura.
     */
    public ArmaduraDecorator(Personaje personaje) {
        super(personaje);
        System.out.printf("[EQUIPAMIENTO] %s equipa %s (-%d daño recibido)%n",
                personaje.getNombre(), NOMBRE_ARMADURA, REDUCCION_DANO);
    }

    /**
     * Recibe daño reducido gracias a la armadura.
     *
     * La armadura absorbe {@value #REDUCCION_DANO} puntos de cada ataque,
     * pero siempre se recibe al menos {@value #DANO_MINIMO_RECIBIDO} punto.
     *
     * @param dano Daño original antes de aplicar la reducción de armadura.
     */
    @Override
    public void recibirDano(int dano) {
        if (dano < 0) return;
        int danoReducido = Math.max(DANO_MINIMO_RECIBIDO, dano - REDUCCION_DANO);
        System.out.printf("[%s] La armadura de %s absorbe %d puntos de daño (%d → %d)%n",
                NOMBRE_ARMADURA, getNombre(), (dano - danoReducido), dano, danoReducido);
        personajeDecorado.recibirDano(danoReducido);
        super.recibirDano(danoReducido);
    }

    /**
     * Delega el ataque al personaje decorado sin modificaciones.
     *
     * @param oponente Personaje que recibirá el ataque.
     */
    @Override
    public void atacar(Personaje oponente) {
        personajeDecorado.atacar(oponente);
    }

    /**
     * Devuelve si el personaje decorado sigue vivo.
     *
     * @return {@code true} si está vivo.
     */
    @Override
    public boolean estaVivo() {
        return personajeDecorado.estaVivo();
    }

    /**
     * Devuelve los HP actuales del personaje decorado.
     *
     * @return Puntos de vida del personaje base.
     */
    @Override
    public int getPuntosDeVida() {
        return personajeDecorado.getPuntosDeVida();
    }

    /**
     * Devuelve la reducción de daño que proporciona la armadura.
     *
     * @return Reducción de daño ({@value #REDUCCION_DANO} puntos).
     */
    public int getReduccionDano() {
        return REDUCCION_DANO;
    }

    @Override
    public String getDescripcionEquipamiento() {
        return NOMBRE_ARMADURA + " (-" + REDUCCION_DANO + " daño recibido)";
    }

    @Override
    public String toString() {
        return personajeDecorado.toString() + " [+" + NOMBRE_ARMADURA + "]";
    }
}

