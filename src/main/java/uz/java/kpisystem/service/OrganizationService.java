package uz.java.kpisystem.service;

import org.springframework.stereotype.Service;
import uz.java.kpisystem.dto.organization.OrganizationFilter;
import uz.java.kpisystem.dto.organization.OrganizationInfo;
import uz.java.kpisystem.dto.organization.OrganizationRequest;
import uz.java.kpisystem.entity.Organization;
import uz.java.kpisystem.exception.CustomNotFoundException;
import uz.java.kpisystem.mapper.OrganizationMapper;
import uz.java.kpisystem.repository.OrganizationRepository;

import java.util.List;
import java.util.Optional;

@Service  // bean qilib beradi
public class OrganizationService implements IOrganizationService {

    private final OrganizationRepository repository;
    private final OrganizationMapper mapper;

    public OrganizationService(OrganizationRepository repository, OrganizationMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<OrganizationInfo> getAll(OrganizationFilter organizationFilter) {
        List<Organization> all = repository.findAll();  // Alt+Enter bosilsa ozgaruvchiga olinadi
        // start
//        List<OrganizationInfo> response = new ArrayList<>();
//        for (Organization organization : all) {
//            OrganizationInfo info = new OrganizationInfo();
//            info.setName(organization.getName());
//            info.setId(organization.getId());
//            info.setAddress(organization.getAddress());
//            info.setEmail(organization.getEmail());
//            response.add(info);
//        }

        return all.stream().map(mapper::toResponse).toList();
    }

    @Override
    public Long create(OrganizationRequest request) {
        Organization organization = mapper.toEntity(request);
        Organization save = repository.save(organization);
//        insert into organizations(id, name, addres, ....) values(1, "sdfsd", "dfsdf");
        return save.getId();
    }

    @Override
    public OrganizationInfo update(Long id, OrganizationRequest request) {
        Optional<Organization> opt = repository.findById(id);
        if (!opt.isPresent())
            throw new CustomNotFoundException("Organization not found");
        Organization organization = opt.get();
        mapper.updateFromRequest(request, organization);
        repository.save(organization); // update qiladi
        // update from organizations set name='dsad', address='adczd' where id=1, 2, ...
        return getOne(id);
    }

    @Override
    public OrganizationInfo getOne(Long id) {
        Optional<Organization> opt = repository.findById(id);
        if (!opt.isPresent())
            throw new CustomNotFoundException("Organization not found");

        Organization organization = opt.get();
        return mapper.toResponse(organization);
    }

    @Override
    public Boolean delete(Long id) {
//        Organization organization = repository.findById(id).orElse(null);
        Organization organization = repository.findById(id).orElseThrow(() -> new CustomNotFoundException("Organization not found"));
//        repository.delete(organization); // hard delete
        organization.makeAsDeleted();
        repository.save(organization); // soft delete
        return true;
    }

//    EPAM interview:
//    1) Optional class of(), ofNullable() methodlari
//    2) stream Api
//    3) Functional interface ozi nima va ichida qanaqa method lar yaratsa boladi
}
