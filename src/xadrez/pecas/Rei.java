package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PartidaDeXadrez;
import xadrez.PecaDeXadrez;

public class Rei extends PecaDeXadrez {

	private PartidaDeXadrez partidaDeXadrez;

	public Rei(Tabuleiro tabuleiro, Cor cor, PartidaDeXadrez partidaDeXadrez) {
		super(tabuleiro, cor);
		this.partidaDeXadrez = partidaDeXadrez;
		// TODO Auto-generated constructor stub
	}

	public boolean testarTorreRoque(Posicao posicao) {
		PecaDeXadrez p = (PecaDeXadrez) getTabuleiro().peca(posicao);
		return (p != null) && (p instanceof Torre) && (p.getCor() == getCor()) && (p.getQtdMovimentos() == 0);
	}

	@Override
	public String toString() {
		return "R";
	}

	public boolean podeMover(Posicao posicao) {
		PecaDeXadrez p = (PecaDeXadrez) getTabuleiro().peca(posicao);
		return (p == null) || (p.getCor() != getCor());
	}

	@Override
	public boolean[][] movimentacoesPossiveis() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

		Posicao p = new Posicao(0, 0);

		// acima
		p.setValue(posicao.getLinha() - 1, posicao.getColuna());
		if (getTabuleiro().existePosicao(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		// baixo
		p.setValue(posicao.getLinha() + 1, posicao.getColuna());
		if (getTabuleiro().existePosicao(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		// esquerda
		p.setValue(posicao.getLinha(), posicao.getColuna() - 1);
		if (getTabuleiro().existePosicao(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		// direita
		p.setValue(posicao.getLinha(), posicao.getColuna() + 1);
		if (getTabuleiro().existePosicao(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		// noroeste (cima + esquerda)
		p.setValue(posicao.getLinha() - 1, posicao.getColuna() - 1);
		if (getTabuleiro().existePosicao(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		// nordeste (cima + direita)
		p.setValue(posicao.getLinha() - 1, posicao.getColuna() + 1);
		if (getTabuleiro().existePosicao(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		// sudoeste (baixo + esquerda)
		p.setValue(posicao.getLinha() + 1, posicao.getColuna() - 1);
		if (getTabuleiro().existePosicao(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		// sudeste (baixo + direita)
		p.setValue(posicao.getLinha() + 1, posicao.getColuna() + 1);
		if (getTabuleiro().existePosicao(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// Movimento especial -> Roque
		if (getQtdMovimentos() == 0 && !partidaDeXadrez.getCheck()) {
			// Roque pequeno (lado do Rei)
			Posicao posicaoTorre1 = new Posicao(posicao.getLinha(), (posicao.getColuna() + 3));
			if (testarTorreRoque(posicaoTorre1)) {
				Posicao p1 = new Posicao(posicao.getLinha(), (posicao.getColuna() + 1));
				Posicao p2 = new Posicao(posicao.getLinha(), (posicao.getColuna() + 2));
				if (getTabuleiro().peca(p1) == null && getTabuleiro().peca(p2) == null) {
					mat[posicao.getLinha()][posicao.getColuna() + 2] = true;
				}
			}
			// Roque grande (lado da Dama)
			Posicao posicaoTorre2 = new Posicao(posicao.getLinha(), (posicao.getColuna() - 4));
			if (testarTorreRoque(posicaoTorre2)) {
				Posicao p1 = new Posicao(posicao.getLinha(), (posicao.getColuna() - 1));
				Posicao p2 = new Posicao(posicao.getLinha(), (posicao.getColuna() - 2));
				Posicao p3 = new Posicao(posicao.getLinha(), (posicao.getColuna() - 3));
				if (getTabuleiro().peca(p1) == null && getTabuleiro().peca(p2) == null
						&& getTabuleiro().peca(p3) == null) {
					mat[posicao.getLinha()][posicao.getColuna() - 2] = true;
				}
			}
		}

		return mat;
	}

}
