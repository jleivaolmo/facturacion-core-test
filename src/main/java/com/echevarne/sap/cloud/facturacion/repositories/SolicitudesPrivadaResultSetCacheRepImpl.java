package com.echevarne.sap.cloud.facturacion.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class SolicitudesPrivadaResultSetCacheRepImpl implements SolicitudesPrivadaResultSetCacheRep {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public void saveSolPrivResultSet(String key, List<Long> solPrivIds) {
		String sql = "INSERT INTO T_SOLPRIVRESULTSETCACHE (FK_SOLPRIVRESULTSETHEADER, SOLPRIV_ID) VALUES (?, ?)";
		// Batch insert using JdbcTemplate
		jdbcTemplate.batchUpdate(sql, solPrivIds, solPrivIds.size(), (ps, argument) -> {
			ps.setString(1, key);
			ps.setLong(2, argument);
		});
	}
	
    // Method to retrieve a list of SOLPRIV_IDs by UUID
    // Using queryForList with elementType
    @Override
	public List<Long> retrieveSolPrivResultSet(String key) {
        String sql = "SELECT SOLPRIV_ID FROM T_SOLPRIVRESULTSETCACHE WHERE FK_SOLPRIVRESULTSETHEADER = ?";
        return jdbcTemplate.queryForList(sql, new Object[]{key}, new int[]{java.sql.Types.BIGINT}, Long.class);
    }
}
