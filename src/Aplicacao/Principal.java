package aplicacao;

import java.util.Scanner;

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

		System.out.println();
		PartidaDeXadrez partida = new PartidaDeXadrez();

		Scanner sc = new Scanner(System.in);

		while (true) {
			Interface.imprimirTabuleiro(partida.getPecas());

			System.out.println();
			System.out.print("Origem: ");
			PosicaoNoXadrez origem = Interface.lerPosicaoNoXadrez(sc);

			System.out.println();
			System.out.print("Destino: ");
			PosicaoNoXadrez destino = Interface.lerPosicaoNoXadrez(sc);
			
			PecaDeXadrez pecaCapturada = partida.MoverPeca(origem, destino);
		}
	}

}
