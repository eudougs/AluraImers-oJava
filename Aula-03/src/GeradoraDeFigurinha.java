import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.awt.BasicStroke;
import javax.imageio.ImageIO;

public class GeradoraDeFigurinha {
    

    private TextLayout textLayout;
    private BufferedImage read;

    public void cria(InputStream inputStream, String nomeArquivo, String texto,InputStream InputimgSobreposicao) throws Exception {

        // leitura da imagem
        // InputStream inputStream = 
        //             new FileInputStream(new File("entrada/filme-maior.jpg"));
        // InputStream inputStream = 
        //                 new URL("https://m.media-amazon.com/images/M/MV5BMDFkYTc0MGEtZmNhMC00ZDIzLWFmNTEtODM1ZmRlYWMwMWFmXkEyXkFqcGdeQXVyMTMxODk2OTU@.jpg")
        //                 .openStream();
        BufferedImage imagemOriginal = ImageIO.read(inputStream);

        // cria nova imagem em memória com transparência e com tamanho novo
        int largura = imagemOriginal.getWidth();
        int altura = imagemOriginal.getHeight();
        int novaAltura = altura + 200;
        BufferedImage novaImagem = new BufferedImage(largura, novaAltura, BufferedImage.TRANSLUCENT);

        // copiar a imagem original pra novo imagem (em memória)
        Graphics2D graphics = (Graphics2D) novaImagem.getGraphics();
        graphics.drawImage(imagemOriginal, 0, 0, null);
        
        if (InputimgSobreposicao != null){
            BufferedImage imgSobreposicao = ImageIO.read(InputimgSobreposicao);
            graphics.drawImage(imgSobreposicao, 0,(novaAltura - imgSobreposicao.getHeight()),null);
        }

        // configurar a fonte
        var fonte = new Font("Impact", Font.BOLD, 80);
        graphics.setColor(Color.YELLOW);
        graphics.setFont(fonte);
        FontMetrics metricas = graphics.getFontMetrics();

        int x = ((largura - metricas.stringWidth(texto)) / 2);
        int y = altura + 100;
        
        // escrever uma frase na nova imagem
        graphics.drawString(texto,x, y);

        FontRenderContext fontRenderContext = graphics.getFontRenderContext();
        textLayout = new TextLayout(texto, fonte, fontRenderContext);

        Shape outline = textLayout.getOutline(null);
        AffineTransform transform =  graphics.getTransform();
        transform.translate(x, y);
        graphics.setTransform(transform);
        
        var outlineStroke = new BasicStroke(largura * 0.004f);
        graphics.setStroke(outlineStroke);
        graphics.setColor(Color.black);
        graphics.draw(outline);
        graphics.setClip(outline);

        // escrever a nova imagem em um arquivo
        ImageIO.write(novaImagem, "png", new File(nomeArquivo));

    }

}