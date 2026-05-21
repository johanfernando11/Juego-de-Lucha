# ⚔️ Juego de Lucha — Patrones de Diseño en Java

![Java CI with Maven](https://github.com/TU_USUARIO/juego-lucha-patrones/actions/workflows/ci.yml/badge.svg)
![Java](https://img.shields.io/badge/Java-17-orange)
![Maven](https://img.shields.io/badge/Maven-3.8+-blue)
![JUnit5](https://img.shields.io/badge/JUnit-5.10-green)
![Mockito](https://img.shields.io/badge/Mockito-5.5-red)
![JaCoCo](https://img.shields.io/badge/Cobertura-80%25+-brightgreen)

Refinamiento arquitectónico de un juego de lucha por turnos en Java, aplicando **patrones de diseño creacionales y estructurales**, pruebas unitarias con JUnit 5 + Mockito, y CI/CD con GitHub Actions.

---

## 🏛️ Patrones de Diseño Implementados

| Patrón | Categoría | Propósito |
|--------|-----------|-----------|
| **Factory Method** | Creacional | Crear diferentes tipos de personajes sin acoplar el cliente a clases concretas |
| **Strategy** | Estructural | Intercambiar algoritmos de ataque en tiempo de ejecución |
| **Decorator** | Estructural | Agregar equipamiento (armas/armaduras) sin modificar las clases base |

---

## 🛠️ Tecnologías

- **Java 17** — Lenguaje principal
- **Maven 3.8+** — Gestión de dependencias y build
- **JUnit 5.10** — Framework de pruebas unitarias
- **Mockito 5.5** — Mocking para pruebas con dependencias aisladas
- **JaCoCo 0.8.11** — Cobertura de código (objetivo: ≥ 80%)
- **GitHub Actions** — Pipeline CI/CD automatizado
- **GitHub Codespaces** — Entorno de desarrollo en la nube

---

## 📁 Estructura del Proyecto

```
juego-lucha-patrones/
├── .github/
│   └── workflows/
│       └── ci.yml                   # Pipeline de CI/CD
├── src/
│   ├── main/java/com/juego/
│   │   ├── model/
│   │   │   ├── Personaje.java       # Clase base abstracta
│   │   │   ├── Guerrero.java        # 120 HP, Ataque Fuerte
│   │   │   ├── Mago.java            # 80 HP, Ataque Mágico
│   │   │   └── Arquero.java         # 100 HP, Ataque Rápido
│   │   ├── patrones/
│   │   │   ├── factory/
│   │   │   │   └── PersonajeFactory.java   # Factory Method
│   │   │   ├── strategy/
│   │   │   │   ├── EstrategiaAtaque.java   # Interface Strategy
│   │   │   │   ├── AtaqueFuerte.java       # 25–40 daño
│   │   │   │   ├── AtaqueRapido.java       # 10–20 daño
│   │   │   │   └── AtaqueMagico.java       # 15–50 daño
│   │   │   └── decorator/
│   │   │       ├── PersonajeDecorator.java # Decorator base abstracto
│   │   │       ├── EspadaDecorator.java    # +15 daño por ataque
│   │   │       └── ArmaduraDecorator.java  # -10 daño recibido
│   │   └── juego/
│   │       └── JuegoLucha.java      # Orquestador del combate + main()
│   └── test/java/com/juego/
│       ├── model/
│       │   └── PersonajeTest.java   # Tests de modelo y Strategy
│       ├── patrones/
│       │   ├── EstrategiaAtaqueTest.java  # Tests de estrategias
│       │   ├── PersonajeFactoryTest.java  # Tests de Factory Method
│       │   └── DecoratorTest.java         # Tests de Decorators
│       └── juego/
│           └── JuegoLuchaTest.java  # Tests de flujo completo
├── pom.xml                          # Configuración Maven
├── .gitignore
└── README.md
```

---

## 🚀 Cómo Ejecutar en GitHub Codespaces

### Paso 1: Crear el Codespace

1. Ve a tu repositorio en GitHub
2. Clic en **Code** → **Codespaces** → **Create codespace on main**
3. Espera 1-2 minutos a que el entorno esté listo

### Paso 2: Verificar herramientas

```bash
java -version    # Debe mostrar: openjdk 17
mvn -version     # Debe mostrar: Apache Maven 3.8+
git --version    # Debe mostrar: git 2.x
```

### Paso 3: Crear estructura del proyecto

```bash
mkdir -p src/main/java/com/juego/{model,patrones/{factory,strategy,decorator},juego}
mkdir -p src/test/java/com/juego/{model,patrones,juego}
mkdir -p .github/workflows
```

### Paso 4: Compilar y ejecutar

```bash
# Compilar
mvn clean compile

# Ejecutar el juego
mvn exec:java -Dexec.mainClass="com.juego.juego.JuegoLucha"
```

---

## 🧪 Cómo Correr los Tests

```bash
# Ejecutar todas las pruebas
mvn test

# Ejecutar una clase de test específica
mvn test -Dtest=PersonajeTest

# Ejecutar un test específico
mvn test -Dtest=PersonajeFactoryTest#testCrearGuerrero

# Ver resultados detallados
cat target/surefire-reports/*.txt
```

---

## 📊 Generar Reporte de Cobertura JaCoCo

```bash
# Ejecutar tests + generar reporte
mvn clean test

# El reporte HTML se genera en:
# target/site/jacoco/index.html

# En Codespaces: clic derecho en index.html → "Open with Live Server"
```

El reporte muestra:
- **Instrucciones** cubiertas / no cubiertas
- **Ramas** (if/else) cubiertas
- **Métodos** y **clases** cubiertas
- Objetivo mínimo: **≥ 80%** de cobertura

---

## ⚙️ GitHub Actions — CI/CD

El pipeline se activa automáticamente con cada `push` a `main` o `develop`:

1. **Checkout** — descarga el código
2. **Setup JDK 17** — configura el entorno Java
3. **Compilar** — `mvn clean compile`
4. **Tests** — `mvn test`
5. **Cobertura** — `mvn jacoco:report`
6. **Artefactos** — sube los reportes para descarga

Para ver el pipeline:
1. Ve a tu repositorio → pestaña **Actions**
2. Clic en el workflow más reciente
3. Descarga los artefactos **jacoco-coverage-report** y **surefire-test-results**

---

## 🎮 Ejemplo de Uso

```java
// 1. Factory Method: crear personajes
Personaje guerrero = PersonajeFactory.crearPersonaje("guerrero", "Arthas");
Personaje mago     = PersonajeFactory.crearPersonaje("mago", "Gandalf");

// 2. Decorator: equipar personajes
guerrero = new EspadaDecorator(guerrero);    // +15 daño
guerrero = new ArmaduraDecorator(guerrero); // -10 daño recibido

// 3. Strategy: cambiar estrategia de ataque en tiempo real
mago.setEstrategiaAtaque(new AtaqueRapido());

// 4. Ejecutar el combate
JuegoLucha juego = new JuegoLucha();
Personaje ganador = juego.ejecutarCombate(guerrero, mago);
```

---

## 📋 Comandos Git Esenciales

```bash
git status                          # Ver cambios pendientes
git add .                           # Agregar todos los archivos
git commit -m "feat: descripción"   # Crear commit
git push origin main                # Subir a GitHub
git log --oneline                   # Ver historial
```

---

## 👥 Autores

Proyecto desarrollado para la asignatura de **Ingeniería de Software** — Ing. Jhon Haide Cano Beltrán MSc.
