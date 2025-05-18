/*
Autor: Nelson García Bravatti
22434

Análisis y diseño de algoritmos
Proyecto 3
*/


import java.util.*;

public class MTF_IMTF {

    public static void main(String[] args) {
        // Menú principal del programa, se puede elegir entre MTF o IMTF
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n=== Menú Principal ===");
            System.out.println("1. Usar MTF");
            System.out.println("2. Usar IMTF");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");
            int opc = sc.nextInt();
            if (opc == 3) {
                System.out.println("¡Adiós!");
                break;
            }
            boolean isMTF = (opc == 1);
            submenu(sc, isMTF);
        }
        sc.close();
    }

    private static void submenu(Scanner sc, boolean isMTF) {
        //Sub menú, aquí se elige que par lista de configuración/secuencia de solicitudes de número se va a probar
        while (true) {
            System.out.println("\n--- Submenú " + (isMTF ? "MTF" : "IMTF") + " ---");
            System.out.println("1. Inputs personalizados");
            System.out.println("2. Inputs predefinidos");
            System.out.println("3. Regresar");
            System.out.print("Seleccione una opción: ");
            int sub = sc.nextInt();
            sc.nextLine(); // consumir newline
            if (sub == 3) return;

            //Se inicializan la lista y secuencia
            List<Integer> config;
            List<Integer> seq;

            if (sub == 1) {
                // Leer lista de configuración
                System.out.print("Ingrese la lista de configuración (ej: 0,1,2,3,4): ");
                config = parseLine(sc.nextLine());
                // Leer secuencia de solicitudes
                System.out.print("Ingrese la secuencia de solicitudes (ej: 0,1,2,3,4,0,1...): ");
                seq = parseLine(sc.nextLine());
            } else {
                // Opciones para probar
                System.out.println("Casos disponibles:");
                System.out.println("1) Pregunta 1");
                System.out.println("2) Pregunta 2");
                System.out.println("3) Best-case");
                System.out.println("4) Worst-case");
                System.out.println("5) Pregunta 5 secuencia de 2");
                System.out.println("6) Pregunta 5 secuencia de 3");
                System.out.print("Elija caso: ");
                int preset = sc.nextInt();
                switch (preset) {
                    case 1:
                        config = Arrays.asList(0, 1, 2, 3, 4);
                        seq = Arrays.asList(0, 1, 2, 3, 4, 0, 1, 2, 3, 4, 0, 1, 2, 3, 4, 0, 1, 2, 3, 4);
                        break;
                    case 2:
                        config = Arrays.asList(0, 1, 2, 3, 4);
                        seq = Arrays.asList(4, 3, 2, 1, 0, 1, 2, 3, 4, 3, 2, 1, 0, 1, 2, 3, 4);
                        break;
                    case 3:
                        config = Arrays.asList(0, 1, 2, 3, 4);
                        seq = Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
                        break;
                    case 4:
                        config = Arrays.asList(0, 1, 2, 3, 4);
                        seq = Arrays.asList(4, 3, 2, 1, 0, 4, 3, 2, 1, 0, 4, 3, 2, 1, 0, 4, 3, 2, 1, 0);
                        break;
                    case 5:
                        config = Arrays.asList(0, 1, 2, 3, 4);
                        seq = Arrays.asList(2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2);
                        break;
                    case 6:
                        config = Arrays.asList(0, 1, 2, 3, 4);
                        seq = Arrays.asList(3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3);
                        break;
                    default:
                        System.out.println("Ingrese por favor una de las opciones.");
                        continue;
                }
            }
            // Ejecutar algoritmo
            if (isMTF) runMTF(new ArrayList<>(config), seq);
            else    runIMTF(new ArrayList<>(config), seq);
        }
    }

    private static List<Integer> parseLine(String line) {
        //Parsea la líneaa ingresada por el usuario para poder ser usada en el algoritmo
        String[] parts = line.trim().split("\\s*,\\s*");
        List<Integer> result = new ArrayList<>();
        for (String p : parts) {
            if (!p.isEmpty()) result.add(Integer.parseInt(p));
        }
        return result;
    }

    private static void runMTF(List<Integer> list, List<Integer> seq) {
        //Algoritmo MTF
        System.out.println("\n>>> Ejecutando MTF");
        int totalCost = 0;
        for (int req : seq) {
            System.out.println("Lista antes: " + list);
            System.out.println("Buscando: " + req);
            int pos = list.indexOf(req);
            int cost = (2 * (pos + 1)) - 1;
            System.out.println("Costo de acceso y movimiento: " + cost);
            // Se suma al costo total de acceso
            totalCost += cost;
            // mover al frente
            list.remove(pos);
            list.add(0, req);
            System.out.println("Lista después: " + list + "\n");
        }
        System.out.println("Costo total de acceso y movimiento: " + totalCost);
    }

    private static void runIMTF(List<Integer> list, List<Integer> seq) {
        // Algoritmo IMT
        System.out.println("\n>>> Ejecutando IMTF");
        int totalCost = 0;
        for (int i = 0; i < seq.size(); i++) {
            int req = seq.get(i);
            System.out.println("Lista antes: " + list);
            System.out.println("Buscando: " + req);
            int pos = list.indexOf(req);
            int cost = 0;
            // look-ahead de pos elementos
            boolean mover = false;
            for (int j = i+1; j < seq.size() && j <= i + pos; j++) {
                if (seq.get(j) == req) {
                    mover = true;
                    break;
                }
            }
            // Si cumple, se mueve el número
            if (mover) {
                list.remove(pos);
                list.add(0, req);
                System.out.println("Se mueve al frente: sí");
                cost = (2 * (pos + 1)) - 1;
                System.out.println("Costo de acceso y movimiento: " + cost);
                totalCost += cost;
            } else {
                System.out.println("Se mueve al frente: no");
                cost = pos + 1;
                System.out.println("Costo de acceso y movimiento: " + cost);
                totalCost += cost;
            }
            System.out.println("Lista después: " + list + "\n");
        }
        System.out.println("Costo total de acceso y movimiento: " + totalCost);
    }
}
