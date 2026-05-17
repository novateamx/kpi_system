package uz.java.kpisystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.java.kpisystem.dto.group.GroupFilter;
import uz.java.kpisystem.entity.Group;
import uz.java.kpisystem.repository.GroupRepository;
import java.util.List;
import java.util.Optional;

@Service

public class GroupService  implements IGroupService{
    private final GroupRepository repository;

    public GroupService(GroupRepository repository) {
        this.repository = repository;
    }

    public List<Group> getAll(GroupFilter groupFilter) {
          return repository.findAll();
    }

    @Override
    public Group create(Group body) {
        return repository.save(body);
    }

    @Override
    public Group update(Long id,Group body) {
        Optional<Group> opt = repository.findById(id);
        if (!opt.isPresent()){
            throw  new RuntimeException("Group not found!!!");
        }
        return repository.save(body);
     }

    @Override
    public Group getOne(Long id) {
         Optional<Group> group = repository.findById(id);
         if(!group.isPresent()) {
             System.out.println("Group not found!!!");
             throw  new RuntimeException("Group not found!!!");
         }
         return group.get();
    }

    @Override
    public Group delete(Long id) {
        Group group = this.getOne(id);
        group.makeAsDeleted();
        repository.save(group);
        return group;
    }
}
