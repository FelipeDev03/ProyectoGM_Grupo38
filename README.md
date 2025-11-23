# Zombie Defense 🧟‍♂️🔫

**Zombie Defense** es un juego de supervivencia arcade de estilo *top-down shooter* desarrollado en Java utilizando el framework **LibGDX**. El objetivo es sobrevivir a oleadas infinitas de zombis, gestionando recursos económicos y mejorando estratégicamente el equipamiento del sobreviviente.

## 📋 Descripción del Proyecto

Este proyecto es una aplicación de escritorio que simula un entorno de supervivencia. El jugador controla a un personaje en el centro de la pantalla que debe defenderse de enemigos que aparecen en los bordes y convergen hacia él. La dificultad escala progresivamente mediante un sistema de rondas, aumentando la cantidad y resistencia de los enemigos.

### Características Principales
* **Sistema de Oleadas Infinitas:** La dificultad y cantidad de enemigos aumentan con cada ronda sobrevivivida.
* **Variedad de Enemigos:**
    * *Normales:* Velocidad y salud estándar.
    * *Rápidos:* Alta velocidad, baja salud (Aparecen desde Ronda 3).
    * *Tanques:* Lentos pero con triple salud (Aparecen desde Ronda 5).
* **Economía y Tienda:** Entre rondas, el jugador accede a una tienda para invertir los puntos ganados en:
    * *Mejora de Daño:* Aumenta la potencia de disparo (Inversión a futuro).
    * *Botiquín:* Recupera vidas perdidas (Gasto de emergencia).
* **Persistencia de Datos:** Sistema de puntuación y récord (*High Score*) que persiste durante la sesión.

## 🛠️ Requisitos del Sistema

* **Java Development Kit (JDK):** Versión 8 o superior (Recomendado JDK 11+).
* **Sistema Operativo:** Windows, macOS o Linux.
* **Memoria RAM:** Mínimo 512MB asignados a la JVM.

## 🚀 Instrucciones de Instalación y Ejecución

El proyecto utiliza **LWJGL3** como backend gráfico.

### Opción 1: Ejecutar desde IDE (Eclipse, IntelliJ, NetBeans)
1.  Importar el proyecto como **Gradle Project**.
2.  Navegar en el explorador de archivos a la ruta: `lwjgl3/src/main/java/io/github/GameSpace/lwjgl3/`.
3.  Localizar la clase **`DesktopLauncher.java`**.
4.  Hacer clic derecho -> **Run As** -> **Java Application**.

### Opción 2: Ejecutar vía Terminal (Gradle)
Abrir una terminal en la raíz del proyecto y ejecutar:

```bash
# Windows
gradlew desktop:run

# Mac/Linux
./gradlew desktop:run
