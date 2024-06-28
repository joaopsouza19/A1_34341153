import br.edu.up.daos.GerenciamentoAlunos;
import br.edu.up.models.Aluno;
import java.util.List;

public class Program {
    public static void main(String[] args) {
        String caminhoArquivo = "alunos.csv"; // Certifique-se de que o arquivo alunos.csv esteja na mesma pasta que os
                                              // arquivos Java
        GerenciamentoAlunos gerenciador = new GerenciamentoAlunos();

        // Leitura dos alunos
        List<Aluno> listaDeAlunos = gerenciador.lerAlunos(caminhoArquivo);

        // Processamento dos dados
        gerenciador.processarDados(listaDeAlunos);
    }
}
