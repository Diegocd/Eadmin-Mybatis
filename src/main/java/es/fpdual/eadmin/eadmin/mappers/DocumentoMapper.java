package es.fpdual.eadmin.eadmin.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import es.fpdual.eadmin.eadmin.modelo.Documento;

public interface DocumentoMapper {
	public abstract int insertarDocumento(@Param("Documento") Documento d);
	public abstract int borrarDocumento(@Param("Documento") Documento d);
	public abstract int actualizarDocumento(@Param("Documento") Documento d);
	public abstract Documento buscarDocumento(@Param("Codigo") int i);
	public abstract List<Documento> obtenerTodosLosDocumentos();
	public int obtenerElMayorCodigoMasUno();
}
