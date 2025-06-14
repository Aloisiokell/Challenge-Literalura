package literalura.literalura.models;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "autores")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private Integer dataNascimento;

    private Integer dataFalecimento;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)

    private List<Livro> livros;


    public Autor() {
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Integer getDataNascimento() {
        return dataNascimento;
    }

    public Integer getDataFalecimento() {
        return dataFalecimento;
    }

    public List<Livro> getLivros() {
        return livros;
    }

    public void setLivros(List<Livro> livros) {
        this.livros = livros;
    }

    public Autor(literalura.literalura.models.records.Autor autor) {
        this.nome = autor.nome();
        this.dataNascimento = autor.dataNascimento();
        this.dataFalecimento = autor.dataFalecimento();
    }

    @Override
    public String toString() {
        return
                "nome='" + nome + '\'' +
                ", dataNascimento=" + dataNascimento +
                ", dataFalecimento=" + dataFalecimento;
    }
}
