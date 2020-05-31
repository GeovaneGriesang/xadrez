package aplicacao;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
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
		List<PecaDeXadrez> capturadas = new ArrayList<>();
		
		Scanner sc = new Scanner(System.in);

		while (!partida.getCheckMate()) {
			try {
				Interface.LimparTela();
				Interface.imprimirPartida(partida, capturadas);

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
				if (pecaCapturada != null) {
					capturadas.add(pecaCapturada);
				
				if (partida.getPromocao() != null) {
					System.out.println("Informe para qual peça o peão deve ser promovido (T/C/B/D): ");
					String tipo = sc.nextLine();
					partida.substituirPecaPromovida(tipo);
				}
				
				}
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
		Interface.LimparTela();
		Interface.imprimirPartida(partida, capturadas);
	}

}
