package xadrez;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;

public abstract class PecaDeXadrez extends Peca {

	private Cor cor;

	private int qtdMovimentos;

	public void incrementarQtdMovimentos() {
		qtdMovimentos++;
	}

	public void DecrementarQtdMovimentos() {
		qtdMovimentos--;
	}

	public int getQtdMovimentos() {
		return qtdMovimentos;
	}

	public PecaDeXadrez(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro);
		this.cor = cor;
	}

	public Cor getCor() {
		return cor;
	}

	protected boolean TemPecaOponente(Posicao posicao) {
		PecaDeXadrez p = (PecaDeXadrez) getTabuleiro().peca(posicao);
		return (p != null) && (p.getCor() != cor);
	}

	public PosicaoNoXadrez getPosicaoNoXadrez() {
		return PosicaoNoXadrez.fromPosicao(posicao);
	}

}
