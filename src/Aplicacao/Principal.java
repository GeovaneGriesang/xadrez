package aplicacao;

import java.util.InputMismatchException;
import java.util.Scanner;

import tabuleiro.ExcecoesDoTabuleiro;
import xadrez.ExcecoesDoXadrez;
import xadrez.PartidaDeXadrez;
import xadrez.PecaDeXadrez;
import xadrez.PosicaoNoXadrez;

public class Principal {

	public static void main(String[] args) {

		/*
		 * Posicao posicao = new Posicao(3, 5); System.out.println(posicao);
		 * 
		 * Tabuleiro tabuleiro = new Tabuleiro(8, 8);
		 * System.out.println("\n"+tabuleiro);
		 */

		//Locale.
		System.out.println();
		PartidaDeXadrez partida = new PartidaDeXadrez();

		Scanner sc = new Scanner(System.in);

		while (true) {
			try {
				Interface.LimparTela();
				Interface.imprimirTabuleiro(partida.getPecas());

				System.out.println();
				System.out.print("Origem: ");
				PosicaoNoXadrez origem = Interface.lerPosicaoNoXadrez(sc);

				boolean [][] movimentacoesPossiveis = partida.movimentosPossiveis(origem);
				Interface.LimparTela();
				Interface.imprimirTabuleiro(partida.getPecas(), movimentacoesPossiveis);
				
				System.out.println();
				System.out.print("Destino: ");
				PosicaoNoXadrez destino = Interface.lerPosicaoNoXadrez(sc);

				PecaDeXadrez pecaCapturada = partida.MoverPeca(origem, destino);
			} catch (ExcecoesDoXadrez e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			} catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			} catch (ExcecoesDoTabuleiro e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
		}
	}

}
