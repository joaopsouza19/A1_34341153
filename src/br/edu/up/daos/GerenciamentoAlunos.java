package br.edu.up.daos;

import br.edu.up.models.Aluno;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GerenciamentoAlunos {
    public List<Aluno> lerAlunos(String caminhoArquivo) {
        String line = "";
        String csvSplitBy = ";"; // Separador ponto e vírgula
        List<Aluno> listaDeAlunos = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            // Ignorar a primeira linha (cabeçalho)
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] alunoData = line.split(csvSplitBy);

                // Verifica se a linha contém todos os campos esperados
                if (alunoData.length >= 3) {
                    Aluno aluno = new Aluno(alunoData[0], alunoData[1],
                            Double.parseDouble(alunoData[2].replace(",", ".")));
                    listaDeAlunos.add(aluno);
                } else {
                    System.err.println("Linha incompleta no arquivo CSV: " + line);
                }
            }
            System.out.println("Leitura do arquivo alunos.csv concluída com sucesso.");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.err.println("Erro ao converter nota para double: " + e.getMessage());
        }

        return listaDeAlunos;
    }

    public void processarDados(List<Aluno> alunos) {
        int totalAlunos = alunos.size();
        int aprovados = 0;
        int reprovados = 0;
        double menorNota = Double.MAX_VALUE;
        double maiorNota = Double.MIN_VALUE;
        double somaNotas = 0;

        for (Aluno aluno : alunos) {
            double nota = aluno.getNota();
            if (nota >= 6.0) {
                aprovados++;
            } else {
                reprovados++;
            }

            if (nota < menorNota) {
                menorNota = nota;
            }
            if (nota > maiorNota) {
                maiorNota = nota;
            }
            somaNotas += nota;
        }

        double mediaNotas = somaNotas / totalAlunos;

        System.out.println("Processamento dos dados concluído.");
        gravarResumo(totalAlunos, aprovados, reprovados, menorNota, maiorNota, mediaNotas);
    }

    public void gravarResumo(int totalAlunos, int aprovados, int reprovados, double menorNota, double maiorNota,
            double mediaNotas) {
        String csvFile = "resumo.csv";
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(csvFile))) {
            bw.write("Total de alunos," + totalAlunos + "\n");
            bw.write("Aprovados," + aprovados + "\n");
            bw.write("Reprovados," + reprovados + "\n");
            bw.write("Menor nota," + menorNota + "\n");
            bw.write("Maior nota," + maiorNota + "\n");
            bw.write("Média das notas," + mediaNotas + "\n");
            System.out.println("Arquivo resumo.csv criado com sucesso.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
