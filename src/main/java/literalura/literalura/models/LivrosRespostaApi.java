package literalura.literalura.models;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import literalura.literalura.models.records.DadosLivro;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LivrosRespostaApi {

    @JsonAlias("results")
    List<DadosLivro> resultadoLivros;

    public List<DadosLivro> getResultadoLivros() {
        return resultadoLivros;
    }

    public void setResultadoLivros(List<DadosLivro> resultadoLivros) {
        this.resultadoLivros = resultadoLivros;
    }
}
