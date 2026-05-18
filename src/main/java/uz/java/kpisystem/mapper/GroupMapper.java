package uz.java.kpisystem.mapper;

import org.mapstruct.Mapper;
import uz.java.kpisystem.dto.group.GroupResponse;
import uz.java.kpisystem.entity.Group;

@Mapper(componentModel = "spring")
public interface GroupMapper {

    GroupResponse toResponse(Group group);
}
