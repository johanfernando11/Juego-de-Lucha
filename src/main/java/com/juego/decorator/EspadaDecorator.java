package com.juego.patrones.decorator;

import com.juego.model.Personaje;

/**
 * Decorador que equipa al personaje con una Espada Legendaria.
 *
 * Añade un bonus de daño fijo a cada ataque del personaje.
 * Se puede combinar con otros decoradores para efectos acumulativos.
 *
 * Efecto: +{@value #BONUS_DANO} puntos de daño adicional por ataque.
 *
 * Uso:
 * <pre>
 *   Personaje guerrero = new Guerrero("Arthas");
 *   guerrero = new EspadaDecorator(guerrero);
 *   // Ahora cada ataque hace 15 puntos más de daño
 * </pre>
 */
public class EspadaDecorator extends PersonajeDecorator {

    /** Daño adicional que añade la espada a cada ataque. */
    private static final int BONUS_DANO = 15;

    /** Nombre del equipamiento. */
    private static final String NOMBRE_ESPADA = "Espada Legendaria";

    /**
     * Crea un decorador de espada para el personaje dado.
     *
     * @param personaje El personaje al que se le equipará la espada.
     */
    public EspadaDecorator(Personaje personaje) {
        super(personaje);
        System.out.printf("[EQUIPAMIENTO] %s equipa %s (+%d daño por ataque)%n",
                personaje.getNombre(), NOMBRE_ESPADA, BONUS_DANO);
    }

    /**
     * Ataca al oponente con el daño base más el bonus de la espada.
     *
     * Primero delega el ataque al personaje decorado (que aplica su estrategia),
     * luego aplica el daño adicional de la espada directamente.
     *
     * @param oponente El personaje que recibirá el daño.
     */
    @Override
    public void atacar(Personaje oponente) {
        // El ataque base del personaje decorado
        personajeDecorado.atacar(oponente);
        // Daño adicional de la espada
        oponente.recibirDano(BONUS_DANO);
        System.out.printf("[%s] ¡%s causa %d puntos de daño adicional con la %s!%n",
                NOMBRE_ESPADA, getNombre(), BONUS_DANO, NOMBRE_ESPADA);
    }

    /**
     * Reduce HP del personaje decorado cuando recibe daño.
     *
     * @param dano Cantidad de daño a recibir.
     */
    @Override
    public void recibirDano(int dano) {
        personajeDecorado.recibirDano(dano);
        // Sincronizar HP con el personaje decorado
        super.recibirDano(dano);
    }

    /**
     * Devuelve si el personaje decorado sigue vivo.
     * La espada no afecta la vitalidad, solo la delega.
     *
     * @return {@code true} si el personaje decorado está vivo.
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
     * Devuelve el bonus de daño que añade la espada.
     *
     * @return Bonus de daño ({@value #BONUS_DANO} puntos).
     */
    public int getBonusDano() {
        return BONUS_DANO;
    }

    @Override
    public String getDescripcionEquipamiento() {
        return NOMBRE_ESPADA + " (+" + BONUS_DANO + " daño)";
    }

    @Override
    public String toString() {
        return personajeDecorado.toString() + " [+" + NOMBRE_ESPADA + "]";
    }
}

