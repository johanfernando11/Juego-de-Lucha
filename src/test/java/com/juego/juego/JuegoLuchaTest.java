package com.juego.juego;

import com.juego.model.Guerrero;
import com.juego.model.Mago;
import com.juego.model.Personaje;
import com.juego.patrones.decorator.ArmaduraDecorator;
import com.juego.patrones.decorator.EspadaDecorator;
import com.juego.patrones.factory.PersonajeFactory;
import com.juego.patrones.strategy.EstrategiaAtaque;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tests de integración y unitarios para JuegoLucha.
 * Verifica el flujo completo del combate, condiciones de victoria y empate.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Tests de JuegoLucha")
class JuegoLuchaTest {

    private JuegoLucha juego;

    @Mock
    private EstrategiaAtaque estrategiaMock1;

    @Mock
    private EstrategiaAtaque estrategiaMock2;

    @BeforeEach
    void setUp() {
        juego = new JuegoLucha();
    }

    // ── Validaciones de entrada ───────────────────────────────────────────

    @Test
    @DisplayName("Combate con personaje nulo debe lanzar excepción")
    void testCombatePersonajeNulo() {
        Guerrero g = new Guerrero("Thor");
        assertThrows(IllegalArgumentException.class,
                () -> juego.ejecutarCombate(null, g));
        assertThrows(IllegalArgumentException.class,
                () -> juego.ejecutarCombate(g, null));
    }

    // ── Resultados del combate ────────────────────────────────────────────

    @Test
    @DisplayName("Personaje 1 debe ganar si personaje 2 tiene 0 HP")
    void testGanador_Personaje1() {
        // P1 muy fuerte: mata en 1 golpe
        when(estrategiaMock1.calcularDano(any())).thenReturn(999);
        when(estrategiaMock1.getNombre()).thenReturn("Golpe Mortal");

        // P2 débil: no hace daño relevante
        when(estrategiaMock2.calcularDano(any())).thenReturn(1);
        when(estrategiaMock2.getNombre()).thenReturn("Cosquillas");

        Personaje p1 = new Guerrero("Heroe", estrategiaMock1);
        Personaje p2 = new Mago("Villano", estrategiaMock2);

        Personaje ganador = juego.ejecutarCombate(p1, p2);

        assertNotNull(ganador);
        assertEquals("Heroe", ganador.getNombre());
        assertFalse(p2.estaVivo());
    }

    @Test
    @DisplayName("Personaje 2 debe ganar si personaje 1 muere primero")
    void testGanador_Personaje2() {
        // P1 debil
        when(estrategiaMock1.calcularDano(any())).thenReturn(1);
        when(estrategiaMock1.getNombre()).thenReturn("Golpe Suave");

        // P2 letal
        when(estrategiaMock2.calcularDano(any())).thenReturn(999);
        when(estrategiaMock2.getNombre()).thenReturn("Golpe Mortal");

        Personaje p1 = new Guerrero("Perdedor", estrategiaMock1);
        Personaje p2 = new Mago("Campeón", estrategiaMock2);

        Personaje ganador = juego.ejecutarCombate(p1, p2);

        assertNotNull(ganador);
        assertEquals("Campeón", ganador.getNombre());
        assertFalse(p1.estaVivo());
    }

    @Test
    @DisplayName("Combate debe terminar con un ganador cuando HP llega a 0")
    void testCombateTerminaConGanador() {
        when(estrategiaMock1.calcularDano(any())).thenReturn(30);
        when(estrategiaMock1.getNombre()).thenReturn("Ataque30");
        when(estrategiaMock2.calcularDano(any())).thenReturn(15);
        when(estrategiaMock2.getNombre()).thenReturn("Ataque15");

        Personaje p1 = new Guerrero("G1", estrategiaMock1); // HP 120
        Personaje p2 = new Guerrero("G2", estrategiaMock2); // HP 120

        Personaje ganador = juego.ejecutarCombate(p1, p2);

        // Alguien debe ganar (no empate)
        assertNotNull(ganador);
        assertTrue(ganador.estaVivo());
    }

    // ── Flujo completo con Factory + Decorator ────────────────────────────

    @Test
    @DisplayName("Combate completo con Factory y Decorator debe funcionar sin errores")
    void testCombateCompletoConPatrones() {
        Personaje guerrero = PersonajeFactory.crearPersonaje("guerrero", "Arthas");
        Personaje mago     = PersonajeFactory.crearPersonaje("mago", "Gandalf");

        guerrero = new EspadaDecorator(guerrero);
        guerrero = new ArmaduraDecorator(guerrero);

        assertDoesNotThrow(() -> juego.ejecutarCombate(guerrero, mago));
    }

    @Test
    @DisplayName("Ganador del combate debe estar vivo al final")
    void testGanadorEstaVivo() {
        when(estrategiaMock1.calcularDano(any())).thenReturn(50);
        when(estrategiaMock1.getNombre()).thenReturn("SuperAtaque");
        when(estrategiaMock2.calcularDano(any())).thenReturn(1);
        when(estrategiaMock2.getNombre()).thenReturn("Debil");

        Personaje p1 = new Guerrero("Fuerte", estrategiaMock1);
        Personaje p2 = new Mago("Debil", estrategiaMock2);

        Personaje ganador = juego.ejecutarCombate(p1, p2);

        assertNotNull(ganador);
        assertTrue(ganador.estaVivo());
        assertTrue(ganador.getPuntosDeVida() > 0);
    }

    @Test
    @DisplayName("Perdedor del combate debe estar muerto al final")
    void testPerdedorEstamuerto() {
        when(estrategiaMock1.calcularDano(any())).thenReturn(999);
        when(estrategiaMock1.getNombre()).thenReturn("Matar");
        when(estrategiaMock2.calcularDano(any())).thenReturn(1);
        when(estrategiaMock2.getNombre()).thenReturn("Debil");

        Personaje p1 = new Guerrero("Asesino", estrategiaMock1);
        Personaje p2 = new Mago("Victima", estrategiaMock2);

        juego.ejecutarCombate(p1, p2);

        assertFalse(p2.estaVivo());
        assertEquals(0, p2.getPuntosDeVida());
    }

    // ── Combate con todos los tipos de personaje ──────────────────────────

    @Test
    @DisplayName("Combate Guerrero vs Mago debe completarse")
    void testCombateGuerreroVsMago() {
        Personaje g = PersonajeFactory.crearPersonaje("guerrero", "Thor");
        Personaje m = PersonajeFactory.crearPersonaje("mago", "Merlin");
        assertDoesNotThrow(() -> juego.ejecutarCombate(g, m));
    }

    @Test
    @DisplayName("Combate Arquero vs Guerrero debe completarse")
    void testCombateArqueroVsGuerrero() {
        Personaje a = PersonajeFactory.crearPersonaje("arquero", "Legolas");
        Personaje g = PersonajeFactory.crearPersonaje("guerrero", "Gimli");
        assertDoesNotThrow(() -> juego.ejecutarCombate(a, g));
    }

    @Test
    @DisplayName("Combate Mago vs Arquero debe completarse")
    void testCombateMagoVsArquero() {
        Personaje m = PersonajeFactory.crearPersonaje("mago", "Gandalf");
        Personaje a = PersonajeFactory.crearPersonaje("arquero", "Hawkeye");
        assertDoesNotThrow(() -> juego.ejecutarCombate(m, a));
    }
}

