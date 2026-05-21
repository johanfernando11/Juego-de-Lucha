package com.juego.juego;

import com.juego.model.Personaje;
import com.juego.patrones.decorator.ArmaduraDecorator;
import com.juego.patrones.decorator.EspadaDecorator;
import com.juego.patrones.factory.PersonajeFactory;
import com.juego.patrones.strategy.AtaqueRapido;

/**
 * Clase principal del Juego de Lucha por Turnos.
 *
 * Orquesta el combate entre dos personajes, gestionando:
 * - La creación de personajes mediante Factory Method.
 * - El equipamiento mediante Decorator.
 * - El combate por turnos.
 * - La detección del ganador.
 *
 * Demuestra el uso integrado de los tres patrones de diseño:
 * - Factory Method → crea los personajes.
 * - Strategy → define el comportamiento de ataque.
 * - Decorator → añade equipamiento a los personajes.
 */
public class JuegoLucha {

    /** Número máximo de turnos para evitar combates infinitos. */
    private static final int MAX_TURNOS = 50;

    /**
     * Ejecuta un combate completo entre dos personajes.
     *
     * El combate sigue estas reglas:
     * 1. En cada turno, el jugador 1 ataca primero.
     * 2. Si el jugador 2 sigue vivo, ataca al jugador 1.
     * 3. El combate termina cuando uno de los dos muere o se alcanza el límite de turnos.
     *
     * @param personaje1 El primer combatiente.
     * @param personaje2 El segundo combatiente.
     * @return El personaje ganador, o {@code null} en caso de empate por límite de turnos.
     */
    public Personaje ejecutarCombate(Personaje personaje1, Personaje personaje2) {
        if (personaje1 == null || personaje2 == null) {
            throw new IllegalArgumentException("Los personajes del combate no pueden ser nulos.");
        }

        System.out.println("\n" + "═".repeat(60));
        System.out.println("           ⚔  COMBATE INICIADO  ⚔");
        System.out.println("═".repeat(60));
        System.out.println("🧙 " + personaje1);
        System.out.println("🗡  " + personaje2);
        System.out.println("═".repeat(60) + "\n");

        int turno = 1;

        while (personaje1.estaVivo() && personaje2.estaVivo() && turno <= MAX_TURNOS) {
            System.out.printf("--- Turno %d ---%n", turno);

            // Turno del personaje 1
            personaje1.atacar(personaje2);
            imprimirEstado(personaje1, personaje2);

            // Verificar si personaje2 murió tras el ataque
            if (!personaje2.estaVivo()) break;

            // Turno del personaje 2
            personaje2.atacar(personaje1);
            imprimirEstado(personaje1, personaje2);

            turno++;
            System.out.println();
        }

        return determinarGanador(personaje1, personaje2, turno);
    }

    /**
     * Determina e imprime el ganador del combate.
     *
     * @param p1    Primer personaje.
     * @param p2    Segundo personaje.
     * @param turno Turno en que terminó el combate.
     * @return El ganador, o {@code null} si hubo empate.
     */
    private Personaje determinarGanador(Personaje p1, Personaje p2, int turno) {
        System.out.println("\n" + "═".repeat(60));

        if (!p1.estaVivo() && !p2.estaVivo()) {
            System.out.println("🤝 ¡EMPATE! Ambos personajes cayeron simultáneamente.");
            System.out.println("═".repeat(60));
            return null;
        }

        if (turno > MAX_TURNOS) {
            System.out.println("⏰ Límite de turnos alcanzado. ¡EMPATE POR TIEMPO!");
            System.out.println("═".repeat(60));
            return null;
        }

        Personaje ganador = p1.estaVivo() ? p1 : p2;
        Personaje perdedor = p1.estaVivo() ? p2 : p1;

        System.out.printf("🏆 ¡%s ha ganado el combate en %d turnos!%n", ganador.getNombre(), turno);
        System.out.printf("💀 %s ha sido derrotado con 0 HP restantes.%n", perdedor.getNombre());
        System.out.printf("❤  HP restantes del ganador: %d%n", ganador.getPuntosDeVida());
        System.out.println("═".repeat(60));

        return ganador;
    }

    /**
     * Imprime el estado actual de los dos personajes.
     *
     * @param p1 Primer personaje.
     * @param p2 Segundo personaje.
     */
    private void imprimirEstado(Personaje p1, Personaje p2) {
        System.out.printf("  Estado → %s: %d HP | %s: %d HP%n",
                p1.getNombre(), p1.getPuntosDeVida(),
                p2.getNombre(), p2.getPuntosDeVida());
    }

    /**
     * Método principal que demuestra el juego completo.
     * Crea personajes, los equipa y ejecuta un combate.
     *
     * @param args Argumentos de línea de comandos (no utilizados).
     */
    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════════════════════════╗");
        System.out.println("║        JUEGO DE LUCHA — PATRONES DE DISEÑO              ║");
        System.out.println("║   Factory Method | Strategy | Decorator                 ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝\n");

        // ── Factory Method: crea los personajes ──────────────────────────
        System.out.println("[ FACTORY METHOD ] Creando personajes...");
        Personaje guerrero = PersonajeFactory.crearPersonaje("guerrero", "Arthas");
        Personaje mago     = PersonajeFactory.crearPersonaje("mago", "Gandalf");

        System.out.println("  → Creado: " + guerrero);
        System.out.println("  → Creado: " + mago);
        System.out.println();

        // ── Decorator: equipar al guerrero con espada y armadura ─────────
        System.out.println("[ DECORATOR ] Equipando a Arthas...");
        guerrero = new EspadaDecorator(guerrero);
        guerrero = new ArmaduraDecorator(guerrero);
        System.out.println();

        // ── Strategy: cambiar la estrategia de Gandalf mid-game ─────────
        System.out.println("[ STRATEGY ] Gandalf cambia a Ataque Rápido...");
        mago.setEstrategiaAtaque(new AtaqueRapido());
        System.out.println("  → Nueva estrategia: " + mago.getEstrategiaAtaque().getNombre());
        System.out.println();

        // ── Ejecutar el combate ──────────────────────────────────────────
        JuegoLucha juego = new JuegoLucha();
        Personaje ganador = juego.ejecutarCombate(guerrero, mago);

        // ── Resultado final ──────────────────────────────────────────────
        System.out.println("\n[ RESULTADO FINAL ]");
        if (ganador != null) {
            System.out.println("  El campeón es: " + ganador.getNombre() + " (" + ganador.getTipo() + ")");
        } else {
            System.out.println("  El combate terminó en empate.");
        }
    }
}

