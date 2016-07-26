package br.com.catelani.santander.filetransfer.util;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author Kennedy Oliveira
 */
public class BooleanUtilsTest {

  @Test
  public void parseBoolean() throws Exception {
    assertThat(BooleanUtils.parseBoolean("s"), is(true));
    assertThat(BooleanUtils.parseBoolean("S"), is(true));
    assertThat(BooleanUtils.parseBoolean("n"), is(false));
    assertThat(BooleanUtils.parseBoolean("N"), is(false));
    assertThat(BooleanUtils.parseBoolean("a"), is(false));
    assertThat(BooleanUtils.parseBoolean("b"), is(false));
    assertThat(BooleanUtils.parseBoolean("basdqwe"), is(false));
    assertThat(BooleanUtils.parseBoolean(null), is(false));
    assertThat(BooleanUtils.parseBoolean(" "), is(false));
  }

  @Test
  public void testFormatBoolean() throws Exception {
    assertThat(BooleanUtils.formatBoolean(true), is("S"));
    assertThat(BooleanUtils.formatBoolean(false), is("N"));
  }
}