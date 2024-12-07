import java.util.ArrayList;
import java.util.List;

public class Jogo {
    List<Jogador> jogadores;
    List<Propriedade> propriedades;
    int rodadas;

    public Jogo(List<Jogador> jogadores, List<Propriedade> propriedades) {
        this.jogadores = jogadores;
        this.propriedades = propriedades;
        this.rodadas = 0;
    }

    public boolean jogaRodada() {
        for (Jogador jogador : jogadores) {
            if (jogador.moedas < 0) continue;

            int rolaDado = (int) (Math.random() * 6) + 1;
            Propriedade propriedade = propriedades.get(rolaDado % propriedades.size());

            if (propriedade.proprietario == null && jogador.podeComprar(propriedade)) {
                jogador.compraPropriedade(propriedade);
            } else {
                jogador.pagaAluguel(propriedade);
            }

            if (jogador.moedas < 0) {
                jogador.propriedades.clear();
                jogador.moedas = 0;
            }
        }

        this.rodadas++;

        List<Jogador> activeJogadores = new ArrayList<>();
        for (Jogador jogador : jogadores) {
            if (jogador.moedas >= 0) activeJogadores.add(jogador);
        }

        return activeJogadores.size() == 1 || this.rodadas >= 1000;
    }

    public String run() {
        while (!jogaRodada()) {
        }

        Jogador vitorioso = null;
        for (Jogador jogador : jogadores) {
            if (jogador.moedas >= 0 && (vitorioso == null || jogador.moedas > vitorioso.moedas)) {
                vitorioso = jogador;
            }
        }
        return vitorioso.comportamento;
    }
}
