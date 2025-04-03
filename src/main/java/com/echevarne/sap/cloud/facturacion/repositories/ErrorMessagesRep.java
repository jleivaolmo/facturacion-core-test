package com.echevarne.sap.cloud.facturacion.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.echevarne.sap.cloud.facturacion.model.ErrorMessages;
import com.echevarne.sap.cloud.facturacion.model.Messages;

/**
 * Repository for the Model: {@link ErrorMessages}
 * @author Hernan Girardi
 * @since 26/03/2020
 */
@Repository("errorMessagesRep")
public interface ErrorMessagesRep extends JpaRepository<ErrorMessages, Long> {
		
	public Optional<ErrorMessages> findByErrorCode(String errorCode);
	public Optional<ErrorMessages> findByMessage(Messages msg);
}
