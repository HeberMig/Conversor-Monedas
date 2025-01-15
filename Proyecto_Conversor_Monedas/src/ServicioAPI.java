import com.google.gson.Gson;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 *
 *
 * @author Heber Miguel Ramirez
 */
public class ServicioAPI {

    private final String urlBase = "https://v6.exchangerate-api.com/v6/a72c2399300979ff5dd8bfc2/latest/USD";
    private final HttpClient client;
    private final Gson gson;


    public ServicioAPI() {
        this.client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();
        this.gson = new Gson();
    }


    public Monedas obtenerTasasDeCambio() throws IOException, InterruptedException {
        HttpRequest solicitud = HttpRequest.newBuilder()
                .uri(URI.create(urlBase))
                .header("Accept", "application/json")
                .GET()
                .build();

        HttpResponse<String> respuesta = client.send(solicitud, HttpResponse.BodyHandlers.ofString());

        if (respuesta.statusCode() == 200) {
            RespuestasAPI datos = gson.fromJson(respuesta.body(), RespuestasAPI.class);
            return datos.conversion_rates();
        } else {
            throw new RuntimeException("Error al obtener datos de la API: " + respuesta.statusCode());
        }
    }


    private double obtenerTasaMoneda(Monedas tasas, String moneda) {
        switch (moneda) {
            case "USD": return tasas.USD();
            case "BRL": return tasas.BRL();
            case "ARS": return tasas.ARS();
            case "COP": return tasas.COP();
            case "CLP": return tasas.CLP();
            case "BOB": return tasas.BOB();
            case "MXN": return tasas.MXN();
            default:
                throw new IllegalArgumentException("Moneda no soportada: " + moneda);
        }
    }


    public double convertirMoneda(double monto, double tasaDeCambio) {
        return monto * tasaDeCambio;
    }


    public double obtenerTasaEspecifica(Monedas tasas, String monedaOrigen, String monedaDestino) {
        double tasaOrigen = obtenerTasaMoneda(tasas, monedaOrigen);
        double tasaDestino = obtenerTasaMoneda(tasas, monedaDestino);
        return tasaDestino / tasaOrigen;
    }


}



