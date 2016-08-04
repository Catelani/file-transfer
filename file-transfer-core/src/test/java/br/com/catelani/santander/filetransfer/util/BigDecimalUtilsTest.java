package br.com.catelani.santander.filetransfer.util;

import org.junit.Test;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

/**
 * @author Kennedy Oliveira
 */
public class BigDecimalUtilsTest {

  @Test
  public void testParseBigDecimal() throws Exception {
    assertThat(BigDecimalUtils.parseBigDecimal(null), is(BigDecimal.ZERO));
    assertThat(BigDecimalUtils.parseBigDecimal(""), is(BigDecimal.ZERO));
    assertThat(BigDecimalUtils.parseBigDecimal("000000000010050"), is(equalTo(new BigDecimal("100.50"))));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testParseComTamanhoIncorreto() throws Exception {
    BigDecimalUtils.parseBigDecimal("1234");
    fail("Deveria ter lançado exception, pois só pode parsear numeros com 15 digitos.");
  }

  @Test
  public void testFormat() throws Exception {
    assertThat(BigDecimalUtils.formatBigDecimal(null), is("0"));
    assertThat(BigDecimalUtils.formatBigDecimal(BigDecimal.ZERO), is("0"));

    assertThat(BigDecimalUtils.formatBigDecimal(BigDecimal.valueOf(1000)), is("100000"));
    assertThat(BigDecimalUtils.formatBigDecimal(BigDecimal.valueOf(1000.50)), is("100050"));
  }
}