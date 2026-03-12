

import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

public class GestioPokedex {
    private static Pokemon[] pokedex = null;
    private static int totalActual = 0; 
    private static Scanner teclat = new Scanner(System.in);

    public static void main(String[] args) {
        int opcio = 0;
        do {
            mostrarMenu();
            try {
                opcio = Integer.parseInt(teclat.nextLine());
                processarOpcio(opcio);
            } catch (NumberFormatException e) {
                System.out.println("Error: Introdueix un número vàlid.");
            }
        } while (opcio != 6);
    }

    private static void mostrarMenu() {
        System.out.println("\n--- MENÚ GESTIÓ POKÉDEX ---");
        System.out.println("1. Carregar pokédex");
        System.out.println("2. Afegir pokémon");
        System.out.println("3. Veure pokédex");
        System.out.println("4. Estadístiques pokédex");
        System.out.println("5. Filtrar i exportar");
        System.out.println("6. Sortir");
        System.out.print("Selecciona una opció: ");
    }

    private static void processarOpcio(int opcio) {
        switch (opcio) {
            case 1: 
            	carregaPokedex(); 
            	break;
            case 2: 
            	afegirPokemon(); 
            	break;
            case 3: 
            	veurePokedex(); 
            	break;
            case 4: 
            	getEstadistiques(); 
            	break;
            case 5: 
            	export();
            	break;
            case 6: 
            	System.out.println("Abandonant la pokédex...");
            	break;
            default: 
            	System.out.println("Opció invàlida");
        }
    }

    public static void carregaPokedex() {
        File fitxer = new File("src/Pokedex.csv");
        int contador = 0;

        try {
            Scanner scContar = new Scanner(fitxer);
            if (scContar.hasNextLine()) {
            	scContar.nextLine();
            }
            while (scContar.hasNextLine()) {
                if (!scContar.nextLine().trim().isEmpty()) {
                	contador++;
                }
            }
            scContar.close();

            pokedex = new Pokemon[contador];
            totalActual = 0;

            Scanner scDades = new Scanner(fitxer);
            if (scDades.hasNextLine()) {
            	scDades.nextLine();
            }
            while (scDades.hasNextLine() && totalActual < pokedex.length) {
                String linia = scDades.nextLine();
                String[] d = linia.split("\\|");
                if (d.length == 5) {
                    pokedex[totalActual] = new Pokemon(d[0], d[1], d[2], d[3], d[4]);
                    totalActual++;
                }
            }
            scDades.close();
            System.out.println("Pokédex carregada. Total: " + totalActual);
        } catch (Exception e) {
            System.out.println("Error en carregar: " + e.getMessage());
        }
    }

    public static void afegirPokemon() {
        try {
            if (pokedex == null) {
            	pokedex = new Pokemon[100]; 
            }
            if (totalActual >= pokedex.length) {
                System.out.println("Error: Pokedex plena.");
                return;
            }

            System.out.print("Nom: "); String nom = teclat.nextLine();
            System.out.print("Tipus: "); String tipus = teclat.nextLine();
            System.out.print("Nivell: "); String nivell = teclat.nextLine();
            System.out.print("PS: "); String ps = teclat.nextLine();
            System.out.print("Capturat (true/false): "); String capturat = teclat.nextLine();

            pokedex[totalActual] = new Pokemon(nom, tipus, nivell, ps, capturat);
            totalActual++;
            System.out.println("Afegit correctament.");
        } catch (DadesPokemonException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error inesperat.");
        }
    }

    public static void veurePokedex() {
        if (totalActual == 0) {
            System.out.println("No hi ha pokemons a la pokedex.");
            return;
        }
        for (int i = 0; i < totalActual; i++) {
            String estat = pokedex[i].isCapturat() ? "Tengui" : "Falti";
            System.out.printf("Pokemon [%d de %d]: [%s, Tipus: %s, Nivell: %d, PS: %d, %s]\n", (i + 1), totalActual, pokedex[i].getNom(), pokedex[i].getTipus(), pokedex[i].getNivell(), pokedex[i].getPs(), estat);
        }
    }

    public static void getEstadistiques() {
        if (totalActual == 0) {
            System.out.println("No hi ha pokemons a la pokedex.");
            return;
        }
        int capturats = 0;
        int max = pokedex[0].getNivell(), min = pokedex[0].getNivell();

        for (int i = 0; i < totalActual; i++) {
        	Pokemon pActual = pokedex[i];

            if (pActual.isCapturat() == true) {
                capturats = capturats + 1;
            }
            if (pActual.getNivell() > max) {
                max = pActual.getNivell();
            }
            if (pActual.getNivell() < min) {
                min = pActual.getNivell();
            }
        }
        System.out.println("Total pokémons pokedex: " + totalActual);
        System.out.println("Total pokémons capturats: " + capturats);
        System.out.println("Nivell més alt: " + max);
        System.out.println("Nivell més baix: " + min);
    }

    public static void export() {
        if (totalActual == 0) {
            System.out.println("No hi ha pokemons a la pokedex.");
            return;
        }
        System.out.print("Tipus a filtrar (blanc=tots): "); 
        String fTipus = teclat.nextLine();
        System.out.print("Estat (tengui/falti/tots): "); 
        String fEstat = teclat.nextLine().toLowerCase();

        try {
        	if (!fEstat.isEmpty() && !fEstat.equals("tots") && !fEstat.equals("tengui") && !fEstat.equals("falti")) {
                throw new Exception("L'estat introduït no és vàlid (tengui/falti/tots).");
            }
        	
        	File folder = new File("src/sortida");
            PrintWriter pw = new PrintWriter(new File(folder, "PokemonsFiltrats.txt"));

            for (int i = 0; i < totalActual; i++) {
                Pokemon p = pokedex[i];
                
                boolean passaFiltreTipus = false;
                if (fTipus.isEmpty()) {
                    passaFiltreTipus = true;
                } else if (p.getTipus().equalsIgnoreCase(fTipus)) {
                    passaFiltreTipus = true;
                }

                if (passaFiltreTipus) {
                    boolean passaFiltreEstat = false;
                    
                    if (fEstat.isEmpty() || fEstat.equals("tots")) {
                        passaFiltreEstat = true;
                    } else if (fEstat.equals("tengui") && p.isCapturat()) {
                        passaFiltreEstat = true;
                    } else if (fEstat.equals("falti") && !p.isCapturat()) {
                        passaFiltreEstat = true;
                    }

                    if (passaFiltreEstat) {
                        String estatText = "";
                        if (p.isCapturat()) {
                            estatText = "Tengui";
                        } else {
                            estatText = "Falti";
                        }
                        
                        pw.println(p.getNom() + ", Tipus: " + p.getTipus() + ", Nivell: " + p.getNivell() + ", PS: " + p.getPs() + ", " + estatText);
                    }
                }
            }
            pw.close();
            System.out.println("Fitxer generat correctament.");
        } catch (Exception e) {
            System.out.println("Error en exportar.");
        }
    }
}