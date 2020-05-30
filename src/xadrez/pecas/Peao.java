package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PartidaDeXadrez;
import xadrez.PecaDeXadrez;

public class Peao extends PecaDeXadrez {

	private PartidaDeXadrez partidaDeXadrez;

	public Peao(Tabuleiro tabuleiro, Cor cor, PartidaDeXadrez partidaDeXadrez) {
		super(tabuleiro, cor);
		this.partidaDeXadrez = partidaDeXadrez;
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

			// enPassant
			if (posicao.getLinha() == 3) {
				Posicao esquerda = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
				if (getTabuleiro().existePosicao(esquerda) && TemPecaOponente(esquerda)
						&& getTabuleiro().peca(esquerda) == partidaDeXadrez.getEnPassantVulneravel()) {
					mat[esquerda.getLinha() - 1][esquerda.getColuna()] = true;
				}

				Posicao direita = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
				if (getTabuleiro().existePosicao(direita) && TemPecaOponente(direita)
						&& getTabuleiro().peca(direita) == partidaDeXadrez.getEnPassantVulneravel()) {
					mat[direita.getLinha() - 1][direita.getColuna()] = true;
				}

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
			
			// enPassant
			if (posicao.getLinha() == 4) {
				Posicao esquerda = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
				if (getTabuleiro().existePosicao(esquerda) && TemPecaOponente(esquerda)
						&& getTabuleiro().peca(esquerda) == partidaDeXadrez.getEnPassantVulneravel()) {
					mat[esquerda.getLinha() + 1][esquerda.getColuna()] = true;
				}

				Posicao direita = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
				if (getTabuleiro().existePosicao(direita) && TemPecaOponente(direita)
						&& getTabuleiro().peca(direita) == partidaDeXadrez.getEnPassantVulneravel()) {
					mat[direita.getLinha() + 1][direita.getColuna()] = true;
				}

			}
			
		}

		return mat;
	}

}
