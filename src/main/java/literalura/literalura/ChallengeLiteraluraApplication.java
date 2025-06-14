package literalura.literalura;
import literalura.literalura.Livraria.Livraria;
import literalura.literalura.repository.iAutorRepository;
import literalura.literalura.repository.iLivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChallengeLiteraluraApplication implements CommandLineRunner {

	@Autowired
	private iLivroRepository livroRepository;
	@Autowired
	private iAutorRepository autorRepository;

	public static void main(String[] args) {
		SpringApplication.run(ChallengeLiteraluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Livraria libreria = new Livraria(livroRepository, autorRepository);
		libreria.consumo();

	}
}
