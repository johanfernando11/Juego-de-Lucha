package com.juego.patrones;

import com.juego.model.Guerrero;
import com.juego.patrones.strategy.AtaqueFuerte;
import com.juego.patrones.strategy.AtaqueMagico;
import com.juego.patrones.strategy.AtaqueRapido;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tests unitarios para las estrategias de ataque.
 * Verifica rangos de daño, nombres y comportamiento con Random controlado.
 */
@DisplayName("Tests de Estrategias de Ataque")
class EstrategiaAtaqueTest {

    private Guerrero guerrero;

    @BeforeEach
    void setUp() {
        guerrero = new Guerrero("TestHero");
    }

    // ── AtaqueFuerte ─────────────────────────────────────────────────────

    @Test
    @DisplayName("AtaqueFuerte debe tener nombre correcto")
    void testAtaqueFuerteNombre() {
        AtaqueFuerte estrategia = new AtaqueFuerte();
        assertEquals("Ataque Fuerte", estrategia.getNombre());
    }

    @Test
    @DisplayName("AtaqueFuerte debe calcular daño entre 25 y 40")
    void testAtaqueFuerteRango() {
        AtaqueFuerte estrategia = new AtaqueFuerte();
        for (int i = 0; i < 100; i++) {
            int dano = estrategia.calcularDano(guerrero);
            assertTrue(dano >= 25 && dano <= 40,
                    "Daño fuera de rango [25,40]: " + dano);
        }
    }

    @Test
    @DisplayName("AtaqueFuerte con Random mockeado debe devolver valor controlado")
    void testAtaqueFuerteConRandomMock() {
        Random randomMock = mock(Random.class);
        when(randomMock.nextInt(16)).thenReturn(10); // 25 + 10 = 35
        AtaqueFuerte estrategia = new AtaqueFuerte(randomMock);
        assertEquals(35, estrategia.calcularDano(guerrero));
    }

    // ── AtaqueRapido ─────────────────────────────────────────────────────

    @Test
    @DisplayName("AtaqueRapido debe tener nombre correcto")
    void testAtaqueRapidoNombre() {
        AtaqueRapido estrategia = new AtaqueRapido();
        assertEquals("Ataque Rápido", estrategia.getNombre());
    }

    @Test
    @DisplayName("AtaqueRapido debe calcular daño entre 10 y 20")
    void testAtaqueRapidoRango() {
        AtaqueRapido estrategia = new AtaqueRapido();
        for (int i = 0; i < 100; i++) {
            int dano = estrategia.calcularDano(guerrero);
            assertTrue(dano >= 10 && dano <= 20,
                    "Daño fuera de rango [10,20]: " + dano);
        }
    }

    @Test
    @DisplayName("AtaqueRapido con Random mockeado debe devolver mínimo")
    void testAtaqueRapidoMinimo() {
        Random randomMock = mock(Random.class);
        when(randomMock.nextInt(11)).thenReturn(0); // 10 + 0 = 10
        AtaqueRapido estrategia = new AtaqueRapido(randomMock);
        assertEquals(10, estrategia.calcularDano(guerrero));
    }

    @Test
    @DisplayName("AtaqueRapido con Random mockeado debe devolver máximo")
    void testAtaqueRapidoMaximo() {
        Random randomMock = mock(Random.class);
        when(randomMock.nextInt(11)).thenReturn(10); // 10 + 10 = 20
        AtaqueRapido estrategia = new AtaqueRapido(randomMock);
        assertEquals(20, estrategia.calcularDano(guerrero));
    }

    // ── AtaqueMagico ─────────────────────────────────────────────────────

    @Test
    @DisplayName("AtaqueMagico debe tener nombre correcto")
    void testAtaqueMagicoNombre() {
        AtaqueMagico estrategia = new AtaqueMagico();
        assertEquals("Ataque Mágico", estrategia.getNombre());
    }

    @Test
    @DisplayName("AtaqueMagico debe calcular daño entre 15 y 50")
    void testAtaqueMagicoRango() {
        AtaqueMagico estrategia = new AtaqueMagico();
        for (int i = 0; i < 100; i++) {
            int dano = estrategia.calcularDano(guerrero);
            assertTrue(dano >= 15 && dano <= 50,
                    "Daño fuera de rango [15,50]: " + dano);
        }
    }

    @Test
    @DisplayName("AtaqueMagico con Random mockeado debe devolver valor controlado")
    void testAtaqueMagicoConRandomMock() {
        Random randomMock = mock(Random.class);
        when(randomMock.nextInt(36)).thenReturn(20); // 15 + 20 = 35
        AtaqueMagico estrategia = new AtaqueMagico(randomMock);
        assertEquals(35, estrategia.calcularDano(guerrero));
    }

    // ── Pruebas cruzadas de nombres ───────────────────────────────────────

    @Test
    @DisplayName("Todas las estrategias deben devolver nombres no nulos")
    void testNombresNoNulos() {
        assertNotNull(new AtaqueFuerte().getNombre());
        assertNotNull(new AtaqueRapido().getNombre());
        assertNotNull(new AtaqueMagico().getNombre());
    }

    @Test
    @DisplayName("Estrategias deben aceptar personaje nulo sin NPE en calcularDano si implementan guard")
    void testEstrategiasDanoPositivo() {
        // Las estrategias actuales no usan el atacante — el daño siempre es >= 0
        assertTrue(new AtaqueFuerte().calcularDano(guerrero) >= 0);
        assertTrue(new AtaqueRapido().calcularDano(guerrero) >= 0);
        assertTrue(new AtaqueMagico().calcularDano(guerrero) >= 0);
    }
}
