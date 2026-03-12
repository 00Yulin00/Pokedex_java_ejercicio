# 📱 Pokedex Java - Treballem amb Pokemons

Aquest projecte és una aplicació de gestió de Pokédex desenvolupada en **Java**. Combina la manipulació de fitxers, el control d'excepcions personalitzades i la gestió d'estructures de dades (Arrays) seguint els principis de la **Programació Orientada a Objectes (POO)**.

---

## 🛠️ Estructura del Projecte

El sistema està dissenyat per ser robust i garantir la integritat de les dades mitjançant les següents classes:

### 1. Entitat `Pokemon`
- **Principi d'Ocultació:** Tots els atributs són privats i només es poden definir mitjançant el constructor. No existeixen *setters* per evitar modificacions externes.
- **Validació de Dades:** El constructor rep `Strings` i realitza la conversió interna. Llança una excepció si:
    - El nom o el tipus estan buits o només contenen espais.
    - El nivell o els PS són inferiors a 1.
- **Gestió de l'estat:** Interpreta si un Pokémon està capturat o no mitjançant els conceptes "Tengui" o "Falti".

### 2. Excepció `DadesPokemonException`
- Classe d'excepció personalitzada que hereta de `Exception`.
- S'encarrega de gestionar els errors específics de format o lògica dels Pokémon sense capturar-los internament, delegant la responsabilitat a les classes de gestió.

### 3. Lògica de Dades: `Ex2Array`
Conté els mètodes estàtics per processar la col·lecció:
- **Lectura de Fitxer:** Llegeix un fitxer on la primera línia indica el total d'elements. Atura l'execució i indica la **línia exacta** i el **camp erroni** si el format no és correcte.
- **`printPokedex`:** Mostra el llistat amb el format requerit:  
  `Pokemon [1 de 13]: [Charmander, Tipus: Foc, Nivell: 12, PS: 39, Tengui]`
- **`getEstadistiques`:** Mostra el total de la Pokédex, total capturats, i els nivells màxim i mínim.
- **`exportaAFitxer`:** Permet filtrar per tipus i estat (Tengui/Falti) i genera un informe a `src/sortida/PokemonsFiltrats.txt`.

### 4. Controlador: `GestioPokedex`
Presenta un menú interactiu per a l'usuari amb les següents funcionalitats:
1. **Carregar pokédex:** Importa les dades del fitxer de text.
2. **Afegir pokémon:** Permet l'entrada manual validant cada camp.
3. **Veure pokédex:** Imprimeix el llistat actual.
4. **Estadístiques:** Mostra el resum numèric de la col·lecció.
5. **Filtrar i exportar:** Genera el fitxer de sortida filtrat.
6. **Sortir.**

---

## 📋 Requisits del Sistema

- **Java JDK 8** o superior.
- Fitxer de dades d'entrada amb format de capçalera correcte.
- Estructura de carpetes per a la sortida: `src/sortida/`.

## 📂 Especificacions de Format
L'exportació de dades genera línies amb el següent format de text net:
```text
Charmander, Tipus: Foc, Nivell: 12, PS: 39, Tengui
Squirtle, Tipus: Aigua, Nivell: 10, PS: 44, Falti
```

---
Projecte creat per 00Yulin00.
