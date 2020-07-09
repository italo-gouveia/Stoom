package com.stoom.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.stoom.data.vo.LocationVO;
import com.stoom.services.ILocationService;
import com.stoom.util.HateoasUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "LocationEndpoint")
@RestController
@RequestMapping("/api/location")
public class LocationController {

	@Autowired
	private ILocationService service;

    @Autowired
    private PagedResourcesAssembler<LocationVO> assembler;

    private static final String DESC_CONST = "desc";

	@ApiOperation(value = "Return a simples String of test of version API Response defined by header: X-API-VERSION" )
	@GetMapping(produces = { "application/json", "application/xml", "application/x-yaml" }, headers = "X-API-VERSION=2")
	public String findAll() {
		return "Hello API V2";
	}

    @ApiOperation(value = "List all Locations" )
    @GetMapping(produces = { "application/json", "application/xml", "application/x-yaml" }, headers = "X-API-VERSION=1")
    public ResponseEntity<PagedModel<EntityModel<LocationVO>>> findAll(
            @RequestParam(value="page", defaultValue = "0") int page,
            @RequestParam(value="limit", defaultValue = "12") int limit,
            @RequestParam(value="direction", defaultValue = "asc") String direction) {
    	Page<LocationVO> locations =  null;
    	PagedModel<EntityModel<LocationVO>> resources = null;
    	Pageable pageable = null;
    	Direction sortDirection = null;
    	
        sortDirection = DESC_CONST.equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;

        pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "name"));

        locations =  service.findAll(pageable);
        locations
                .stream()
                .forEach(p -> p.add(
                        linkTo(methodOn(LocationController.class).findById(p.getKey())).withSelfRel(),
                        linkTo(methodOn(LocationController.class).update(p)).withRel(HateoasUtil.UPDATE_REL),
                        linkTo(methodOn(LocationController.class).delete(p.getKey())).withRel(HateoasUtil.DELETE_REL)
                        )
                );

        resources = assembler.toModel(locations);

        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @ApiOperation(value = "Find Locations by name" )
    @GetMapping(value = "/findLocationByName/{name}", produces = { "application/json", "application/xml", "application/x-yaml" }, headers = "X-API-VERSION=1")
    public ResponseEntity<PagedModel<EntityModel<LocationVO>>> findPersonByName(
            @PathVariable("name") String name,
            @RequestParam(value="page", defaultValue = "0") int page,
            @RequestParam(value="limit", defaultValue = "12") int limit,
            @RequestParam(value="direction", defaultValue = "asc") String direction) {
    	Page<LocationVO> locations =  null;
    	PagedModel<EntityModel<LocationVO>> resources = null;
    	Pageable pageable = null;
    	Direction sortDirection = null;
    	
    	sortDirection = DESC_CONST.equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;

    	pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "name"));

        locations =  service.findLocationByName(name, pageable);
        locations
                .stream()
                .forEach(p -> p.add(
                        linkTo(methodOn(LocationController.class).findById(p.getKey())).withSelfRel(),
                        linkTo(methodOn(LocationController.class).update(p)).withRel(HateoasUtil.UPDATE_REL),
                        linkTo(methodOn(LocationController.class).delete(p.getKey())).withRel(HateoasUtil.DELETE_REL)
                        )
                );

        resources = assembler.toModel(locations);

        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @ApiOperation(value = "Find a Location by ID" )
	@GetMapping(value = "/{id}", produces = { "application/json", "application/xml", "application/x-yaml" }, headers = "X-API-VERSION=1")
	public LocationVO findById(@PathVariable("id") Long id) {
		LocationVO locationVO = null;
		locationVO = service.findById(id);
		locationVO.add(linkTo(methodOn(LocationController.class).findById(id)).withSelfRel());
        locationVO.add(linkTo(methodOn(LocationController.class).update(locationVO)).withRel(HateoasUtil.UPDATE_REL));
        locationVO.add(linkTo(methodOn(LocationController.class).delete(id)).withRel(HateoasUtil.DELETE_REL));
		return locationVO;
	}

	@ApiOperation(value = "Create a new Location")
	@PostMapping(produces = { "application/json", "application/xml", "application/x-yaml" },
			consumes = { "application/json", "application/xml", "application/x-yaml" }, headers = "X-API-VERSION=1")
	public LocationVO create(@RequestBody LocationVO location) {
		LocationVO locationVO = service.create(location);
		locationVO.add(linkTo(methodOn(LocationController.class).findById(locationVO.getKey())).withSelfRel());
        locationVO.add(linkTo(methodOn(LocationController.class).update(locationVO)).withRel(HateoasUtil.UPDATE_REL));
        locationVO.add(linkTo(methodOn(LocationController.class).delete(locationVO.getKey())).withRel(HateoasUtil.DELETE_REL));
		return locationVO;
	}

	@ApiOperation(value = "Update a Location by ID")
	@PutMapping(produces = { "application/json", "application/xml", "application/x-yaml" },
			consumes = { "application/json", "application/xml", "application/x-yaml" }, headers = "X-API-VERSION=1")
	public LocationVO update(@RequestBody LocationVO location) {
		LocationVO locationVO = service.update(location);
		locationVO.add(linkTo(methodOn(LocationController.class).findById(locationVO.getKey())).withSelfRel());
        locationVO.add(linkTo(methodOn(LocationController.class).update(locationVO)).withRel(HateoasUtil.UPDATE_REL));
        locationVO.add(linkTo(methodOn(LocationController.class).delete(locationVO.getKey())).withRel(HateoasUtil.DELETE_REL));
		return locationVO;
	}

	@ApiOperation(value = "Delete a Location by ID")
	@DeleteMapping(value="/{id}", headers = "X-API-VERSION=1")
	public ResponseEntity<BodyBuilder> delete(@PathVariable("id") Long id) {
		service.delete(id);
		return ResponseEntity.ok().build();
	}

}
