package tabuleiro;

import java.util.Arrays;

public class Tabuleiro {
	private int linhas;
	private int colunas;
	private Peca[][] pecas;

	public Tabuleiro(int linhas, int colunas) {
		if (linhas < 1 || colunas < 1) {
			throw new ExcecoesDoTabuleiro("Erro ao criar o tabuleiro: linhas ou coluna deve ser maior que 1");
		}

		this.linhas = linhas;
		this.colunas = colunas;
		this.pecas = new Peca[linhas][colunas];
	}

	public int getLinhas() {
		return linhas;
	}

	public int getColunas() {
		return colunas;
	}

	public Peca peca(int linha, int coluna) {
		if (!existePosicao(linha, coluna)){
			throw new ExcecoesDoTabuleiro("Posição não existe no tabuleiro!");
		}
		return pecas[linha][coluna];
	}

	public Peca peca(Posicao posicao) {
		if (!existePosicao(posicao)) {
			throw new ExcecoesDoTabuleiro("Posição não existe no tabuleiro!");
		}
		return pecas[posicao.getLinha()][posicao.getColuna()];
	}

	@Override
	public String toString() {
		return "Tabuleiro [linhas=" + linhas + ", colunas=" + colunas + ", pecas=\n" + Arrays.toString(pecas) + "]";
	}

	public void colocarPeca(Peca peca, Posicao posicao) {
		if (TemPecaNaPosicao(posicao)) {
			throw new ExcecoesDoTabuleiro("Já existe uma peça nessa posição: "+posicao);
		}
		pecas[posicao.getLinha()][posicao.getColuna()] = peca;
		peca.posicao = posicao;
	}

	public boolean existePosicao(int linha, int coluna) {
		return linha >= 0 && linha < linhas && coluna < colunas && coluna >= 0;
	}

	public boolean existePosicao(Posicao posicao) {
		return existePosicao(posicao.getLinha(), posicao.getColuna());
	}

	public boolean TemPecaNaPosicao(Posicao posicao) {
		if (!existePosicao(posicao)){
			throw new ExcecoesDoTabuleiro("Posição não existe no tabuleiro!");
		}
		return peca(posicao) != null;
	}
	
	public Peca removerPeca(Posicao posicao) {
		if (!existePosicao(posicao)) {
			throw new ExcecoesDoTabuleiro("Posição não existe no tabuleiro!");
		}
		Peca aux = peca(posicao);
		if (aux == null) {
			return null;
		}
		aux.posicao = null;
		pecas[posicao.getLinha()][posicao.getColuna()] = null;
		return aux;
		
	}

}
