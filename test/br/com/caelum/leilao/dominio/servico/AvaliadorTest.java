package br.com.caelum.leilao.dominio.servico;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.dominio.Usuario;

public class AvaliadorTest {
	
	@Test
	public void avaliaMaiorEMenorLanceCrescente() {
		
		Usuario rafael = new Usuario("Rafael");
		Usuario carlos = new Usuario("Carlos");
		Usuario thais = new Usuario("Thais");
		
		Leilao leilao = new Leilao("Smart TV LG 40");
		
		leilao.propoe(new Lance(rafael, 250));
		leilao.propoe(new Lance(carlos, 300));
		leilao.propoe(new Lance(thais, 400));
		
		double maiorLance = 400;
		double menorLance = 250;
		
		Avaliador leiloeiro = new Avaliador();
		leiloeiro.avalia(leilao);
		
		assertEquals(maiorLance, leiloeiro.getMaiorLance(), 0.00001);
		assertEquals(menorLance, leiloeiro.getMenorLance(), 0.00001);

	}
	
	@Test
	public void avaliaMaiorEMenorLanceDecrescente() {
		
		Usuario rafael = new Usuario("Rafael");
		Usuario carlos = new Usuario("Carlos");
		Usuario thais = new Usuario("Thais");
		
		Leilao leilao = new Leilao("Smart TV LG 40");
		
		leilao.propoe(new Lance(rafael, 400));
		leilao.propoe(new Lance(carlos, 300));
		leilao.propoe(new Lance(thais, 250));
		
		double maiorLance = 400;
		double menorLance = 250;
		
		Avaliador leiloeiro = new Avaliador();
		leiloeiro.avalia(leilao);
		
		assertEquals(maiorLance, leiloeiro.getMaiorLance(), 0.00001);
		assertEquals(menorLance, leiloeiro.getMenorLance(), 0.00001);

	}
	
	@Test
	public void avaliaMaiorEMenorLanceAleatorio() {
		
		Usuario rafael = new Usuario("Rafael");
		Usuario carlos = new Usuario("Carlos");
		Usuario thais = new Usuario("Thais");
		
		Leilao leilao = new Leilao("Smart TV LG 40");
		
		leilao.propoe(new Lance(rafael, 200));
		leilao.propoe(new Lance(carlos, 450));
		leilao.propoe(new Lance(thais, 120));
		leilao.propoe(new Lance(thais, 700));
		leilao.propoe(new Lance(thais, 630));
		leilao.propoe(new Lance(thais, 230));
		
		double maiorLance = 700;
		double menorLance = 120;
		
		Avaliador leiloeiro = new Avaliador();
		leiloeiro.avalia(leilao);
		
		assertEquals(maiorLance, leiloeiro.getMaiorLance(), 0.00001);
		assertEquals(menorLance, leiloeiro.getMenorLance(), 0.00001);

	}
	
	@Test
	public void calculaValorMedio() {
		Usuario rafael = new Usuario("Rafael");
		Usuario carlos = new Usuario("Carlos");
		Usuario thais = new Usuario("Thais");
		
		Leilao leilao = new Leilao("Smart TV LG 40");
		
		leilao.propoe(new Lance(rafael, 500));
		leilao.propoe(new Lance(carlos, 300));
		leilao.propoe(new Lance(thais, 400));
		
		Avaliador leiloeiro = new Avaliador();
		leiloeiro.avalia(leilao);
		
		assertEquals(400, leiloeiro.getValorMedio(), 0.00001);
	}
	
	@Test
	public void calculaValorMedioZeroLance() {
		
		Leilao leilao = new Leilao("Smart TV LG 40");
		
		Avaliador leiloeiro = new Avaliador();
		leiloeiro.avalia(leilao);
		
		assertEquals(0, leiloeiro.getValorMedio(), 0.00001);
	}
	
	@Test
	public void deveEntenderLeilaoComApenasUmLance() {
		
		Usuario rafael = new Usuario("Rafael");
		
		Leilao leilao = new Leilao("Smartphone");
		leilao.propoe(new Lance(rafael, 600.0));
		
		Avaliador leiloeiro = new Avaliador();
		leiloeiro.avalia(leilao);
		
		assertEquals(600, leiloeiro.getMaiorLance(), 0.00001);
		assertEquals(600, leiloeiro.getMenorLance(), 0.00001);
		
	}
	
	@Test
	public void deveEncontrarOsTresMaioresLances() {
		Usuario rafael = new Usuario("Rafael");
		Usuario carlos = new Usuario("Carlos");
		
		Leilao leilao = new Leilao("Geladeira");
		leilao.propoe(new Lance(rafael, 300.0));
		leilao.propoe(new Lance(carlos, 800.0));
		leilao.propoe(new Lance(rafael, 250.0));
		leilao.propoe(new Lance(carlos, 850.0));
		leilao.propoe(new Lance(rafael, 999.0));
		
		Avaliador leiloeiro = new Avaliador();
		leiloeiro.avalia(leilao);
		
		assertEquals(3, leiloeiro.getTresMaiores().size());
		assertEquals(999.0, leiloeiro.getTresMaiores().get(0).getValor(), 0.00001);
		assertEquals(850.0, leiloeiro.getTresMaiores().get(1).getValor(), 0.00001);
		assertEquals(800.0, leiloeiro.getTresMaiores().get(2).getValor(), 0.00001);
	}
	
	@Test
	public void deveEncontrarOsTresMaioresLancesCom2Elementos() {
		Usuario rafael = new Usuario("Rafael");
		Usuario carlos = new Usuario("Carlos");
		
		Leilao leilao = new Leilao("Geladeira");
		leilao.propoe(new Lance(rafael, 300.0));
		leilao.propoe(new Lance(carlos, 800.0));

		
		Avaliador leiloeiro = new Avaliador();
		leiloeiro.avalia(leilao);
		
		assertEquals(2, leiloeiro.getTresMaiores().size());
		assertEquals(800.0, leiloeiro.getTresMaiores().get(0).getValor(), 0.00001);
		assertEquals(300.0, leiloeiro.getTresMaiores().get(1).getValor(), 0.00001);
	}
	
	@Test
	public void deveEncontrarOsTresMaioresLancesComListaVazia() {
		
		Leilao leilao = new Leilao("Geladeira");

		
		Avaliador leiloeiro = new Avaliador();
		leiloeiro.avalia(leilao);

		assertEquals(0, leiloeiro.getTresMaiores().size());
	}
	
}
