package com.stoom.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.stoom.data.vo.LocationVO;

public interface ILocationService {
	 public LocationVO create(LocationVO client);
	 public Page<LocationVO> findLocationByName(String name, Pageable pageable);
	 public Page<LocationVO> findAll(Pageable pageable);
	 public LocationVO findById(Long id);
	 public LocationVO update(LocationVO client);
	 public void delete(Long id);

}
