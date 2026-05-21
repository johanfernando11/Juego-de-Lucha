package com.juego.patrones.factory;

import com.juego.model.Arquero;
import com.juego.model.Guerrero;
import com.juego.model.Mago;
import com.juego.model.Personaje;

/**
 * Implementación del patrón Factory Method para crear personajes del juego.
 *
 * Centraliza la lógica de creación de personajes, permitiendo que el código
 * cliente solicite un personaje por tipo sin conocer las clases concretas.
 *
 * Patrón Factory Method:
 * - Problema resuelto: evitar la creación directa con {@code new} dispersa por el código.
 * - Beneficio: si se añade un nuevo tipo de personaje (ej: Paladin), solo se modifica aquí.
 * - Principio OCP: el cliente no necesita cambiar cuando se añaden nuevos tipos.
 *
 * Uso:
 * <pre>
 *   Personaje guerrero = PersonajeFactory.crearPersonaje("guerrero", "Arthas");
 *   Personaje mago     = PersonajeFactory.crearPersonaje("mago", "Gandalf");
 * </pre>
 */
public class PersonajeFactory {

    /** Clave de tipo para crear un Guerrero. */
    public static final String TIPO_GUERRERO = "guerrero";

    /** Clave de tipo para crear un Mago. */
    public static final String TIPO_MAGO = "mago";

    /** Clave de tipo para crear un Arquero. */
    public static final String TIPO_ARQUERO = "arquero";

    /**
     * Constructor privado: esta clase solo tiene métodos estáticos.
     * No debe instanciarse.
     */
    private PersonajeFactory() {
        throw new UnsupportedOperationException("PersonajeFactory no debe ser instanciada.");
    }

    /**
     * Crea y devuelve un personaje del tipo especificado.
     *
     * El tipo se evalúa de forma insensible a mayúsculas/minúsculas,
     * por lo que "GUERRERO", "Guerrero" y "guerrero" son equivalentes.
     *
     * @param tipo   Tipo de personaje: "guerrero", "mago" o "arquero".
     * @param nombre Nombre del personaje a crear.
     * @return Instancia concreta del personaje solicitado.
     * @throws IllegalArgumentException si el tipo no es reconocido o el nombre es inválido.
     */
    public static Personaje crearPersonaje(String tipo, String nombre) {
        if (tipo == null || tipo.trim().isEmpty()) {
            throw new IllegalArgumentException("El tipo de personaje no puede ser nulo o vacío.");
        }

        return switch (tipo.trim().toLowerCase()) {
            case TIPO_GUERRERO -> new Guerrero(nombre);
            case TIPO_MAGO     -> new Mago(nombre);
            case TIPO_ARQUERO  -> new Arquero(nombre);
            default -> throw new IllegalArgumentException(
                    "Tipo de personaje desconocido: '" + tipo + "'. " +
                    "Tipos válidos: guerrero, mago, arquero."
            );
        };
    }

    /**
     * Devuelve los tipos de personaje disponibles como array de Strings.
     *
     * @return Array con los tipos válidos.
     */
    public static String[] getTiposDisponibles() {
        return new String[]{TIPO_GUERRERO, TIPO_MAGO, TIPO_ARQUERO};
    }
}

