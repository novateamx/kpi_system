package uz.java.kpisystem.mapper;

import org.mapstruct.*;
import uz.java.kpisystem.dto.organization.OrganizationInfo;
import uz.java.kpisystem.dto.organization.OrganizationRequest;
import uz.java.kpisystem.entity.Organization;

@Mapper(componentModel = "spring") // Mapper ni ozi bean qip beradi
public interface OrganizationMapper {

    @Mapping(source = "phone", target = "phoneNumber")
    OrganizationInfo toResponse(Organization organization);

    Organization toEntity(OrganizationRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromRequest(OrganizationRequest request, @MappingTarget Organization organization);
}
