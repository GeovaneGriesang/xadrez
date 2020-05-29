package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaDeXadrez;

public class Bispo extends PecaDeXadrez {

	public Bispo(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean[][] movimentacoesPossiveis() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

		Posicao p = new Posicao(0, 0);

		// acima + esquerda
		p.setValue(posicao.getLinha() - 1, posicao.getColuna() - 1);
		while (getTabuleiro().existePosicao(p) && !getTabuleiro().TemPecaNaPosicao(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setValue(p.getLinha() - 1, p.getColuna() - 1);
		}
		if (getTabuleiro().existePosicao(p) && TemPecaOponente(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// acima + direita
		p.setValue(posicao.getLinha() - 1, posicao.getColuna() + 1);
		while (getTabuleiro().existePosicao(p) && !getTabuleiro().TemPecaNaPosicao(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setValue(p.getLinha() - 1, p.getColuna() + 1);
		}
		if (getTabuleiro().existePosicao(p) && TemPecaOponente(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// baixo + esquerda
		p.setValue(posicao.getLinha() + 1, posicao.getColuna() - 1);
		while (getTabuleiro().existePosicao(p) && !getTabuleiro().TemPecaNaPosicao(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setValue(p.getLinha() + 1, p.getColuna() - 1);
		}
		if (getTabuleiro().existePosicao(p) && TemPecaOponente(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// baixo + direita
		p.setValue(posicao.getLinha() + 1, posicao.getColuna() + 1);
		while (getTabuleiro().existePosicao(p) && !getTabuleiro().TemPecaNaPosicao(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setValue(p.getLinha() + 1, p.getColuna() + 1);
		}
		if (getTabuleiro().existePosicao(p) && TemPecaOponente(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		return mat;
	}

	@Override
	public String toString() {
		return "B";
	}
}
