package org.pah_monitoring.main.services.common.interfaces;

public interface DeletionValidationService<T> {

    boolean isNotValidForDeletion(T t);

}
