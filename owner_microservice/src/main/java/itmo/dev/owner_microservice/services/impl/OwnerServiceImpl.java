package itmo.dev.owner_microservice.services.impl;

import itmo.dev.common.models.Cat;
import itmo.dev.common.models.Owner;
import itmo.dev.owner_microservice.repositories.OwnerRepository;
import itmo.dev.owner_microservice.dto.OwnerDto;
import itmo.dev.owner_microservice.exceptions.OwnerNotFoundException;
import itmo.dev.owner_microservice.mapper.CatMapper;
import itmo.dev.owner_microservice.mapper.OwnerMapper;
import itmo.dev.owner_microservice.services.interfaces.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OwnerServiceImpl implements OwnerService {

    private final OwnerRepository ownerRepository;
    private final CatMapper catMapper;
    private final OwnerMapper ownerMapper;

    @Autowired
    public OwnerServiceImpl(OwnerRepository ownerRepository, CatMapper catMapper, OwnerMapper ownerMapper) {

        this.ownerRepository = ownerRepository;
        this.catMapper = catMapper;
        this.ownerMapper = ownerMapper;
    }

    @Override
    public OwnerDto createOwner(OwnerDto ownerDto) {

        Owner owner = ownerMapper.ownerToModel(ownerDto);

        return ownerMapper.ownerToDto(ownerRepository.save(owner));
    }

    @Override
    public List<OwnerDto> getAllOwners() {

        List<Owner> ownerList = ownerRepository.findAll();

        return ownerList.stream()
                .map(ownerMapper::ownerToDto)
                .toList();
    }

    @Override
    public OwnerDto getOwnerById(Integer id) throws OwnerNotFoundException {

        Owner owner = ownerRepository.findById(id)
                .orElseThrow(() -> new OwnerNotFoundException("Owner could not be found"));

        return ownerMapper.ownerToDto(owner);
    }

    @Override
    public OwnerDto updateOwner(OwnerDto ownerDto, Integer id) throws OwnerNotFoundException {

        Owner owner = ownerRepository.findById(id)
                .orElseThrow(() -> new OwnerNotFoundException("Owner could not be found"));
        owner.setName(ownerDto.getName());
        owner.setBirthDate(ownerDto.getBirthDate());

        List<Cat> tmpList = ownerDto.getCatsList() == null ? null : ownerDto.getCatsList().stream()
                .map(catMapper::catToModel)
                .toList();
        owner.setCatsList(tmpList);

        return ownerMapper.ownerToDto(ownerRepository.save(owner));
    }

    @Override
    public void deleteOwner(Integer id) throws OwnerNotFoundException {

        Owner owner = ownerRepository.findById(id)
                .orElseThrow(() -> new OwnerNotFoundException("Owner could not be found"));

        ownerRepository.delete(owner);
    }
}
