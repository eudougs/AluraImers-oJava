import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class App {
    public static void main(String[] args) throws Exception {
        
        API api = API.IMDB_TOP_TV;

       // String url = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/TopMovies.json"; // usando
                                                                                                              // endereço
                                                                                                              // alternativo
        String url = api.getUrl();
        ExtratorDeConteudo extrator = api.getExtrator();

        var http = new ClienteHttp();
        String json = http.buscaDados(url);

        List<Conteudo> conteudos = extrator.extraiConteudos(json);

        if (!new File("saida").exists()) {
            new File("saida").mkdir();
        }

        var geradora = new GeradoraDeFigurinha();
        
        for (int i = 0; i < 3; i++) {
            Conteudo conteudo = conteudos.get(i);

            String texto ="";
            String imgClassificacao = "";
            InputStream inputStreamClassificacao = null;
  
            if (conteudo.Rating() == null){
                texto = "TOPZERAA";
            } else {
                Integer Classificacao = Math.round(Float.parseFloat(conteudo.Rating()));               

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
                inputStreamClassificacao = new FileInputStream(new File(imgClassificacao));                
            }

            //InputStream inputStreamClassificacao = new FileInputStream(new File(imgClassificacao));
            InputStream inputStream = new URL(conteudo.urlImagem()).openStream();
            String nomeArquivo = "saida\\" + conteudo.titulo() + ".png";


            geradora.cria(inputStream, nomeArquivo,texto,inputStreamClassificacao);

            System.out.println(conteudo.titulo());
            System.out.println();
        }

    }
}