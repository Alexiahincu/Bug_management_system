package org.example.Domain;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class BugStatusConverter implements AttributeConverter<BugStatus, String> {

    @Override
    public String convertToDatabaseColumn(BugStatus status) {
        if (status == null) {
            return null;
        }
        return status.name();
    }

    @Override
    public BugStatus convertToEntityAttribute(String status) {
        if (status == null) {
            return null;
        }
        return BugStatus.valueOf(status);
    }
}
