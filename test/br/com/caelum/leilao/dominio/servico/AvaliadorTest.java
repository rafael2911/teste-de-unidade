package br.com.caelum.leilao.dominio.servico;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.dominio.Usuario;
import br.com.caelum.leilao.dominio.builder.CriadorDeLeilao;

public class AvaliadorTest {
	
	private Avaliador leiloeiro;
	private Usuario rafael;
	private Usuario carlos;
	private Usuario thais;
	
	@BeforeEach
	private void criaAvaliador() {
		leiloeiro = new Avaliador();
		
		rafael = new Usuario("Rafael");
		carlos = new Usuario("Carlos");
		thais = new Usuario("Thais");
		
	}

	@Test
	public void avaliaMaiorEMenorLanceCrescente() {
		
		Leilao leilao = new CriadorDeLeilao().para("Smart TV LG 40")
				.lance(rafael, 250)
				.lance(carlos, 300)
				.lance(thais, 400)
				.constroi();
		
		double maiorLance = 400;
		double menorLance = 250;
		
		leiloeiro.avalia(leilao);
		
		// Utilizando o hamcrest
		assertThat(leiloeiro.getMaiorLance(), equalTo(maiorLance));
		assertThat(leiloeiro.getMenorLance(), equalTo(menorLance));

	}
	
	@Test
	public void avaliaMaiorEMenorLanceDecrescente() {
		
		Leilao leilao = new CriadorDeLeilao().para("Smart TV LG 40")
				.lance(rafael, 400)
				.lance(carlos, 300)
				.lance(thais, 250)
				.constroi();
		
		double maiorLance = 400;
		double menorLance = 250;
		
		leiloeiro.avalia(leilao);
		
		assertEquals(maiorLance, leiloeiro.getMaiorLance(), 0.00001);
		assertEquals(menorLance, leiloeiro.getMenorLance(), 0.00001);

	}
	
	@Test
	public void avaliaMaiorEMenorLanceAleatorio() {
		
		Leilao leilao = new CriadorDeLeilao().para("Smart TV LG 40")
				.lance(rafael, 200)
				.lance(carlos, 450)
				.lance(thais, 120)
				.lance(thais, 700)
				.lance(thais, 630)
				.lance(thais, 230)
				.constroi();
		
		double maiorLance = 450;
		double menorLance = 120;
		
		leiloeiro.avalia(leilao);
		
		assertEquals(maiorLance, leiloeiro.getMaiorLance(), 0.00001);
		assertEquals(menorLance, leiloeiro.getMenorLance(), 0.00001);

	}
	
	@Test
	public void calculaValorMedio() {
		
		Leilao leilao = new CriadorDeLeilao().para("Smart TV LG 40")
				.lance(rafael, 500)
				.lance(carlos, 300)
				.lance(thais, 400)
				.constroi();
		
		leiloeiro.avalia(leilao);
		
		assertEquals(400, leiloeiro.getValorMedio(), 0.00001);
	}
	
	@Test()
	public void calculaValorMedioZeroLance() {
		
		Leilao leilao = new CriadorDeLeilao().para("Smart TV LG 40").constroi();
		
		Exception exception = assertThrows(RuntimeException.class, () -> leiloeiro.avalia(leilao));
		
		assertEquals("Não é possível avaliar um leilão sem lances!", exception.getMessage());
	}
	
	@Test
	public void deveEntenderLeilaoComApenasUmLance() {
		
		Leilao leilao = new CriadorDeLeilao().para("Smartphone")
				.lance(rafael, 600.0)
				.constroi();
		
		leiloeiro.avalia(leilao);
		
		assertEquals(600, leiloeiro.getMaiorLance(), 0.00001);
		assertEquals(600, leiloeiro.getMenorLance(), 0.00001);
		
	}
	
	@Test
	public void deveEncontrarOsTresMaioresLances() {
		
		Leilao leilao = new CriadorDeLeilao().para("Geladeira")
				.lance(rafael, 300.0)
				.lance(carlos, 800.0)
				.lance(rafael, 250.0)
				.lance(carlos, 850.0)
				.lance(rafael, 999.0)
				.constroi();
		
		leiloeiro.avalia(leilao);
		
		// utilizando hamcrest para comparar coleções
		assertThat(leiloeiro.getTresMaiores().size(), equalTo(3));
		assertThat(leiloeiro.getTresMaiores(), hasItems(
				new Lance(rafael, 999.0),
				new Lance(carlos, 850.0),
				new Lance(carlos, 800.0)
		));
	}
	
	@Test
	public void deveEncontrarOsTresMaioresLancesCom2Elementos() {
		
		Leilao leilao = new CriadorDeLeilao().para("Geladeira")
				.lance(rafael, 300.0)
				.lance(carlos, 800.0)
				.constroi();

		
		leiloeiro.avalia(leilao);
		
		assertEquals(2, leiloeiro.getTresMaiores().size());
		assertEquals(800.0, leiloeiro.getTresMaiores().get(0).getValor(), 0.00001);
		assertEquals(300.0, leiloeiro.getTresMaiores().get(1).getValor(), 0.00001);
	}
	
	@Test
	public void deveEncontrarOsTresMaioresLancesComListaVazia() {
		
		Leilao leilao = new CriadorDeLeilao().para("Geladeira")
				.constroi();

		Exception exception = assertThrows(RuntimeException.class, () -> leiloeiro.avalia(leilao));

		assertEquals("Não é possível avaliar um leilão sem lances!", exception.getMessage());
	}
	
}
