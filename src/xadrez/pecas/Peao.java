package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaDeXadrez;

public class Peao extends PecaDeXadrez {

	public Peao(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "P";
	}
	
	@Override
	public boolean[][] movimentacoesPossiveis() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

		Posicao p = new Posicao(0, 0);

		if (getCor() == Cor.BRANCO) {
			// cima
			p.setValue(posicao.getLinha() - 1, posicao.getColuna());
			if (getTabuleiro().existePosicao(p) && !getTabuleiro().TemPecaNaPosicao(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			p.setValue(posicao.getLinha() - 2, posicao.getColuna());
			Posicao p2 = new Posicao(posicao.getLinha() - 1, posicao.getColuna());
			if (getTabuleiro().existePosicao(p) && !getTabuleiro().TemPecaNaPosicao(p) && getQtdMovimentos() == 0
					&& getTabuleiro().existePosicao(p2) && !getTabuleiro().TemPecaNaPosicao(p2)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			// cima + esquerda
			p.setValue(posicao.getLinha() - 1, posicao.getColuna() - 1);
			if (getTabuleiro().existePosicao(p) && TemPecaOponente(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			// cima + direita
			p.setValue(posicao.getLinha() - 1, posicao.getColuna() + 1);
			if (getTabuleiro().existePosicao(p) && TemPecaOponente(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
		} else {
			// baixo
			p.setValue(posicao.getLinha() + 1, posicao.getColuna());
			if (getTabuleiro().existePosicao(p) && !getTabuleiro().TemPecaNaPosicao(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			p.setValue(posicao.getLinha() + 2, posicao.getColuna());
			Posicao p2 = new Posicao(posicao.getLinha() + 1, posicao.getColuna());
			if (getTabuleiro().existePosicao(p) && !getTabuleiro().TemPecaNaPosicao(p) && getQtdMovimentos() == 0
					&& getTabuleiro().existePosicao(p2) && !getTabuleiro().TemPecaNaPosicao(p2)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			// baixo + esquerda
			p.setValue(posicao.getLinha() + 1, posicao.getColuna() - 1);
			if (getTabuleiro().existePosicao(p) && TemPecaOponente(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			// baixo + direita
			p.setValue(posicao.getLinha() + 1, posicao.getColuna() + 1);
			if (getTabuleiro().existePosicao(p) && TemPecaOponente(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
		}

		return mat;
	}

}
