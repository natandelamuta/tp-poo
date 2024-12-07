import java.io.*;
import java.util.*;

public class SimulacaoJogo {

    public static List<Propriedade> leJogoConfig(String filename) throws IOException {
        List<Propriedade> propriedades = new ArrayList<>();
        BufferedReader leitor = new BufferedReader(new FileReader(filename));

        String linha;
        while ((linha = leitor.readLine()) != null) {
            String[] tokens = linha.split(" ");
            int preco = Integer.parseInt(tokens[0]);
            int aluguel = Integer.parseInt(tokens[1]);
            propriedades.add(new Propriedade(preco, aluguel));
        }

        leitor.close();
        return propriedades;
    }

    public static void simularJogos(int numSimulacoes, List<Propriedade> propriedades) {
        String[] comportamentos = {"impulsivo", "exigente", "cauteloso", "aleatorio"};
        int[] contadorVitorias = new int[4];
        int totalRodadas = 0;
        int contadorTimeout = 0;

        for (int i = 0; i < numSimulacoes; i++) {
            List<Jogador> jogadores = new ArrayList<>();
            for (int j = 0; j < comportamentos.length; j++) {
                jogadores.add(new Jogador(j, comportamentos[j]));
            }

            Collections.shuffle(jogadores);

            Jogo jogo = new Jogo(jogadores, propriedades);
            String vitoriosoComportamento = jogo.run();
            totalRodadas += jogo.rodadas;

            for (int j = 0; j < comportamentos.length; j++) {
                if (vitoriosoComportamento.equals(comportamentos[j])) {
                    contadorVitorias[j]++;
                }
            }

            if (jogo.rodadas >= 1000) {
                contadorTimeout++;
            }
        }

        System.out.println("Timeouts: " + contadorTimeout + " / " + numSimulacoes);
        System.out.println("Average rodadas per Jogo: " + (double) totalRodadas / numSimulacoes);
        for (int i = 0; i < comportamentos.length; i++) {
            System.out.printf("Vitoria percentage for %s: %.2f%%\n", comportamentos[i], (double) contadorVitorias[i] / numSimulacoes * 100);
        }

        int maximoVitorias = Arrays.stream(contadorVitorias).max().getAsInt();
        for (int i = 0; i < contadorVitorias.length; i++) {
            if (contadorVitorias[i] == maximoVitorias) {
                System.out.println("O comportamento que ganha mais: " + comportamentos[i]);
                break;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        List<Propriedade> propriedades = leJogoConfig("JogoConfig.txt");

        int numSimulacoes = 300;
        simularJogos(numSimulacoes, propriedades);
    }
}
