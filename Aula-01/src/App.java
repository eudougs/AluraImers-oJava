import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;

public class App {
    public static void main(String[] args) throws Exception {

        String url = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/TopMovies.json"; // usando
                                                                                                              // endereço
                                                                                                              // alternativo
        URI endereco = URI.create(url);
        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder(endereco).GET().build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        String body = response.body();

        var parser = new JsonParser();
        List<Map<String, String>> ListaDeFilmes = parser.parse(body);

        String estrela = "\u2B50";

        for (Map<String, String> filme : ListaDeFilmes) {
            System.out.println("\033[0;1m" + "Titulo: " + "\u001b[3m" + filme.get("title") + "\u001b[3m" +"\033[0;0m");
            System.out.println(filme.get("image"));
            System.out.print("\033[0;1m" + "\u001B[33m" + filme.get("imDbRating") + " - " + "\033[0;0m" + "\u001B[0m");
            
            Integer vl = Math.round(Float.parseFloat(filme.get("imDbRating")));

            for (int i=1; i < vl; i++) {
                System.out.print(estrela);    
            }
            
            System.out.println();
            System.out.println();            
        }

    }
}