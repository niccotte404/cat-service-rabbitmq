package itmo.dev.cat_microservice.services.impl;

import itmo.dev.common.models.Cat;
import itmo.dev.common.models.Owner;
import itmo.dev.cat_microservice.repositories.CatRepository;
import itmo.dev.repositorylayer.repositories.OwnerRepository;
import itmo.dev.cat_microservice.dto.CatDto;
import itmo.dev.cat_microservice.exceptions.CatNotFoundException;
import itmo.dev.cat_microservice.exceptions.OwnerNotFoundException;
import itmo.dev.cat_microservice.mapper.CatMapper;
import itmo.dev.cat_microservice.services.interfaces.CatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CatServiceImpl implements CatService {

    private final CatRepository catRepository;
    private final OwnerRepository ownerRepository;
    private final CatMapper catMapper;

    @Autowired
    public CatServiceImpl(@Qualifier("catRepository") CatRepository catRepository,
                          @Qualifier("ownerRepository") OwnerRepository ownerRepository,
                          CatMapper catMapper) {

        this.ownerRepository = ownerRepository;
        this.catRepository = catRepository;
        this.catMapper = catMapper;
    }

    @Override
    public CatDto createCat(CatDto catDto, Integer ownerId) throws OwnerNotFoundException {

        Owner owner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new OwnerNotFoundException("Owner could not be found"));
        Cat cat = catMapper.catToModel(catDto);
        cat.setOwner(owner);

        return catMapper.catToDto(catRepository.save(cat));
    }

    @Override
    public List<CatDto> getAllCats() {

        List<Cat> cats = catRepository.findAll();

        return cats.stream()
                .map(cat -> catMapper.catToDto(cat))
                .toList();
    }

    @Override
    public CatDto getCatById(Integer ownerId, Integer catId) throws OwnerNotFoundException, CatNotFoundException {

        Owner tmpOwner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new OwnerNotFoundException("Owner could not be found"));
        Cat tmpCat = catRepository.findById(catId)
                .orElseThrow(() -> new CatNotFoundException("Cat could not be found"));

        if (!tmpCat.getOwner().equals(tmpOwner)) {

            throw new CatNotFoundException("This cat doesnt belong to the owner");
        }

        return catMapper.catToDto(tmpCat);
    }

    @Override
    public List<CatDto> getCatsByOwnerId(Integer ownerId) {

        List<Cat> cats = catRepository.findAllByOwnerId(ownerId);

        return cats.stream()
                .map(cat -> catMapper.catToDto(cat))
                .toList();
    }

    @Override
    public CatDto updateCat(Integer ownerId, Integer catId, CatDto catDto) throws OwnerNotFoundException, CatNotFoundException {

        Owner tmpOwner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new OwnerNotFoundException("Owner could not be found"));
        Cat tmpCat = catRepository.findById(catId)
                .orElseThrow(() -> new CatNotFoundException("Cat could not be found"));

        if (!tmpCat.getOwner().equals(tmpOwner)) {

            throw new CatNotFoundException("This cat doesnt belong to the owner");
        }

        tmpCat.setName(catDto.getName());
        tmpCat.setBirthDate(catDto.getBirthDate());
        tmpCat.setBreed(catDto.getBreed());
        tmpCat.setColor(catDto.getColor());
        tmpCat.setCatFriends(catDto.getCatFriends() == null ? null : catDto.getCatFriends().stream()
                .map(tmp -> catMapper.catToModel(tmp)).toList());

        return catMapper.catToDto(catRepository.save(tmpCat));
    }

    @Override
    public void addCatFriend(Integer catId, Integer friendId) throws CatNotFoundException{

        Cat firstCat = catRepository.findById(catId)
                .orElseThrow(() -> new CatNotFoundException("First cat could not be found"));
        Cat secondCat = catRepository.findById(friendId)
                .orElseThrow(() -> new CatNotFoundException("Second cat could not be found"));

        firstCat.getCatFriends().add(secondCat);
        secondCat.getCatFriends().add(firstCat);

        catRepository.save(firstCat);
        catRepository.save(secondCat);
    }

    @Override
    public void deleteCat(Integer ownerId, Integer catId) throws OwnerNotFoundException, CatNotFoundException {

        Owner tmpOwner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new OwnerNotFoundException("Owner could not be found"));
        Cat tmpCat = catRepository.findById(catId)
                .orElseThrow(() -> new CatNotFoundException("Cat could not be found"));

        if (!tmpCat.getOwner().equals(tmpOwner)) {

            throw new CatNotFoundException("This cat doesnt belong to the owner");
        }

        catRepository.delete(tmpCat);
    }
}