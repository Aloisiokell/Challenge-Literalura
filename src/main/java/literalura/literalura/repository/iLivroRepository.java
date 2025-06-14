package literalura.literalura.repository;
import literalura.literalura.models.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface iLivroRepository extends JpaRepository<Livro, Long> {

    boolean existsByTitulo(String titulo);

    Livro findByTituloContainsIgnoreCase(String titulo);

    List<Livro> findByIdioma(String idioma);



    @Query("SELECT l FROM Livro l ORDER BY l.quantidadeDownloads DESC LIMIT 10")
    List<Livro> findTop10ByTituloByQuantidadeDownloads();


}
