package org.pah_monitoring.main.mappers.main_admin_contacts;

import org.pah_monitoring.main.aop.annotations.NullWhenNull;
import org.pah_monitoring.main.dto.out.main_admin_contacts.MainAdminContactOutDto;
import org.pah_monitoring.main.entities.main.main_admin_contacts.MainAdminContact;
import org.pah_monitoring.main.mappers.common.interfaces.BaseEntityToOutDtoMapper;
import org.springframework.stereotype.Component;

@Component("mainAdminContactMapper")
public class MainAdminContactToOutDtoMapper implements BaseEntityToOutDtoMapper<MainAdminContact, MainAdminContactOutDto> {

    @Override
    @NullWhenNull
    public MainAdminContactOutDto map(MainAdminContact mainAdminContact) {
        return MainAdminContactOutDto
                .builder()
                .id(mainAdminContact.getId())
                .contact(mainAdminContact.getContact())
                .description(mainAdminContact.getDescription())
                .build();
    }

}
