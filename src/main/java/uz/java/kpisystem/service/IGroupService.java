package uz.java.kpisystem.service;

import uz.java.kpisystem.dto.group.GroupFilter;
import uz.java.kpisystem.entity.Group;

import java.util.List;

public interface IGroupService {

    List<Group> getAll(GroupFilter groupFilter);

    Group create(Group body);

    Group update(Long id,Group body);

    Group getOne(Long id);

    Group delete(Long id);
}
