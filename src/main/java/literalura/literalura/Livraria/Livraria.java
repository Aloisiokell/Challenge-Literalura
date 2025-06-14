package literalura.literalura.Livraria;
import literalura.literalura.config.ConsumoApiGutendex;
import literalura.literalura.config.ConverteDados;
import literalura.literalura.models.Autor;
import literalura.literalura.models.Livro;
import literalura.literalura.models.LivrosRespostaApi;
import literalura.literalura.models.records.DadosLivro;
import literalura.literalura.repository.iAutorRepository;
import literalura.literalura.repository.iLivroRepository;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

public class Livraria {

    private final Scanner sc = new Scanner(System.in);
    private final ConsumoApiGutendex consumoApi = new ConsumoApiGutendex();
    private final ConverteDados converter = new ConverteDados();
    private static final String API_BASE = "https://gutendex.com/books/?search=";
    //var json = consumoApi.obtenerDatos("https://gutendex.com/books/?ids=1513");
    //var json = consumoApi.obtenerDatos("https://gutendex.com/books/?search=Romeo%20and%20Juliet");
    private final List<Livro> datosLivro = new ArrayList<>();
    private final iLivroRepository livroRepository;
    private final iAutorRepository autorRepository;
    public Livraria(iLivroRepository livroRepository, iAutorRepository autorRepository) {
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
    }

    public void consumo(){
        var opcion = -1;
        while (opcion != 0){
            var menu = """
                    
                    |***************************************************|
                    |*****       BEM VINDO A LIVRARIA       ******|
                    |***************************************************|
                    
                    1 - Cadastrar Livro por Nome
                    2 - Livros cadastrados
                    3 - Buscar livro por Nome
                    4 - Buscar todos os Autores de livros buscados
                    5 - Buscar Autores por ano
                    6 - Buscar Livros por Idioma
                    7 - Top 10 Livros mais Baixados
                    8 - Buscar Autor por Nome
                   
                    
               
                    0 - Sair
                    
                    |***************************************************|
                    |*****            DIGITE UMA OPÇAO          ******|
                    |***************************************************|
                    """;

            try {
                System.out.println(menu);
                opcion = sc.nextInt();
                sc.nextLine();
            } catch (InputMismatchException e) {

                System.out.println("|****************************************|");
                System.out.println("|  Por favor, digite um número válido.  |");
                System.out.println("|****************************************|\n");
                sc.nextLine();
                continue;
            }



            switch (opcion){
                case 1:
                    buscarLivroEnLaWeb();
                    break;
                case 2:
                    livrosBuscados();
                    break;
                case 3:
                    buscarLivroPorNome();
                    break;
                case 4:
                    BuscarAutores();
                    break;
                case 5:
                    buscarAutoresPorAno();
                    break;
                case 6:
                    buscarLivrosPorIdioma();
                    break;
                case 7:
                    top10LivrosMaisBaixados();
                    break;
                case 8:
                    buscarAutorPorNome();
                    break;
                case 0:
                    opcion = 0;
                    System.out.println("|********************************|");
                    System.out.println("|    ERRO NA APLICAÇÃO. Bye!    |");
                    System.out.println("|********************************|\n");
                    break;
                default:
                    System.out.println("|*********************|");
                    System.out.println("|  Opcão Incorreta. |");
                    System.out.println("|*********************|\n");
                    System.out.println("Tente com uma nova Opção");
                    consumo();
                    break;
            }
        }
    }

    private Livro getDadosLivro(){
        System.out.println("Digite o nome do livro: ");
        var nomeLivro = sc.nextLine().toLowerCase();
        var json = consumoApi.obterDados(API_BASE + nomeLivro.replace(" ", "%20"));

        LivrosRespostaApi dados = converter.converterDadosJsonAJava(json, LivrosRespostaApi.class);

            if (dados != null && dados.getResultadoLivros() != null && !dados.getResultadoLivros().isEmpty()) {
                DadosLivro primerLivro = dados.getResultadoLivros().get(0);
                return new Livro(primerLivro);
            } else {
                System.out.println("Resultados não  encontrado.");
                return null;
            }
    }


    private void buscarLivroEnLaWeb() {
        Livro livro = getDadosLivro();

        if (livro == null){
            System.out.println("Livro não encontrado. o valor é null");
            return;
        }


        try{
            boolean livroExists = livroRepository.existsByTitulo(livro.getTitulo());
            if (livroExists){
                System.out.println("O livro já existe na base de dados!");
            }else {
                livroRepository.save(livro);
                System.out.println(livro);
            }
        }catch (InvalidDataAccessApiUsageException e){
            System.out.println("Nâo pode inserir o livro buscado!");
        }
    }

    @Transactional(readOnly = true)
    private void livrosBuscados(){

        List<Livro> livros = livroRepository.findAll();
        if (livros.isEmpty()) {
            System.out.println("Livros não encontrado na base de dados.");
        } else {
            System.out.println("Libros encontrados na base de dados:");
            for (Livro livro : livros) {
                System.out.println(livro.toString());
            }
        }
    }

    private void buscarLivroPorNome() {
        System.out.println("Digite o Titulo do livro que queres buscar: ");
        var titulo = sc.nextLine();
        Livro livroBuscado = livroRepository.findByTituloContainsIgnoreCase(titulo);
        if (livroBuscado != null) {
            System.out.println("O livro buscado foi: " + livroBuscado);
        } else {
            System.out.println("O livro com o titulo '" + titulo + "' não encontrado.");
        }
    }

    private  void BuscarAutores(){

        List<Autor> autores = autorRepository.findAll();

        if (autores.isEmpty()) {
            System.out.println("Livros não encontrados na base de dados. \n");
        } else {
            System.out.println("Livros encontrados na base de dados: \n");
            Set<String> autoresUnicos = new HashSet<>();
            for (Autor autor : autores) {

                if (autoresUnicos.add(autor.getNome())){
                    System.out.println(autor.getNome()+'\n');
                }
            }
        }
    }

    private void  buscarLivrosPorIdioma(){
        System.out.println("Digite o idioma  que  buscar: \n");
        System.out.println("|***********************************|");
        System.out.println("|  Opção - es : Livros em espanhol. |");
        System.out.println("|  Opção - en : Livros em ingles.  |");
        System.out.println("|  Opção - pt : Livros em potuguês.  |");
        System.out.println("|***********************************|\n");

        var idioma = sc.nextLine();
        List<Livro> livrosPorIdioma = livroRepository.findByIdioma(idioma);

        if (livrosPorIdioma.isEmpty()) {
            System.out.println("Livros não encontrados na base de dados.");
        } else {
            System.out.println("Livros segundo o idioma encontrados na base de dados:");
            for (Livro livro : livrosPorIdioma) {
                System.out.println(livro.toString());
            }
        }

    }

    private void buscarAutoresPorAno() {
//

        System.out.println("Digite o ano para consultar que autores estão vivos: \n");
        var anoBuscado = sc.nextInt();
        sc.nextLine();

        List<Autor> autoresVivos = autorRepository.findByDataNascimentoLessThanOrDataFalecimentoGreaterThanEqual(anoBuscado, anoBuscado);

        if (autoresVivos.isEmpty()) {
            System.out.println("Não foi encontrado autores que estão vivos no ano digitado " + anoBuscado + ".");
        } else {
            System.out.println("Os autores que estão vivos no ano digitado " + anoBuscado + " são:");
            Set<String> autoresUnicos = new HashSet<>();

            for (Autor autor : autoresVivos) {
                if (autor.getDataNascimento() != null && autor.getDataFalecimento() != null) {
                    if (autor.getDataNascimento() <= anoBuscado && autor.getDataFalecimento() >= anoBuscado) {
                        if (autoresUnicos.add(autor.getNome())) {
                            System.out.println("Autor: " + autor.getNome());
                        }
                    }
                }
            }
        }
    }

    private void top10LivrosMaisBaixados(){
        List<Livro> top10Livros = livroRepository.findTop10ByTituloByQuantidadeDownloads();
        if (!top10Livros.isEmpty()){
            int index = 1;
            for (Livro livro : top10Livros){
                System.out.printf("Livro %d: %s Autor: %s Baixados: %d\n",
                        index, livro.getTitulo(), livro.getAutores().getNome(), livro.getQuantidadeDownloads());
                index++;
            }

        }
    }


    private void buscarAutorPorNome() {
        System.out.println("Digite o  nome do escritor: ");
        var escritor = sc.nextLine();
        Optional<Autor> escritorBuscado = autorRepository.findFirstByNomeContainsIgnoreCase(escritor);
        if (escritorBuscado != null) {
            System.out.println("\nO escritor buscado foi: " + escritorBuscado.get().getNome());

        } else {
            System.out.println("\nO escritor com o titulo '" + escritor + "' não encontrado.");
        }
    }
}
