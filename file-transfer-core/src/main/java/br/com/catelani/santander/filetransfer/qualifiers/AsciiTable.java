package br.com.catelani.santander.filetransfer.qualifiers;

import br.com.catelani.santander.filetransfer.parser.PropostaAcordoWriter;
import com.google.inject.BindingAnnotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Qualificador para obter um {@link PropostaAcordoWriter} que gera {@code Ascii Tables}.
 *
 * @author Kennedy Oliveira
 * @since 1.0.0
 */
@BindingAnnotation
@Retention(RUNTIME)
@Target({TYPE, FIELD, PARAMETER, CONSTRUCTOR})
public @interface AsciiTable {}
