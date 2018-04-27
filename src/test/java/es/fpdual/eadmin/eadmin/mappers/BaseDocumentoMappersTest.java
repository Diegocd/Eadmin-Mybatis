package es.fpdual.eadmin.eadmin.mappers;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.catalina.Session;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import es.fpdual.eadmin.eadmin.modelo.Documento;
import es.fpdual.eadmin.eadmin.modelo.EstadoDocumento;
import es.fpdual.eadmin.eadmin.util.Utilidades;

@Transactional("eadminTransactionManager")
public abstract class BaseDocumentoMappersTest {

	private Documento documento;

	@Autowired
	private DocumentoMapper mapper;

	@Before
	public void inicializarVariables() {
		documento = new Documento(1, "Documento1", Utilidades.asDate(LocalDate.of(2015, 1, 1)),
				Utilidades.asDate(LocalDate.of(2015, 1, 2)), true, EstadoDocumento.ACTIVO);
	}

	@Test
	public void deberiaInsertarUnDocumento() {

		final int resultado = this.mapper.insertarDocumento(documento);

		assertThat(resultado, is(1));
	}

	@Test
	public void deberiaBorrarUnDocumento() {

		this.mapper.insertarDocumento(documento);
		final int resultadoBorrar = this.mapper.borrarDocumento(documento);

		assertThat(resultadoBorrar, is(1));
	}

	@Test
	public void deberiaDevolverUnDocumento() {
		this.mapper.insertarDocumento(documento);
		Documento resultado = this.mapper.buscarDocumento(1);

		assertThat(resultado, is(documento));
	}
	
	@Test
	public void deberiaCambiarUnDocumento() {
		this.mapper.insertarDocumento(documento);
		Documento documentoCambiado = new Documento(1, "Documento1Cambiado", Utilidades.asDate(LocalDate.of(2015, 2, 1)),
				Utilidades.asDate(LocalDate.of(2015, 2, 2)), false, EstadoDocumento.APROBADO);
		final int resultado = this.mapper.actualizarDocumento(documentoCambiado);

		assertThat(resultado, is(1));
		
		final Documento documentoModificado = this.mapper.buscarDocumento(1);
		assertThat(documentoCambiado, is(documentoModificado));
	}
	
	@Test
	public void deberiaDevolverTodosLosDocumentos() {
		Documento documento2 = new Documento(2, "Documento1Cambiado", Utilidades.asDate(LocalDate.of(2015, 2, 1)),
				Utilidades.asDate(LocalDate.of(2015, 2, 2)), false, EstadoDocumento.APROBADO);
		this.mapper.insertarDocumento(this.documento);
		this.mapper.insertarDocumento(documento2);
		List<Documento> resultado = this.mapper.obtenerTodosLosDocumentos();
		
		
		assertThat(resultado, hasItems(documento, documento2));
		assertThat(resultado, hasSize(2));
	}
	
	@Test
	public void deberiaDevolverElCodigoMaximoMasUno() {
		Documento documento2 = new Documento(2, "Documento1Cambiado", Utilidades.asDate(LocalDate.of(2015, 2, 1)),
				Utilidades.asDate(LocalDate.of(2015, 2, 2)), false, EstadoDocumento.APROBADO);
		this.mapper.insertarDocumento(this.documento);
		this.mapper.insertarDocumento(documento2);
		int resultado = this.mapper.obtenerElMayorCodigoMasUno();
		
		assertThat(resultado, is(3));
	}
	
	@Test
	public void deberiaDevolverUnoSiElCodigoMaximoMasUnoEsNull() {
		
		int resultado = this.mapper.obtenerElMayorCodigoMasUno();
		
		assertThat(resultado, is(1));
	}


}
