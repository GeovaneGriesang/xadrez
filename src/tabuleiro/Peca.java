package tabuleiro;

public abstract class Peca {

	protected Posicao posicao;
	private Tabuleiro tabuleiro;

	public Peca(Tabuleiro tabuleiro) {
		this.tabuleiro = tabuleiro;
		this.posicao = null;
	}

	protected Tabuleiro getTabuleiro() {
		return tabuleiro;
	}

	public abstract boolean[][] movimentacoesPossiveis();

	public boolean movimentoPossivel(Posicao posicao) {
		return movimentacoesPossiveis()[posicao.getLinha()][posicao.getColuna()];
	}

	public boolean existePeloMenosUmMovimentoPossivel() {
		boolean[][] mat = movimentacoesPossiveis();
		for (int i = 0; i < mat.length; i++) {
			for (int j = 0; j < mat.length; j++) {
				if (mat[i][j]) {
					return true;
				}
			}
		}
		return false;
	}
}
