package cz.tmobile.conf.persistence.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

@MappedTypes(value = LocalDateTime.class)
@MappedJdbcTypes(value = { JdbcType.TIMESTAMP})
public class LocalDateTimeTypeHandler implements TypeHandler<LocalDateTime> {

	@Override
	public void setParameter(PreparedStatement ps, int i, LocalDateTime parameter, JdbcType jdbcType) throws SQLException {
		if (parameter == null) {
			ps.setTimestamp(i, null);
		} else {
			ps.setTimestamp(i, Timestamp.valueOf(parameter));
		}
	}

	@Override
	public LocalDateTime getResult(ResultSet rs, String columnName) throws SQLException {
		Timestamp ts = rs.getTimestamp(columnName);
		return ts == null ? null : ts.toLocalDateTime();
	}

	@Override
	public LocalDateTime getResult(ResultSet rs, int columnIndex) throws SQLException {
		Timestamp ts = rs.getTimestamp(columnIndex);
		return ts == null ? null : ts.toLocalDateTime();
	}

	@Override
	public LocalDateTime getResult(CallableStatement cs, int columnIndex) throws SQLException {
		Timestamp ts = cs.getTimestamp(columnIndex);
		return ts == null ? null : ts.toLocalDateTime();
	}
}