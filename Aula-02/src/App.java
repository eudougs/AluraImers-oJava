import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;

import javax.lang.model.util.ElementScanner14;

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

        if (!new File("saida").exists()) {
            new File("saida").mkdir();
        } 

        var geradora = new GeradoraDeFigurinha();
        for (Map<String,String> filme : ListaDeFilmes) {

            String urlImagem = filme.get("image");
            String titulo = filme.get("title");
            Integer Classificacao = Math.round(Float.parseFloat(filme.get("imDbRating")));
            String texto ="";
            String imgClassificacao = "";

            if (Classificacao >= 8) {
                texto = "TOPZERAA";
                imgClassificacao = "Positivo.jpg";
            } else {
                if ((Classificacao < 8) && (Classificacao > 5 )) {
                    texto = "MALE MALE";
                    imgClassificacao = "MaisOuMenos.jpg";
                } else {
                    texto = "É RUIM MESMO";
                    imgClassificacao = "Negativo.jpg";
                }                
            }
            InputStream inputStreamClassificacao = new FileInputStream(new File(imgClassificacao));

            InputStream inputStream = new URL(urlImagem).openStream();
            String nomeArquivo = "saida\\" + titulo + ".png";


            geradora.cria(inputStream, nomeArquivo,texto,inputStreamClassificacao);

            System.out.println(titulo);
            System.out.println();
        }

    }
}