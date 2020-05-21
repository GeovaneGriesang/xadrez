package aplicacao;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;

public class Principal {

	public static void main(String[] args) {
		
		Posicao posicao = new Posicao(3, 5);
		System.out.println(posicao);
		
		Tabuleiro tabuleiro = new Tabuleiro(8, 8);
		System.out.println("\n"+tabuleiro);

	}

}
