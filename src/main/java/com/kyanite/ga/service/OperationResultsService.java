package com.kyanite.ga.service;

import com.kyanite.ga.domain.OperationResults;
import com.kyanite.ga.repository.OperationResultsRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link OperationResults}.
 */
@Service
@Transactional
public class OperationResultsService {

    private final Logger log = LoggerFactory.getLogger(OperationResultsService.class);

    private final OperationResultsRepository operationResultsRepository;

    public OperationResultsService(OperationResultsRepository operationResultsRepository) {
        this.operationResultsRepository = operationResultsRepository;
    }

    /**
     * Save a operationResults.
     *
     * @param operationResults the entity to save.
     * @return the persisted entity.
     */
    public OperationResults save(OperationResults operationResults) {
        log.debug("Request to save OperationResults : {}", operationResults);
        return operationResultsRepository.save(operationResults);
    }

    /**
     * Partially update a operationResults.
     *
     * @param operationResults the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<OperationResults> partialUpdate(OperationResults operationResults) {
        log.debug("Request to partially update OperationResults : {}", operationResults);

        return operationResultsRepository
            .findById(operationResults.getId())
            .map(
                existingOperationResults -> {
                    if (operationResults.getOperationType() != null) {
                        existingOperationResults.setOperationType(operationResults.getOperationType());
                    }
                    if (operationResults.getTime() != null) {
                        existingOperationResults.setTime(operationResults.getTime());
                    }
                    if (operationResults.getText() != null) {
                        existingOperationResults.setText(operationResults.getText());
                    }
                    if (operationResults.getOperationSource() != null) {
                        existingOperationResults.setOperationSource(operationResults.getOperationSource());
                    }

                    return existingOperationResults;
                }
            )
            .map(operationResultsRepository::save);
    }

    /**
     * Get all the operationResults.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<OperationResults> findAll() {
        log.debug("Request to get all OperationResults");
        return operationResultsRepository.findAll();
    }

    /**
     * Get one operationResults by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<OperationResults> findOne(Long id) {
        log.debug("Request to get OperationResults : {}", id);
        return operationResultsRepository.findById(id);
    }

    /**
     * Delete the operationResults by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete OperationResults : {}", id);
        operationResultsRepository.deleteById(id);
    }
}
