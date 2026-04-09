import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

public class Array {
	public static Scanner teclat = new Scanner(System.in);
    public static void main(String[] args) {
        String rutaFitxer = "src/Pokedex.csv";
        File fitxer = new File(rutaFitxer);
        
        int numLineas = 0;
        try {
        	Scanner lectorContador = new Scanner(fitxer);
            if (lectorContador.hasNextLine()) {
                lectorContador.nextLine();
            }
            while (lectorContador.hasNextLine()) {
                String l = lectorContador.nextLine();
                if (!l.trim().isEmpty()) {
                    numLineas++;
                }
            }
            lectorContador.close();
        } catch (Exception e) {
            System.out.println("Error al contar: " + e.getMessage());
            return;
        }
        
        Pokemon[] pokedex = new Pokemon[numLineas];
        int liniaActual = 1;

        try {
        	Scanner lector = new Scanner(fitxer);
        	if (lector.hasNextLine()) {
        	    lector.nextLine(); 
        	    liniaActual++;
        	}
            int index = 0;
            while (lector.hasNextLine() && index < pokedex.length) {
                liniaActual++;
                String linia = lector.nextLine();
                String[] dades = linia.split("\\|");
                
                if (dades.length != 5) {
                    System.out.println("Error a la línia " + liniaActual + ": El format de la línia és incorrecte (faltan o sobren camps).");
                    return;
                }

                try {
                    pokedex[index] = new Pokemon(dades[0], dades[1], dades[2], dades[3], dades[4]);
                    index++;
                } catch (DadesPokemonException e) {
                    System.out.println("Error: " + e.getMessage() + " [Línia " + liniaActual + "]");
                    return;
                }
            }
            lector.close();
            printPokedex(pokedex);
            getEstadistiques(pokedex);
            exportaAFitxer(pokedex);
            
        } catch (Exception e) {
            System.out.println("Error al llegir el fitxer: " + e.getMessage());
        }
    }

    public static void printPokedex(Pokemon[] llista) {
        if (llista == null) return;
        
        for (int i = 0; i < llista.length; i++) {
            if (llista[i] != null) {
                String estat = (llista[i].isCapturat()) ? "Tengui" : "Falti";
                
                System.out.printf("Pokemon [%d de %d]: [%s, Tipus: %s, Nivell: %d, PS: %d, %s]\n",
                        (i + 1), llista.length, 
                        llista[i].getNom(), 
                        llista[i].getTipus(), 
                        llista[i].getNivell(), 
                        llista[i].getPs(),
                        estat);
            }
        }
    }
    
    public static void getEstadistiques(Pokemon[] llista) {
        if (llista == null || llista.length == 0) {
            System.out.println("La Pokedex està buida.");
            return;
        }

        int totalPokemons = llista.length;
        int capturats = 0;
        
        int nivellMax = llista[0].getNivell();
        int nivellMin = llista[0].getNivell();

        for (int i = 0; i < llista.length; i++) {
            if (llista[i] != null && llista[i].isCapturat()) {
            	capturats++;
            }
            if (llista[i] != null && llista[i].getNivell() > nivellMax) {
                nivellMax = llista[i].getNivell();
            }
            if (llista[i] != null && llista[i].getNivell() < nivellMin) {
                nivellMin = llista[i].getNivell();
            }
        }

        System.out.println("\n--- ESTADÍSTIQUES DE LA POKEDEX ---");
        System.out.println("Total pokémons pokedex: " + totalPokemons);
        System.out.println("Total pokémons capturats: " + capturats);
        System.out.println("Nivell més alt: " + nivellMax);
        System.out.println("Nivell més baix: " + nivellMin);
    }
    
    public static void exportaAFitxer(Pokemon[] llista) {        
        try {
            System.out.print("\nPer quin tipus vols filtrar? (deixa en blanc per a tots): ");
            String filtreTipus = teclat.nextLine().trim();

            System.out.print("Vols filtrar per (Tengui / Falti / Tots): ");
            String filtreEstat = teclat.nextLine().trim().toLowerCase();

            File carpetaSortida = new File("src/sortida");
            File fitxerSortida = new File(carpetaSortida, "PokemonsFiltrats.txt");

            try (PrintWriter escriptor = new PrintWriter(fitxerSortida)) {
                for (Pokemon p : llista) {
                    if (p == null) continue;

                    boolean compleixTipus = filtreTipus.isEmpty() || p.getTipus().equalsIgnoreCase(filtreTipus);
                    
                    boolean compleixEstat = false;
                    if (filtreEstat.isEmpty() || filtreEstat.equals("tots")) {
                        compleixEstat = true;
                    } else if (filtreEstat.equals("tengui") && p.isCapturat()) {
                        compleixEstat = true;
                    } else if (filtreEstat.equals("falti") && !p.isCapturat()) {
                        compleixEstat = true;
                    }

                    if (compleixTipus && compleixEstat) {
                        String estatText = p.isCapturat() ? "Tengui" : "Falti";
                        escriptor.printf("%s, Tipus: %s, Nivell: %d, PS: %d, %s\n",p.getNom(), p.getTipus(), p.getNivell(), p.getPs(), estatText);
                    }
                }
                System.out.println("Fitxer generat correctament a: " + fitxerSortida.getPath());
            }

        } catch (Exception e) {
            System.out.println("S'ha produït un error en exportar el fitxer: " + e.getMessage());
        }
    }
}
