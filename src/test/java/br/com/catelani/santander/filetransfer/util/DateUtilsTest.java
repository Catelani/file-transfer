package br.com.catelani.santander.filetransfer.util;

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

/**
 * @author Kennedy Oliveira
 */
public class DateUtilsTest {

  @Test
  public void parseDate() throws Exception {
    assertThat(DateUtils.parseDate("20160525"), is(LocalDate.of(2016, Month.MAY, 25)));

    try {
      DateUtils.parseDate("1234");
      fail("Deveria lançar exception.");
    } catch (Exception e) {
    }
  }

  @Test
  public void parseTime() throws Exception {
    assertThat(DateUtils.parseTime("081523"), is(LocalTime.of(8, 15, 23)));

    try {
      DateUtils.parseTime("1231231232");
      fail("Deveria lançar exception.");
    } catch (Exception e) {
    }
  }

  @Test
  public void formatDate() throws Exception {
    assertThat(DateUtils.format(LocalDate.of(2016, Month.MAY, 25)), is("20160525"));
  }

  @Test
  public void formatTime() throws Exception {
    assertThat(DateUtils.format(LocalTime.of(8, 25, 5)), is("082505"));
  }
}