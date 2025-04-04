package com.echevarne.sap.cloud.facturacion.repositories;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.echevarne.sap.cloud.facturacion.repositories.dto.ResultSetData;

@Repository
public class SolicitudesPrivadaResultSetHeaderRepImpl implements SolicitudesPrivadaResultSetHeaderRep {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Save a new record in T_SOLPRIVRESULTSETHEADER using ResultSetHeader DTO    
    @Override
    public void saveSolPrivResultSetHeader(ResultSetData header) {
        // Set the current system time for both created and lastUpdated fields
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        header.setCreated(currentTime);
        header.setLastUpdated(currentTime);

        String sql = "INSERT INTO T_SOLPRIVRESULTSETHEADER (UUID, URI, EMAIL, NUM_ENTRIES, ENTITY_CREATION, LAST_UPDATE) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, header.getUuid(), header.getUri(), header.getEmail(), header.getTotalCount(), header.getCreated(), header.getLastUpdated());
    }    

    // Retrieve a record as ResultSetHeader DTO
    @Override
    public ResultSetData retrieveSolPrivResultSetHeaderByUUID(String uuid) {
        String sql = "SELECT UUID, URI, EMAIL, NUM_ENTRIES, ENTITY_CREATION, LAST_UPDATE FROM T_SOLPRIVRESULTSETHEADER WHERE UUID = ?";
        try {
			return jdbcTemplate.queryForObject(sql, new Object[]{uuid}, (rs, rowNum) -> {
			    ResultSetData header = new ResultSetData();
			    header.setUuid(rs.getString("UUID"));
			    header.setUri(rs.getString("URI"));
			    header.setEmail(rs.getString("EMAIL"));
			    header.setTotalCount(rs.getInt("NUM_ENTRIES"));
			    header.setCreated(rs.getTimestamp("ENTITY_CREATION"));
			    header.setLastUpdated(rs.getTimestamp("LAST_UPDATE"));
			    return header;
			});
		} catch (EmptyResultDataAccessException e) {
            return null;
		}
    }
    
    // Retrieve a record by URI as ResultSetHeader DTO
    @Override
    public ResultSetData retrieveSolPrivResultSetHeaderByURI(String uri) {
        String sql = "SELECT UUID, URI, EMAIL, NUM_ENTRIES, ENTITY_CREATION, LAST_UPDATE FROM T_SOLPRIVRESULTSETHEADER WHERE URI = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{uri}, (rs, rowNum) -> {
                ResultSetData header = new ResultSetData();
			    header.setUuid(rs.getString("UUID"));
			    header.setUri(rs.getString("URI"));
			    header.setEmail(rs.getString("EMAIL"));
			    header.setTotalCount(rs.getInt("NUM_ENTRIES"));
			    header.setCreated(rs.getTimestamp("ENTITY_CREATION"));
			    header.setLastUpdated(rs.getTimestamp("LAST_UPDATE"));
                return header;
            });
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
    
    @Override
    public void deleteSolPrivResultSetHeader(String uuid) {
        String sql = "DELETE FROM T_SOLPRIVRESULTSETHEADER WHERE URI = ?";
        jdbcTemplate.update(sql, uuid);
    }    
    
}
