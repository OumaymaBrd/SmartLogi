package org.example.smartspring.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.smartspring.dto.livreur.AddLivreurDTO;
import org.example.smartspring.dto.livreur.LivreurDTO;
import org.example.smartspring.dto.livreur.UpdateLivreurDTO;
import org.example.smartspring.entities.Livreur;
import org.example.smartspring.entities.Zone;
import org.example.smartspring.exception.ResourceNotFoundException;
import org.example.smartspring.mapper.LivreurMapper;
import org.example.smartspring.repository.LivreurRepository;
import org.example.smartspring.repository.ZoneRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class LivreurService {

}
