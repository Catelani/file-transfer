package br.com.catelani.santander.filetransfer.util;

import org.junit.Test;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

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

  @Test
  public void testFormat() throws Exception {
    assertThat(BigDecimalUtils.formatBigDecimal(null), is("0"));
    assertThat(BigDecimalUtils.formatBigDecimal(BigDecimal.ZERO), is("0"));

    assertThat(BigDecimalUtils.formatBigDecimal(BigDecimal.valueOf(1000)), is("100000"));
    assertThat(BigDecimalUtils.formatBigDecimal(BigDecimal.valueOf(1000.50)), is("100050"));
  }
}