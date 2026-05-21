package com.juego.patrones;

import com.juego.model.Guerrero;
import com.juego.model.Mago;
import com.juego.model.Personaje;
import com.juego.patrones.decorator.ArmaduraDecorator;
import com.juego.patrones.decorator.EspadaDecorator;
import com.juego.patrones.strategy.EstrategiaAtaque;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tests unitarios para los decoradores EspadaDecorator y ArmaduraDecorator.
 * Verifica que el patrón Decorator aplique correctamente los efectos sin
 * romper el comportamiento base del personaje.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Tests de Decorators")
class DecoratorTest {

    private Guerrero guerrero;
    private Mago mago;

    @BeforeEach
    void setUp() {
        guerrero = new Guerrero("Arthas");
        mago     = new Mago("Jaina");
    }

    // ── EspadaDecorator ───────────────────────────────────────────────────

    @Test
    @DisplayName("EspadaDecorator debe preservar el nombre del personaje")
    void testEspadaPreservaNombre() {
        EspadaDecorator guerreroConEspada = new EspadaDecorator(guerrero);
        assertEquals("Arthas", guerreroConEspada.getNombre());
    }

    @Test
    @DisplayName("EspadaDecorator debe preservar el tipo del personaje")
    void testEspadaPreservaTipo() {
        EspadaDecorator guerreroConEspada = new EspadaDecorator(guerrero);
        assertEquals("Guerrero", guerreroConEspada.getTipo());
    }

    @Test
    @DisplayName("EspadaDecorator debe devolver bonus de daño de 15")
    void testEspadaBonusDano() {
        EspadaDecorator guerreroConEspada = new EspadaDecorator(guerrero);
        assertEquals(15, guerreroConEspada.getBonusDano());
    }

    @Test
    @DisplayName("EspadaDecorator debe aplicar daño adicional al atacar")
    void testEspadaAtaqueAdicional() {
        // Mockear la estrategia para un daño controlado
        EstrategiaAtaque estrategiaMock = mock(EstrategiaAtaque.class);
        when(estrategiaMock.calcularDano(any())).thenReturn(20);
        when(estrategiaMock.getNombre()).thenReturn("MockAtaque");

        guerrero.setEstrategiaAtaque(estrategiaMock);
        EspadaDecorator guerreroConEspada = new EspadaDecorator(guerrero);

        int vidaInicial = mago.getPuntosDeVida(); // 80
        guerreroConEspada.atacar(mago);

        // Debe causar 20 (estrategia) + 15 (espada) = 35 de daño
        assertEquals(vidaInicial - 35, mago.getPuntosDeVida());
    }

    @Test
    @DisplayName("EspadaDecorator estaVivo debe reflejar al personaje decorado")
    void testEspadaEstaVivo() {
        EspadaDecorator guerreroConEspada = new EspadaDecorator(guerrero);
        assertTrue(guerreroConEspada.estaVivo());
        guerrero.recibirDano(999);
        assertFalse(guerreroConEspada.estaVivo());
    }

    @Test
    @DisplayName("EspadaDecorator getPuntosDeVida debe reflejar al personaje decorado")
    void testEspadaGetPuntosDeVida() {
        EspadaDecorator guerreroConEspada = new EspadaDecorator(guerrero);
        assertEquals(120, guerreroConEspada.getPuntosDeVida());
        guerrero.recibirDano(40);
        assertEquals(80, guerreroConEspada.getPuntosDeVida());
    }

    @Test
    @DisplayName("EspadaDecorator debe tener descripción de equipamiento")
    void testEspadaDescripcion() {
        EspadaDecorator e = new EspadaDecorator(guerrero);
        assertNotNull(e.getDescripcionEquipamiento());
        assertTrue(e.getDescripcionEquipamiento().contains("15"));
    }

    // ── ArmaduraDecorator ─────────────────────────────────────────────────

    @Test
    @DisplayName("ArmaduraDecorator debe preservar el nombre del personaje")
    void testArmaduraPreservaNombre() {
        ArmaduraDecorator guerreroConArmadura = new ArmaduraDecorator(guerrero);
        assertEquals("Arthas", guerreroConArmadura.getNombre());
    }

    @Test
    @DisplayName("ArmaduraDecorator debe reducir el daño recibido en 10")
    void testArmaduraReduceDano() {
        ArmaduraDecorator guerreroConArmadura = new ArmaduraDecorator(guerrero);
        guerreroConArmadura.recibirDano(30);
        // 30 - 10 = 20 de daño efectivo → HP: 120 - 20 = 100
        assertEquals(100, guerreroConArmadura.getPuntosDeVida());
    }

    @Test
    @DisplayName("ArmaduraDecorator no debe permitir daño reducido menor a 1")
    void testArmaduraDanoMinimoUno() {
        ArmaduraDecorator guerreroConArmadura = new ArmaduraDecorator(guerrero);
        guerreroConArmadura.recibirDano(5); // 5 - 10 = -5 → mínimo 1
        // HP: 120 - 1 = 119
        assertEquals(119, guerreroConArmadura.getPuntosDeVida());
    }

    @Test
    @DisplayName("ArmaduraDecorator debe devolver reducción de daño de 10")
    void testArmaduraReduccionDano() {
        ArmaduraDecorator a = new ArmaduraDecorator(guerrero);
        assertEquals(10, a.getReduccionDano());
    }

    @Test
    @DisplayName("ArmaduraDecorator estaVivo debe reflejar al personaje decorado")
    void testArmaduraEstaVivo() {
        ArmaduraDecorator guerreroConArmadura = new ArmaduraDecorator(guerrero);
        assertTrue(guerreroConArmadura.estaVivo());
        guerrero.recibirDano(999);
        assertFalse(guerreroConArmadura.estaVivo());
    }

    @Test
    @DisplayName("ArmaduraDecorator debe ignorar daño negativo")
    void testArmaduraDanoNegativo() {
        ArmaduraDecorator a = new ArmaduraDecorator(guerrero);
        a.recibirDano(-20);
        assertEquals(120, a.getPuntosDeVida());
    }

    @Test
    @DisplayName("ArmaduraDecorator debe tener descripción de equipamiento")
    void testArmaduraDescripcion() {
        ArmaduraDecorator a = new ArmaduraDecorator(guerrero);
        assertNotNull(a.getDescripcionEquipamiento());
        assertTrue(a.getDescripcionEquipamiento().contains("10"));
    }

    // ── Decoradores combinados ────────────────────────────────────────────

    @Test
    @DisplayName("Espada + Armadura: nombre debe mantenerse")
    void testDecoradorCombinado_Nombre() {
        Personaje p = new EspadaDecorator(new ArmaduraDecorator(guerrero));
        assertEquals("Arthas", p.getNombre());
    }

    @Test
    @DisplayName("Espada + Armadura: tipo debe mantenerse como Guerrero")
    void testDecoradorCombinado_Tipo() {
        Personaje p = new ArmaduraDecorator(new EspadaDecorator(guerrero));
        assertEquals("Guerrero", p.getTipo());
    }

    @Test
    @DisplayName("Armadura + Espada: el ataque tiene bonus de espada y armadura protege")
    void testDecoradorDoble() {
        EstrategiaAtaque mockEstrategia = mock(EstrategiaAtaque.class);
        when(mockEstrategia.calcularDano(any())).thenReturn(20);
        when(mockEstrategia.getNombre()).thenReturn("Mock");

        guerrero.setEstrategiaAtaque(mockEstrategia);
        Personaje atacante = new EspadaDecorator(guerrero);
        Personaje defensor = new ArmaduraDecorator(mago);

        int vidaInicialMago = defensor.getPuntosDeVida(); // 80

        atacante.atacar(defensor);

        // La espada causa 20 + 15 = 35 de daño sobre la armadura del mago
        // La armadura del mago reduce 10 (ataque base) pero la espada va directo al mago
        // Ataque base: 20 daño → armadura reduce a 10
        // Espada extra: 15 daño → va directo al mago decorado (sin armadura en este extra)
        // HP final = 80 - 10 - 15 = 55
        assertEquals(55, defensor.getPuntosDeVida());
    }
}

