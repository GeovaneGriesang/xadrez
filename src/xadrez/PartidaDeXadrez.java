package xadrez;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.pecas.Bispo;
import xadrez.pecas.Cavalo;
import xadrez.pecas.Dama;
import xadrez.pecas.Peao;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaDeXadrez {

	private Tabuleiro tabuleiro;
	private int turno;
	private Cor jogadorAtual;
	private boolean check = false;
	private boolean checkMate = false;
	private PecaDeXadrez enPassantVulneravel;

	private List<Peca> pecasNoTabuleiro = new ArrayList<>();
	private List<Peca> pecasCapturadas = new ArrayList<>();

	public PartidaDeXadrez() {
		tabuleiro = new Tabuleiro(8, 8);
		this.turno = 1;
		this.jogadorAtual = Cor.BRANCO;
		iniciarConfiguracao();
	}

	public boolean getCheck() {
		return check;
	}

	public boolean getCheckMate() {
		return checkMate;
	}

	public int getTurno() {
		return turno;
	}

	public Cor getJogadorAtual() {
		return jogadorAtual;
	}

	public PecaDeXadrez[][] getPecas() {
		PecaDeXadrez[][] mat = new PecaDeXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
		for (int i = 0; i < tabuleiro.getLinhas(); i++) {
			for (int j = 0; j < tabuleiro.getColunas(); j++) {
				mat[i][j] = (PecaDeXadrez) tabuleiro.peca(i, j);
			}
		}
		return mat;
	}

	public boolean[][] movimentosPossiveis(PosicaoNoXadrez posicaoOrigem) {
		Posicao posicao = posicaoOrigem.toPosicao();
		validarPosicaoOrigem(posicao);
		return tabuleiro.peca(posicao).movimentacoesPossiveis();
	}

	public PecaDeXadrez MoverPeca(PosicaoNoXadrez posicaoOrigem, PosicaoNoXadrez posicaoDestino) {
		Posicao origem = posicaoOrigem.toPosicao();
		Posicao destino = posicaoDestino.toPosicao();
		validarPosicaoOrigem(origem);
		validarPosicaoDestino(origem, destino);
		Peca pecaCapturada = fazerMovimento(origem, destino);

		if (testarCheck(jogadorAtual)) {
			desfazerMovimento(origem, destino, pecaCapturada);
			throw new ExcecoesDoXadrez("Você não pode se colocar em check!");
		}

		PecaDeXadrez pecaMovida = (PecaDeXadrez) tabuleiro.peca(destino);

		check = (testarCheck(oponente(jogadorAtual))) ? true : false;

		if (testarCheckMate(oponente(jogadorAtual))) {
			checkMate = true;
		} else {
			proximoTurno();
		}

		// Peão vulnerável ao enPassant
		if (pecaMovida instanceof Peao
				&& (destino.getLinha() == origem.getLinha() - 2 || destino.getLinha() == origem.getLinha() + 2)) {
			enPassantVulneravel = pecaMovida;
		} else {
			enPassantVulneravel = null;
		}

		return (PecaDeXadrez) pecaCapturada;
	};

	private Peca fazerMovimento(Posicao origem, Posicao destino) {
		PecaDeXadrez p = (PecaDeXadrez) tabuleiro.removerPeca(origem);
		p.incrementarQtdMovimentos();
		Peca capturada = tabuleiro.removerPeca(destino);
		tabuleiro.colocarPeca(p, destino);

		if (capturada != null) {
			pecasNoTabuleiro.remove(capturada);
			pecasCapturadas.add(capturada);
		}

		// Roque pequeno (rei já se moveu, então, precisamos mover a torre)
		if (p instanceof Rei && destino.getColuna() == origem.getColuna() + 2) {
			Posicao origemTorre = new Posicao(origem.getLinha(), origem.getColuna() + 3);
			Posicao destinoTorre = new Posicao(origem.getLinha(), origem.getColuna() + 1);
			PecaDeXadrez torre = (PecaDeXadrez) tabuleiro.removerPeca(origemTorre);
			tabuleiro.colocarPeca(torre, destinoTorre);
			torre.incrementarQtdMovimentos();
		}

		// Roque grande (rei já se moveu, então, precisamos mover a torre)
		if (p instanceof Rei && destino.getColuna() == origem.getColuna() - 2) {
			Posicao origemTorre = new Posicao(origem.getLinha(), origem.getColuna() - 4);
			Posicao destinoTorre = new Posicao(origem.getLinha(), origem.getColuna() - 1);
			PecaDeXadrez torre = (PecaDeXadrez) tabuleiro.removerPeca(origemTorre);
			tabuleiro.colocarPeca(torre, destinoTorre);
			torre.incrementarQtdMovimentos();
		}

		// en passant
		if (p instanceof Peao) {
			if (origem.getColuna() != destino.getColuna() && capturada == null) {
				Posicao posicaoPeao;
				if (p.getCor() == Cor.BRANCO) {
					posicaoPeao = new Posicao(destino.getLinha() + 1, destino.getColuna());
				} else {
					posicaoPeao = new Posicao(destino.getLinha() - 1, destino.getColuna());
				}
				capturada = tabuleiro.removerPeca(posicaoPeao);
				pecasCapturadas.add(capturada);
				pecasNoTabuleiro.remove(capturada);
			}
		}

		return capturada;
	}

	public void desfazerMovimento(Posicao origem, Posicao destino, Peca pecaCapturada) {
		PecaDeXadrez p = (PecaDeXadrez) tabuleiro.removerPeca(destino);
		p.DecrementarQtdMovimentos();
		tabuleiro.colocarPeca(p, origem);

		if (pecaCapturada != null) {
			tabuleiro.colocarPeca(pecaCapturada, destino);
			pecasCapturadas.remove(pecaCapturada);
			pecasNoTabuleiro.add(pecaCapturada);

		}

		// Roque pequeno (rei já se moveu, então, precisamos mover a torre)
		if (p instanceof Rei && destino.getColuna() == origem.getColuna() + 2) {
			Posicao origemTorre = new Posicao(origem.getLinha(), origem.getColuna() + 3);
			Posicao destinoTorre = new Posicao(origem.getLinha(), origem.getColuna() + 1);
			PecaDeXadrez torre = (PecaDeXadrez) tabuleiro.removerPeca(destinoTorre);
			tabuleiro.colocarPeca(torre, origemTorre);
			torre.DecrementarQtdMovimentos();
		}

		// Roque grande (rei já se moveu, então, precisamos mover a torre)
		if (p instanceof Rei && destino.getColuna() == origem.getColuna() - 2) {
			Posicao origemTorre = new Posicao(origem.getLinha(), origem.getColuna() - 4);
			Posicao destinoTorre = new Posicao(origem.getLinha(), origem.getColuna() - 1);
			PecaDeXadrez torre = (PecaDeXadrez) tabuleiro.removerPeca(destinoTorre);
			tabuleiro.colocarPeca(torre, origemTorre);
			torre.DecrementarQtdMovimentos();
		}

		// en passant
		if (p instanceof Peao) {
			if (origem.getColuna() != destino.getColuna() && pecaCapturada == enPassantVulneravel) {
				PecaDeXadrez peao = (PecaDeXadrez)tabuleiro.removerPeca(destino);
				Posicao posicaoPeao;
				if (p.getCor() == Cor.BRANCO) { 
					posicaoPeao = new Posicao(3, destino.getColuna());
				} else {
					posicaoPeao = new Posicao(4, destino.getColuna());
				}
				tabuleiro.colocarPeca(peao, posicaoPeao);
			}
		}

	}

	private void validarPosicaoOrigem(Posicao posicao) {
		if (!tabuleiro.TemPecaNaPosicao(posicao)) {
			throw new ExcecoesDoXadrez("Não existe peça na posição de origem!");
		}
		if (jogadorAtual != ((PecaDeXadrez) tabuleiro.peca(posicao)).getCor()) {
			throw new ExcecoesDoXadrez("A peça escolhida não é sua!");
		}
		if (!tabuleiro.peca(posicao).existePeloMenosUmMovimentoPossivel()) {
			throw new ExcecoesDoXadrez("Não existem movimentos possível para essa peça!");
		}

	}

	private void validarPosicaoDestino(Posicao origem, Posicao destino) {
		if (!tabuleiro.peca(origem).movimentoPossivel(destino)) {
			throw new ExcecoesDoXadrez("A peça escolhida não pode-se mover para a posição de destino!");
		}
	}

	public void proximoTurno() {
		turno++;
		jogadorAtual = (jogadorAtual == Cor.BRANCO) ? Cor.PRETO : Cor.BRANCO;
	}

	public void colocarNovaPeca(char coluna, int linha, PecaDeXadrez peca) {
		tabuleiro.colocarPeca(peca, new PosicaoNoXadrez(coluna, linha).toPosicao());
		pecasNoTabuleiro.add(peca);
	}

	public Cor oponente(Cor cor) {
		return (cor == Cor.BRANCO) ? Cor.PRETO : Cor.BRANCO;
	}

	public PecaDeXadrez rei(Cor cor) {
		List<Peca> list = pecasNoTabuleiro.stream().filter(x -> ((PecaDeXadrez) x).getCor() == cor)
				.collect(Collectors.toList());
		for (Peca p : list) {
			if (p instanceof Rei) {
				return (PecaDeXadrez) p;
			}
		}
		throw new IllegalStateException("Não existe o rei " + cor + " no tabuleiro!");

	}

	public boolean testarCheck(Cor cor) {
		Posicao posicaoRei = rei(cor).getPosicaoNoXadrez().toPosicao();
		List<Peca> pecasOponentes = pecasNoTabuleiro.stream().filter(x -> ((PecaDeXadrez) x).getCor() == oponente(cor))
				.collect(Collectors.toList());
		for (Peca p : pecasOponentes) {
			boolean[][] mat = p.movimentacoesPossiveis();
			if (mat[posicaoRei.getLinha()][posicaoRei.getColuna()]) {
				return true;
			}
		}
		return false;
	}

	public boolean testarCheckMate(Cor cor) {
		// Para ser checkMate, precisa estar em check!
		if (!testarCheck(cor)) {
			return false;
		}
		List<Peca> list = pecasNoTabuleiro.stream().filter(x -> ((PecaDeXadrez) x).getCor() == cor)
				.collect(Collectors.toList());
		for (Peca p : list) {
			boolean[][] mat = p.movimentacoesPossiveis();
			for (int i = 0; i < tabuleiro.getLinhas(); i++) {
				for (int j = 0; j < tabuleiro.getColunas(); j++) {
					if (mat[i][j]) {
						Posicao origem = ((PecaDeXadrez) p).getPosicaoNoXadrez().toPosicao();
						Posicao destino = new Posicao(i, j);
						Peca pecaCapturada = fazerMovimento(origem, destino);
						boolean testaCheck = testarCheck(cor);
						desfazerMovimento(origem, destino, pecaCapturada);
						if (!testaCheck) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}

	public PecaDeXadrez getEnPassantVulneravel() {
		return enPassantVulneravel;
	};

	public void iniciarConfiguracao() {
		colocarNovaPeca('a', 1, new Torre(tabuleiro, Cor.BRANCO));
		colocarNovaPeca('b', 1, new Cavalo(tabuleiro, Cor.BRANCO));
		colocarNovaPeca('c', 1, new Bispo(tabuleiro, Cor.BRANCO));
		colocarNovaPeca('d', 1, new Dama(tabuleiro, Cor.BRANCO));
		colocarNovaPeca('e', 1, new Rei(tabuleiro, Cor.BRANCO, this));
		colocarNovaPeca('f', 1, new Bispo(tabuleiro, Cor.BRANCO));
		colocarNovaPeca('g', 1, new Cavalo(tabuleiro, Cor.BRANCO));
		colocarNovaPeca('h', 1, new Torre(tabuleiro, Cor.BRANCO));
		colocarNovaPeca('a', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		colocarNovaPeca('b', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		colocarNovaPeca('c', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		colocarNovaPeca('d', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		colocarNovaPeca('e', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		colocarNovaPeca('f', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		colocarNovaPeca('g', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		colocarNovaPeca('h', 2, new Peao(tabuleiro, Cor.BRANCO, this));

		colocarNovaPeca('a', 8, new Torre(tabuleiro, Cor.PRETO));
		colocarNovaPeca('b', 8, new Cavalo(tabuleiro, Cor.PRETO));
		colocarNovaPeca('c', 8, new Bispo(tabuleiro, Cor.PRETO));
		colocarNovaPeca('d', 8, new Dama(tabuleiro, Cor.PRETO));
		colocarNovaPeca('e', 8, new Rei(tabuleiro, Cor.PRETO, this));
		colocarNovaPeca('f', 8, new Bispo(tabuleiro, Cor.PRETO));
		colocarNovaPeca('g', 8, new Cavalo(tabuleiro, Cor.PRETO));
		colocarNovaPeca('h', 8, new Torre(tabuleiro, Cor.PRETO));
		colocarNovaPeca('a', 7, new Peao(tabuleiro, Cor.PRETO, this));
		colocarNovaPeca('b', 7, new Peao(tabuleiro, Cor.PRETO, this));
		colocarNovaPeca('c', 7, new Peao(tabuleiro, Cor.PRETO, this));
		colocarNovaPeca('d', 7, new Peao(tabuleiro, Cor.PRETO, this));
		colocarNovaPeca('e', 7, new Peao(tabuleiro, Cor.PRETO, this));
		colocarNovaPeca('f', 7, new Peao(tabuleiro, Cor.PRETO, this));
		colocarNovaPeca('g', 7, new Peao(tabuleiro, Cor.PRETO, this));
		colocarNovaPeca('h', 7, new Peao(tabuleiro, Cor.PRETO, this));

	}
}
