package es.fpdual.eadmin.eadmin.repositorio.impl;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import es.fpdual.eadmin.eadmin.mappers.DocumentoMapper;
import es.fpdual.eadmin.eadmin.modelo.Documento;
import es.fpdual.eadmin.eadmin.modelo.EstadoDocumento;
import es.fpdual.eadmin.eadmin.modelo.builder.DocumentoBuilder;
import es.fpdual.eadmin.eadmin.repositorio.RepositorioDocumento;
import es.fpdual.eadmin.eadmin.repositorio.impl.RepositorioDocumentoImpl;

import java.util.Arrays;
import org.junit.Assert.*;

public class RepositorioDocumentoImplTest {

	private static final Date FECHA_CREACION = new Date();
	private static final Date FECHA_ULTIMA_MODIFICACION = new Date();
	private static final String NOMBRE_DOCUMENTO = "nombre";
	private static final boolean DOCUMENTO_PUBLICO = true;
	private static final Integer CODIGO_DOCUMENTO = 1;
	private DocumentoMapper mapper;

	private RepositorioDocumentoImpl repositorioDocumento;
	
	private final Documento documento =
			
			new Documento(CODIGO_DOCUMENTO, NOMBRE_DOCUMENTO, FECHA_CREACION,FECHA_ULTIMA_MODIFICACION, DOCUMENTO_PUBLICO, EstadoDocumento.ACTIVO);
	
	@Before
	public void inicializarEnCadaTest() {
		mapper = mock(DocumentoMapper.class);
		
		this.repositorioDocumento = new RepositorioDocumentoImpl(mapper);
	}
	
	@Test
	public void deberiaAlmacenarUnDocumento() {
		when(this.mapper.obtenerElMayorCodigoMasUno()).thenReturn(2);
		this.repositorioDocumento.altaDocumento(documento);
		Documento resultante = new DocumentoBuilder().clonar(documento).conCodigo(2).construir();
		verify(mapper).obtenerElMayorCodigoMasUno();
		verify(mapper).insertarDocumento(resultante);
	}

	@Test
	public void deberiaModificarUnDocumento() {
		when(this.mapper.actualizarDocumento(documento)).thenReturn(1);
		this.repositorioDocumento.modificarDocumento(documento);
		
		verify(this.mapper).actualizarDocumento(documento);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void deberiaLanzarExcepcionSiIntentamosModificarUnDocumentoQueNoExiste() {
		
		this.repositorioDocumento.modificarDocumento(documento);
	}
	
	@Test 
	public void deberiaEliminarUnDocumento() {
		when(this.mapper.buscarDocumento(1)).thenReturn(documento);
		this.repositorioDocumento.eliminarDocumento(documento.getCodigo());
		
		verify(this.mapper).borrarDocumento(documento);
	}

	@Test
	public void deberiaObtenerDocumentoPorCodigo() {
		when(this.mapper.buscarDocumento(CODIGO_DOCUMENTO)).thenReturn(documento);
		Documento resultado = this.repositorioDocumento.obtenerDocumentoPorCodigo(CODIGO_DOCUMENTO);
		
		verify(this.mapper).buscarDocumento(CODIGO_DOCUMENTO);
		
		assertThat(resultado, is(documento));
	}
	
	@Test
	public void deberiaDevolverUnaListaDeDocumentos() {
		final List<Documento> lista = Arrays.asList(documento);
		when(this.mapper.obtenerTodosLosDocumentos()).thenReturn(lista);
		List<Documento> resultado = this.repositorioDocumento.obtenerTodosLosDocumentos();
		
		assertThat(resultado, hasItem(documento));
		assertThat(resultado, hasSize(1));
	}
	
}
