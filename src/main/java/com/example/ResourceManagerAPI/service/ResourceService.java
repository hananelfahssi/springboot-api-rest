package com.example.ResourceManagerAPI.service;

import com.example.ResourceManagerAPI.dto.ResourceDTO;
import com.example.ResourceManagerAPI.entity.Resource;
import com.example.ResourceManagerAPI.exception.ResourceNotFoundException;
import com.example.ResourceManagerAPI.repository.ResourceRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ResourceService {

    private final ResourceRepository resourceRepository;

    public ResourceService(ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    @Cacheable("resources")
    public List<ResourceDTO> getAllResources() {
        return resourceRepository.findAll().stream()
                .map(resource -> new ResourceDTO(resource.getId(), resource.getName(), resource.getDescription()))
                .collect(Collectors.toList());
    }

    public ResourceDTO getResourceById(Long id) {
        Resource resource = resourceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found with id: " + id));
        return new ResourceDTO(resource.getId(), resource.getName(), resource.getDescription());
    }

    public ResourceDTO createResource(ResourceDTO resourceDTO) {
        Resource resource = new Resource();
        resource.setName(resourceDTO.getName());
        resource.setDescription(resourceDTO.getDescription());

        Resource savedResource = resourceRepository.save(resource);
        return new ResourceDTO(savedResource.getId(), savedResource.getName(), savedResource.getDescription());
    }

    public ResourceDTO updateResource(Long id, ResourceDTO resourceDTO) {
        Resource resource = resourceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found with id: " + id));

        resource.setName(resourceDTO.getName());
        resource.setDescription(resourceDTO.getDescription());

        Resource updatedResource = resourceRepository.save(resource);
        return new ResourceDTO(updatedResource.getId(), updatedResource.getName(), updatedResource.getDescription());
    }

    public void deleteResource(Long id) {
        if (!resourceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Resource not found with id: " + id);
        }
        resourceRepository.deleteById(id);
    }
}
