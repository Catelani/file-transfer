package br.com.catelani.santander.filetransfer.util;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

/**
 * @author Kennedy Oliveira
 */
public class StringUtilsTest {

  @Test
  public void testIsNullOrEmpty() throws Exception {
    assertThat(StringUtils.isNullOrEmpty(""), is(true));
    assertThat(StringUtils.isNullOrEmpty(null), is(true));
    assertThat(StringUtils.isNullOrEmpty("asdqwe"), is(false));
    assertThat(StringUtils.isNullOrEmpty(" "), is(false));
  }

  @Test
  public void testLeftPad() throws Exception {
    assertThat(StringUtils.leftPad("zupa", '0', 10), is("000000zupa"));
    assertThat(StringUtils.leftPad("zupa", 'x', 4), is("zupa"));
    assertThat(StringUtils.leftPad(null, 'c', 50), is(nullValue()));
  }
}