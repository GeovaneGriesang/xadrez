package tabuleiro;

import java.util.Arrays;

public class Tabuleiro {
	private int linhas;
	private int colunas;
	private Peca[][] pecas;
	public Tabuleiro(int linhas, int colunas) {
		this.linhas = linhas;
		this.colunas = colunas;
		this.pecas = new Peca[linhas][colunas];
	}
	public int getLinhas() {
		return linhas;
	}
	public void setLinhas(int linhas) {
		this.linhas = linhas;
	}
	public int getColunas() {
		return colunas;
	}
	public void setColunas(int colunas) {
		this.colunas = colunas;
	}
	@Override
	public String toString() {
		return "Tabuleiro [linhas=" + linhas + ", colunas=" + colunas + ", pecas=\n" + Arrays.toString(pecas) + "]";
	}
	
	
	
}
