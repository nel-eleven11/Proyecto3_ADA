import java.util.*;

public class MTF_IMTF {

    public static void main(String[] args) {
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
        while (true) {
            System.out.println("\n--- Submenú " + (isMTF ? "MTF" : "IMTF") + " ---");
            System.out.println("1. Inputs personalizados");
            System.out.println("2. Inputs predefinidos");
            System.out.println("3. Regresar");
            System.out.print("Seleccione una opción: ");
            int sub = sc.nextInt();
            sc.nextLine(); // consumir newline
            if (sub == 3) return;

            List<Integer> config;
            List<Integer> seq;

            if (sub == 1) {
                // Leer lista de configuración
                System.out.print("Ingrese la lista de configuración (ej: 0,1,2,3,4): ");
                config = parseLine(sc.nextLine());
                System.out.print("Ingrese la secuencia de solicitudes (ej: 0,1,2,3,4,0,1...): ");
                seq = parseLine(sc.nextLine());
            } else {
                // Dos casos predefinidos
                System.out.println("Casos disponibles:");
                System.out.println("1) Pregunta 1");
                System.out.println("2) Pregunta 2");
                System.out.print("Elija caso: ");
                int preset = sc.nextInt();
                switch (present) {

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
                        seq = (1, 2);
                        break;
                    default:
                        System.out.println("Ingrese por favor una de las opciones.")
                        break;
                }
            }

            // Ejecutar
            if (isMTF) runMTF(new ArrayList<>(config), seq);
            else        runIMTF(new ArrayList<>(config), seq);
        }
    }

    private static List<Integer> parseLine(String line) {
        String[] parts = line.trim().split("\\s*,\\s*");
        List<Integer> result = new ArrayList<>();
        for (String p : parts) {
            if (!p.isEmpty()) result.add(Integer.parseInt(p));
        }
        return result;
    }

    private static void runMTF(List<Integer> list, List<Integer> seq) {
        System.out.println("\n>>> Ejecutando MTF");
        int totalCost = 0;
        for (int req : seq) {
            System.out.println("Lista antes: " + list);
            System.out.println("Buscando: " + req);
            int pos = list.indexOf(req);
            int cost = pos + 1;
            System.out.println("Costo de acceso: " + cost);
            totalCost += cost;
            // mover al frente
            list.remove(pos);
            list.add(0, req);
            System.out.println("Lista después: " + list + "\n");
        }
        System.out.println("Costo total de acceso: " + totalCost);
    }

    private static void runIMTF(List<Integer> list, List<Integer> seq) {
        System.out.println("\n>>> Ejecutando IMTF");
        int totalCost = 0;
        for (int i = 0; i < seq.size(); i++) {
            int req = seq.get(i);
            System.out.println("Lista antes: " + list);
            System.out.println("Buscando: " + req);
            int pos = list.indexOf(req);
            int cost = pos + 1;
            System.out.println("Costo de acceso: " + cost);
            totalCost += cost;
            // look-ahead de (cost - 1) elementos
            boolean mover = false;
            for (int j = i+1; j < seq.size() && j <= i + cost - 1; j++) {
                if (seq.get(j) == req) {
                    mover = true;
                    break;
                }
            }
            if (mover) {
                list.remove(pos);
                list.add(0, req);
                System.out.println("Se mueve al frente: sí");
            } else {
                System.out.println("Se mueve al frente: no");
            }
            System.out.println("Lista después: " + list + "\n");
        }
        System.out.println("Costo total de acceso: " + totalCost);
    }
}
