import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ServicioAPI servicio = new ServicioAPI();
        Scanner scanner = new Scanner(System.in);

        boolean continuar = true;

        System.out.println("\n***********************************");
        System.out.println("Bienvenido a tu conversor de monedas     ");
        System.out.println("***********************************\n");

        while (continuar) {
            try {
                // Elegir la moneda de origen
                System.out.println("Seleccione la moneda de partida:");
                System.out.println("1. MXN (Peso mexicano)");
                System.out.println("2. USD (Dólar estadounidense)");
                System.out.println("3. BRL (Real brasileño)");
                System.out.println("4. COP (Peso colombiano)");
                System.out.println("5. CLP (Peso chileno)");
                System.out.println("6. BOB (Boliviano boliviano)");
                System.out.println("7. ARS (Peso argentino)");
                System.out.println("8. Salir");
                System.out.print("Ingrese su opción: ");
                int opcionOrigen = Integer.parseInt(scanner.nextLine());

                if (opcionOrigen == 8) {
                    System.out.println("\n¡Gracias por usar el conversor! Hasta pronto.\n");
                    continuar = false;
                    break;
                }

                String monedaOrigen = obtenerMoneda(opcionOrigen);
                if (monedaOrigen == null) {
                    System.out.println("\nOpción inválida. Intente nuevamente.\n");
                    continue;
                }

                String nombreMonedaOrigen = obtenerNombreMoneda(monedaOrigen);
                System.out.println("\nHas seleccionado: " + nombreMonedaOrigen);

                // Elegir la moneda de destino
                System.out.println("\nSeleccione la moneda de destino:");
                System.out.println("1. MXN (peso mexicano)");
                System.out.println("2. USD (Dólar estadounidense)");
                System.out.println("3. BRL (Real brasileño)");
                System.out.println("4. COP (Peso colombiano)");
                System.out.println("5. CLP (Peso chileno)");
                System.out.println("6. BOB (Peso boliviano)");
                System.out.println("7. ARS (Peso argentino)");
                System.out.print("Ingrese su opción: ");
                int opcionDestino = Integer.parseInt(scanner.nextLine());

                String monedaDestino = obtenerMoneda(opcionDestino);
                if (monedaDestino == null) {
                    System.out.println("\nOpción inválida. Intente nuevamente.\n");
                    continue;
                }

                String nombreMonedaDestino = obtenerNombreMoneda(monedaDestino);
                System.out.println("\nHas seleccionado: " + nombreMonedaDestino);


                // Pedir el monto a convertir
                System.out.print("\nIngresa el monto en " + nombreMonedaOrigen + " a convertir: ");
                double monto = Double.parseDouble(scanner.nextLine());

                if (monto <= 0) {
                    System.out.println("\nEl monto debe ser mayor que 0. Intente nuevamente.\n");
                    continue;
                }

                // Realizar la conversión
                Monedas tasas = servicio.obtenerTasasDeCambio();
                double tasaDeCambio = servicio.obtenerTasaEspecifica(tasas, monedaOrigen, monedaDestino);
                double resultado = servicio.convertirMoneda(monto, tasaDeCambio);

                // Mostrar el resultado
                System.out.println("\n*****************************************");
                System.out.printf("%30s%n", "El resultado es:");
                System.out.printf("%30s%n", String.format("%.2f %s convertidos a %s son: %.2f %s",
                        monto, monedaOrigen, monedaDestino, resultado, monedaDestino));
                System.out.println("*****************************************\n");




            } catch (NumberFormatException e) {
                System.out.println("\nError: Debe ingresar un número válido. Intente nuevamente.\n");
            } catch (IllegalArgumentException e) {
                System.out.println("\nError: " + e.getMessage() + "\n");
            } catch (Exception e) {
                System.out.println("\nOcurrió un error inesperado. Intente nuevamente.\n");
                e.printStackTrace();
            }
        }

        scanner.close();
    }

    private static String obtenerMoneda(int opcion) {
        return switch (opcion) {
            case 1 -> "MXN";
            case 2 -> "USD";
            case 3 -> "BRL";
            case 4 -> "COP";
            case 5 -> "CLP";
            case 6 -> "BOB";
            case 7 -> "ARS";
            default -> null;
        };
    }

    private static String obtenerNombreMoneda(String moneda) {
        return switch (moneda) {
            case "MXN" -> "Peso mexicano";
            case "USD" -> "Dólar estadounidense";
            case "BRL" -> "Real brasileño";
            case "COP" -> "Peso colombiano";
            case "CLP" -> "Peso chileno";
            case "BOB" -> "Peso boliviano";
            case "ARS" -> "Peso argentino";
            default -> "Desconocida";
        };
    }
}
