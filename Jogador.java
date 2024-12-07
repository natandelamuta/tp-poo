import java.util.ArrayList;
import java.util.List;

class Jogador {
    int id;
    int moedas;
    List<Propriedade> propriedades;
    String comportamento;

    public Jogador(int id, String comportamento) {
        this.id = id;
        this.moedas = 300;
        this.propriedades = new ArrayList<>();
        this.comportamento = comportamento;
    }

    public boolean podeComprar(Propriedade propriedade) {
        if (propriedade.proprietario != null) {
            return false;
        }
        
        switch (comportamento) {
            case "impulsivo":
                return true;
            case "exigente":
                return propriedade.aluguel > 50;
            case "cauteloso":
                return this.moedas >= propriedade.preco + 80;
            case "aleatorio":
                return Math.random() < 0.5;
            default:
                return false;
        }
    }

    public void compraPropriedade(Propriedade propriedade) {
        if (this.moedas >= propriedade.preco) {
            this.moedas -= propriedade.preco;
            this.propriedades.add(propriedade);
            propriedade.proprietario = this;
        }
    }

    public void pagaAluguel(Propriedade propriedade) {
        if (propriedade.proprietario != null && propriedade.proprietario != this) {
            this.moedas -= propriedade.aluguel;
            propriedade.proprietario.moedas += propriedade.aluguel;
        }
    }
}
