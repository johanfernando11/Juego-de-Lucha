package com.juego.model;

import com.juego.patrones.strategy.AtaqueFuerte;
import com.juego.patrones.strategy.AtaqueRapido;
import com.juego.patrones.strategy.EstrategiaAtaque;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tests unitarios para la clase Personaje y sus subclases.
 * Cubre: creación, ataque, recibir daño, vitalidad y cambio de estrategia.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Tests de Personaje")
class PersonajeTest {

    private Guerrero guerrero;
    private Mago mago;
    private Arquero arquero;

    @Mock
    private EstrategiaAtaque estrategiaMock;

    @BeforeEach
    void setUp() {
        guerrero = new Guerrero("Thor");
        mago     = new Mago("Gandalf");
        arquero  = new Arquero("Legolas");
    }

    // ── Creación de personajes ───────────────────────────────────────────

    @Test
    @DisplayName("Guerrero debe crearse con nombre y 120 HP")
    void testCreacionGuerrero() {
        assertEquals("Thor", guerrero.getNombre());
        assertEquals(120, guerrero.getPuntosDeVida());
        assertEquals(120, guerrero.getVidaMaxima());
        assertTrue(guerrero.estaVivo());
        assertEquals("Guerrero", guerrero.getTipo());
    }

    @Test
    @DisplayName("Mago debe crearse con nombre y 80 HP")
    void testCreacionMago() {
        assertEquals("Gandalf", mago.getNombre());
        assertEquals(80, mago.getPuntosDeVida());
        assertEquals("Mago", mago.getTipo());
        assertTrue(mago.estaVivo());
    }

    @Test
    @DisplayName("Arquero debe crearse con nombre y 100 HP")
    void testCreacionArquero() {
        assertEquals("Legolas", arquero.getNombre());
        assertEquals(100, arquero.getPuntosDeVida());
        assertEquals("Arquero", arquero.getTipo());
        assertTrue(arquero.estaVivo());
    }

    @Test
    @DisplayName("toString debe incluir nombre, tipo e HP")
    void testToString() {
        String resultado = guerrero.toString();
        assertTrue(resultado.contains("Thor"));
        assertTrue(resultado.contains("Guerrero"));
        assertTrue(resultado.contains("120"));
    }

    // ── Validaciones de constructor ──────────────────────────────────────

    @Test
    @DisplayName("Debe lanzar excepción con nombre nulo")
    void testConstructorNombreNulo() {
        assertThrows(IllegalArgumentException.class,
                () -> new Guerrero(null));
    }

    @Test
    @DisplayName("Debe lanzar excepción con nombre vacío")
    void testConstructorNombreVacio() {
        assertThrows(IllegalArgumentException.class,
                () -> new Guerrero("   "));
    }

    @Test
    @DisplayName("Constructor con estrategia personalizada debe funcionar")
    void testConstructorConEstrategia() {
        Guerrero g = new Guerrero("Arthas", new AtaqueRapido());
        assertNotNull(g.getEstrategiaAtaque());
        assertEquals("Ataque Rápido", g.getEstrategiaAtaque().getNombre());
    }

    // ── Recibir daño ─────────────────────────────────────────────────────

    @Test
    @DisplayName("Debe reducir HP al recibir daño")
    void testRecibirDano() {
        guerrero.recibirDano(30);
        assertEquals(90, guerrero.getPuntosDeVida());
    }

    @Test
    @DisplayName("HP no debe ser negativo al recibir daño excesivo")
    void testHpNuncaNegativo() {
        guerrero.recibirDano(999);
        assertEquals(0, guerrero.getPuntosDeVida());
        assertFalse(guerrero.estaVivo());
    }

    @Test
    @DisplayName("Daño negativo debe ser ignorado")
    void testDanoNegativoIgnorado() {
        guerrero.recibirDano(-50);
        assertEquals(120, guerrero.getPuntosDeVida());
    }

    @Test
    @DisplayName("Daño cero no debe cambiar HP")
    void testDanoCero() {
        guerrero.recibirDano(0);
        assertEquals(120, guerrero.getPuntosDeVida());
    }

    @Test
    @DisplayName("Daño exacto igual a HP debe dejar personaje muerto")
    void testDanoExactoMata() {
        guerrero.recibirDano(120);
        assertEquals(0, guerrero.getPuntosDeVida());
        assertFalse(guerrero.estaVivo());
    }

    // ── Restaurar vida ───────────────────────────────────────────────────

    @Test
    @DisplayName("Restaurar vida debe regresar al máximo")
    void testRestaurarVida() {
        guerrero.recibirDano(80);
        assertEquals(40, guerrero.getPuntosDeVida());
        guerrero.restaurarVida();
        assertEquals(120, guerrero.getPuntosDeVida());
    }

    // ── Ataque con Strategy mock ─────────────────────────────────────────

    @Test
    @DisplayName("Atacar debe usar la estrategia y causar daño al oponente")
    void testAtacarUsaEstrategia() {
        when(estrategiaMock.calcularDano(guerrero)).thenReturn(25);
        when(estrategiaMock.getNombre()).thenReturn("MockAtaque");

        guerrero.setEstrategiaAtaque(estrategiaMock);
        guerrero.atacar(mago);

        assertEquals(55, mago.getPuntosDeVida()); // 80 - 25
        verify(estrategiaMock, times(1)).calcularDano(guerrero);
    }

    @Test
    @DisplayName("Atacar con oponente nulo debe lanzar excepción")
    void testAtacarOponenteNulo() {
        assertThrows(IllegalArgumentException.class,
                () -> guerrero.atacar(null));
    }

    // ── Cambio de estrategia ─────────────────────────────────────────────

    @Test
    @DisplayName("Cambiar estrategia debe actualizar el ataque")
    void testCambioDeEstrategia() {
        guerrero.setEstrategiaAtaque(new AtaqueRapido());
        assertEquals("Ataque Rápido", guerrero.getEstrategiaAtaque().getNombre());
    }

    @Test
    @DisplayName("Cambiar estrategia a nulo debe lanzar excepción")
    void testCambioEstrategiaNula() {
        assertThrows(IllegalArgumentException.class,
                () -> guerrero.setEstrategiaAtaque(null));
    }

    @Test
    @DisplayName("La estrategia puede cambiarse múltiples veces")
    void testMultiplesCambiosEstrategia() {
        guerrero.setEstrategiaAtaque(new AtaqueRapido());
        assertEquals("Ataque Rápido", guerrero.getEstrategiaAtaque().getNombre());

        guerrero.setEstrategiaAtaque(new AtaqueFuerte());
        assertEquals("Ataque Fuerte", guerrero.getEstrategiaAtaque().getNombre());
    }
}

