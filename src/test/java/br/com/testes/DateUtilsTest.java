package br.com.testes;

import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Test;

import br.ce.wcaquino.taskbackend.utils.DateUtils;

public class DateUtilsTest {

	@Test
	public void deveRetornarTrueParaDatasFuturas() {
		LocalDate dt = LocalDate.of(2030, 1, 1);
		Assert.assertTrue(DateUtils.isEqualOrFutureDate(dt));
	}
	
	@Test
	public void deveRetornarFalseParaDatasFuturas() {
		LocalDate dt = LocalDate.of(2010, 1, 1);
		Assert.assertFalse(DateUtils.isEqualOrFutureDate(dt));
	}
	
	@Test
	public void deveRetornarTrueParaDatasAtual() {
		LocalDate dt = LocalDate.now();
		Assert.assertTrue(DateUtils.isEqualOrFutureDate(dt));
	}
}
