package es.fpdual.eadmin.eadmin.repositorio.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import es.fpdual.eadmin.eadmin.mappers.DocumentoMapper;
import es.fpdual.eadmin.eadmin.modelo.Documento;
import es.fpdual.eadmin.eadmin.modelo.builder.DocumentoBuilder;
import es.fpdual.eadmin.eadmin.repositorio.RepositorioDocumento;

@Repository
public class RepositorioDocumentoImpl implements RepositorioDocumento {

	private DocumentoMapper mapper;

	@Autowired
	public RepositorioDocumentoImpl(DocumentoMapper mapper) {
		this.mapper = mapper;
	}

	@Override
	public void altaDocumento(Documento documento) {
		int codigo = this.mapper.obtenerElMayorCodigoMasUno();
		Documento modificado = new DocumentoBuilder().clonar(documento).conCodigo(codigo).construir();
		this.mapper.insertarDocumento(modificado);
	}

	@Override
	public void modificarDocumento(Documento documento) {
		final int modificado = this.mapper.actualizarDocumento(documento);
		if(modificado==0) {
			throw new IllegalArgumentException("No se encuentra el documento");
		}
	}

	@Override
	public void eliminarDocumento(Integer codigo) {
		final Documento documento = this.mapper.buscarDocumento(codigo);
		this.mapper.borrarDocumento(documento);
	}

	@Override
	public Documento obtenerDocumentoPorCodigo(Integer codigo) {
		return this.mapper.buscarDocumento(codigo);
	}

	@Override
	public List<Documento> obtenerTodosLosDocumentos() {
		return this.mapper.obtenerTodosLosDocumentos();
	}

}
