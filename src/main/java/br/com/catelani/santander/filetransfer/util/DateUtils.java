package br.com.catelani.santander.filetransfer.util;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Objects;

/**
 * Métodos utilitarios para manipulação de datas de acordo com as regras do Banco.
 *
 * @author Kennedy Oliveira
 * @since 1.0.0
 */
public class DateUtils {

  /**
   * Formato de data padrão do Banco
   */
  private final static DateTimeFormatter DATA_FORMATTER_PADRAO = new DateTimeFormatterBuilder().appendValue(ChronoField.YEAR)
                                                                                               .appendValue(ChronoField.MONTH_OF_YEAR, 2)
                                                                                               .appendValue(ChronoField.DAY_OF_MONTH, 2)
                                                                                               .toFormatter();
  /**
   * Formato de hora padrão do Banco
   */
  private final static DateTimeFormatter HORA_FORMATTER_PADRAO = new DateTimeFormatterBuilder().appendValue(ChronoField.HOUR_OF_DAY, 2)
                                                                                               .appendValue(ChronoField.MINUTE_OF_HOUR, 2)
                                                                                               .appendValue(ChronoField.SECOND_OF_MINUTE, 2)
                                                                                               .toFormatter();

  private DateUtils() {}

  /**
   * Transforma a data no formato do banco {@code YYYYMMDD} em um {@link LocalDate}.
   *
   * @param date Data no formato do banco
   * @return Um {@link LocalDate}
   */
  @Contract(pure = true)
  public static LocalDate parseDate(@NotNull String date) {
    Objects.requireNonNull(date, "A data não pode ser vazia!");

    return LocalDate.parse(date, DATA_FORMATTER_PADRAO);
  }

  /**
   * Transforma um horario no formato do bacno {@code HHMMSS} em um {@link LocalTime}.
   *
   * @param time Horario a ser transformado
   * @return Um {@link LocalTime}
   */
  @Contract(pure = true)
  public static LocalTime parseTime(@NotNull String time) {
    Objects.requireNonNull(time, "O horario não pode ser vazio");

    return LocalTime.parse(time, HORA_FORMATTER_PADRAO);
  }

  /**
   * Formata um {@link LocalDate} no formato do banco {@code YYYYMMDD}.
   *
   * @param date Data a ser formatada.
   * @return Uma string formatada no formato do banco.
   */
  @Contract(pure = true)
  public static String format(@NotNull LocalDate date) {
    Objects.requireNonNull(date, "A data não pode ser nula para ser formatada!");

    return DATA_FORMATTER_PADRAO.format(date);
  }

  /**
   * Formata um {@link LocalTime} no formato do banco {@code HHMMSS}.
   *
   * @param time Horario a ser formatado.
   * @return Uma string formatada no formato do banco.
   */
  @Contract(pure = true)
  public static String format(@NotNull LocalTime time) {
    Objects.requireNonNull(time, "O Horario não pode ser nulo para ser formatado!");

    return HORA_FORMATTER_PADRAO.format(time);
  }
}
