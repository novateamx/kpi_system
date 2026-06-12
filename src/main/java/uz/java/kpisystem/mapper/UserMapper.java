package uz.java.kpisystem.mapper;

import org.mapstruct.*;
import uz.java.kpisystem.dto.UserRequest;
import uz.java.kpisystem.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "password", ignore = true)
    @Mapping(target = "organization", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "keycloakId", ignore = true)
    @Mapping(target = "status", ignore = true)
    User toEntity(UserRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "organization", ignore = true)
    void updateFromRequest(UserRequest request, @MappingTarget User user);
}
