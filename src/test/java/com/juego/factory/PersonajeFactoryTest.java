package com.juego.patrones;

import com.juego.model.Arquero;
import com.juego.model.Guerrero;
import com.juego.model.Mago;
import com.juego.model.Personaje;
import com.juego.patrones.factory.PersonajeFactory;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitarios para PersonajeFactory (patrón Factory Method).
 * Verifica creación correcta, tipos, nombres y manejo de errores.
 */
@DisplayName("Tests de PersonajeFactory (Factory Method)")
class PersonajeFactoryTest {

    // ── Creación exitosa ─────────────────────────────────────────────────

    @Test
    @DisplayName("Debe crear un Guerrero con el nombre correcto")
    void testCrearGuerrero() {
        Personaje p = PersonajeFactory.crearPersonaje("guerrero", "Arthas");
        assertNotNull(p);
        assertInstanceOf(Guerrero.class, p);
        assertEquals("Arthas", p.getNombre());
        assertEquals("Guerrero", p.getTipo());
        assertEquals(120, p.getPuntosDeVida());
    }

    @Test
    @DisplayName("Debe crear un Mago con el nombre correcto")
    void testCrearMago() {
        Personaje p = PersonajeFactory.crearPersonaje("mago", "Gandalf");
        assertNotNull(p);
        assertInstanceOf(Mago.class, p);
        assertEquals("Gandalf", p.getNombre());
        assertEquals("Mago", p.getTipo());
        assertEquals(80, p.getPuntosDeVida());
    }

    @Test
    @DisplayName("Debe crear un Arquero con el nombre correcto")
    void testCrearArquero() {
        Personaje p = PersonajeFactory.crearPersonaje("arquero", "Legolas");
        assertNotNull(p);
        assertInstanceOf(Arquero.class, p);
        assertEquals("Legolas", p.getNombre());
        assertEquals("Arquero", p.getTipo());
        assertEquals(100, p.getPuntosDeVida());
    }

    // ── Insensibilidad a mayúsculas ───────────────────────────────────────

    @ParameterizedTest
    @ValueSource(strings = {"GUERRERO", "Guerrero", "guerrero", "GuErReRo"})
    @DisplayName("Debe crear Guerrero con distintas mayúsculas/minúsculas")
    void testCrearGuerreroInsensible(String tipo) {
        Personaje p = PersonajeFactory.crearPersonaje(tipo, "Test");
        assertInstanceOf(Guerrero.class, p);
    }

    @ParameterizedTest
    @ValueSource(strings = {"MAGO", "Mago", "mago"})
    @DisplayName("Debe crear Mago con distintas mayúsculas/minúsculas")
    void testCrearMagoInsensible(String tipo) {
        Personaje p = PersonajeFactory.crearPersonaje(tipo, "Test");
        assertInstanceOf(Mago.class, p);
    }

    // ── Errores esperados ─────────────────────────────────────────────────

    @Test
    @DisplayName("Tipo desconocido debe lanzar IllegalArgumentException")
    void testTipoDesconocido() {
        assertThrows(IllegalArgumentException.class,
                () -> PersonajeFactory.crearPersonaje("paladin", "Uther"));
    }

    @Test
    @DisplayName("Tipo nulo debe lanzar IllegalArgumentException")
    void testTipoNulo() {
        assertThrows(IllegalArgumentException.class,
                () -> PersonajeFactory.crearPersonaje(null, "Alguien"));
    }

    @Test
    @DisplayName("Tipo vacío debe lanzar IllegalArgumentException")
    void testTipoVacio() {
        assertThrows(IllegalArgumentException.class,
                () -> PersonajeFactory.crearPersonaje("  ", "Alguien"));
    }

    @Test
    @DisplayName("Nombre nulo en personaje debe lanzar IllegalArgumentException")
    void testNombreNulo() {
        assertThrows(IllegalArgumentException.class,
                () -> PersonajeFactory.crearPersonaje("guerrero", null));
    }

    // ── Tipos disponibles ─────────────────────────────────────────────────

    @Test
    @DisplayName("getTiposDisponibles debe devolver los tres tipos")
    void testGetTiposDisponibles() {
        String[] tipos = PersonajeFactory.getTiposDisponibles();
        assertEquals(3, tipos.length);
        assertArrayEquals(new String[]{"guerrero", "mago", "arquero"}, tipos);
    }

    @Test
    @DisplayName("La Factory no debe ser instanciable")
    void testFactoryNoInstanciable() {
        assertThrows(UnsupportedOperationException.class, () -> {
            var constructor = PersonajeFactory.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            try {
                constructor.newInstance();
            } catch (java.lang.reflect.InvocationTargetException e) {
                throw (UnsupportedOperationException) e.getCause();
            }
        });
    }

    // ── Personajes independientes ─────────────────────────────────────────

    @Test
    @DisplayName("Dos personajes creados deben ser instancias independientes")
    void testPersonajesIndependientes() {
        Personaje p1 = PersonajeFactory.crearPersonaje("guerrero", "Uno");
        Personaje p2 = PersonajeFactory.crearPersonaje("guerrero", "Dos");
        assertNotSame(p1, p2);
        p1.recibirDano(50);
        assertEquals(70, p1.getPuntosDeVida());
        assertEquals(120, p2.getPuntosDeVida()); // p2 no debe verse afectado
    }
}

