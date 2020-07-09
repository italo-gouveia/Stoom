package com.stoom.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.stoom.converter.DozerConverter;
import com.stoom.data.model.Location;
import com.stoom.data.vo.LocationVO;
import com.stoom.exception.ResourceNotFoundException;
import com.stoom.repository.LocationRepository;
import com.stoom.services.ILocationService;
import com.stoom.util.MessagesUtil;

@Service
public class LocationServices implements ILocationService {

    @Autowired
    LocationRepository repository;

    @Override
    public LocationVO create(LocationVO client) {
        Location entity = DozerConverter.parseObject(client, Location.class);
        return DozerConverter.parseObject(repository.save(entity), LocationVO.class);
    }

    @Override
    public Page<LocationVO> findLocationByName(String name, Pageable pageable) {
        Page<Location> page = repository.findLocationByName(name, pageable);
        return page.map(this::convertToLocationVO);
    }

    @Override
    public Page<LocationVO> findAll(Pageable pageable) {
        Page<Location> page = repository.findAll(pageable);
        return page.map(this::convertToLocationVO);
    }

    private LocationVO convertToLocationVO(Location entity) {
        return DozerConverter.parseObject(entity, LocationVO.class);
    }

    @Override
    public LocationVO findById(Long id) {
    	Location entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(MessagesUtil.NO_RECORDS_FOUND));
        return DozerConverter.parseObject(entity, LocationVO.class);
    }

    @Override
    public LocationVO update(LocationVO client) {
        Location entity = repository.findById(client.getKey())
                .orElseThrow(() -> new ResourceNotFoundException(MessagesUtil.NO_RECORDS_FOUND));

        entity.setStreet(client.getStreet());
        entity.setNumber(client.getNumber());
        entity.setComplement(client.getComplement());
        entity.setNeighbourhood(client.getNeighbourhood());
        entity.setCity(client.getCity());
        entity.setState(client.getState());
        entity.setCountry(client.getCountry());
        entity.setZipcode(client.getZipcode());
        entity.setLatitude(client.getLatitude());
        entity.setLongitude(client.getLongitude());

        return DozerConverter.parseObject(repository.save(entity), LocationVO.class);
    }

    @Override
    public void delete(Long id) {
    	Location entity = null;
    	entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(MessagesUtil.NO_RECORDS_FOUND));
        repository.delete(entity);
    }

}
