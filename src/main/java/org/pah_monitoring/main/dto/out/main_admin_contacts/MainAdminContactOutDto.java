package org.pah_monitoring.main.dto.out.main_admin_contacts;

import lombok.Builder;
import lombok.Data;
import org.pah_monitoring.main.dto.out.common.interfaces.OutDto;

@Data
@Builder
public class MainAdminContactOutDto implements OutDto {

    private Integer id;

    private String contact;

    private String description;

}
