package es.fpdual.eadmin.eadmin.mappers.handlers;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import es.fpdual.eadmin.eadmin.modelo.EstadoDocumento;

@MappedTypes (EstadoDocumento.class)
public class EstadoDocumentoTypeHandler implements TypeHandler<EstadoDocumento>{

	@Override
	public EstadoDocumento getResult(ResultSet rs, String param) throws SQLException {
		return EstadoDocumento.obtenerEstadoDocumento(rs.getInt(param));
	}

	@Override
	public EstadoDocumento getResult(ResultSet rs, int param) throws SQLException {
		return EstadoDocumento.obtenerEstadoDocumento(rs.getInt(param));
	}

	@Override
	public EstadoDocumento getResult(CallableStatement rs, int col) throws SQLException {
		return EstadoDocumento.obtenerEstadoDocumento(rs.getInt(col));
	}

	@Override
	public void setParameter(PreparedStatement ps, int columnIndex, EstadoDocumento estadoDocumento, JdbcType arg3)
			throws SQLException {
		ps.setInt(columnIndex, estadoDocumento.getCodigo());
	}


}
