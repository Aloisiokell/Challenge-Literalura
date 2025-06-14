package literalura.literalura.repository;
import literalura.literalura.models.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface iAutorRepository extends JpaRepository<Autor,Long> {

    List<Autor> findAll();


    List<Autor> findByDataNascimentoLessThanOrDataFalecimentoGreaterThanEqual(int anoBuscado, int anoBuscado1);

    Optional<Autor> findFirstByNomeContainsIgnoreCase(String escritor);
}
