package uz.java.kpisystem.service;

import uz.java.kpisystem.dto.group.GroupFilter;
import uz.java.kpisystem.dto.group.GroupResponse;
import uz.java.kpisystem.entity.Group;

import java.util.List;

public interface IGroupService {

    List<GroupResponse> getAll(GroupFilter groupFilter);

    Long create(String name);

    Long update(Long id, String name);

    GroupResponse getOne(Long id);

    Boolean delete(Long id);
}
