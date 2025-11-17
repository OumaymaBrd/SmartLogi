package org.example.smartspring.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.smartspring.dto.zone.AddZoneDTO;
import org.example.smartspring.dto.zone.ZoneDTO;
import org.example.smartspring.dto.zone.UpdateZoneDTO;
import org.example.smartspring.entities.Zone;
import org.example.smartspring.exception.ResourceNotFoundException;
import org.example.smartspring.mapper.ZoneMapper;
import org.example.smartspring.repository.ZoneRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ZoneService {

}
