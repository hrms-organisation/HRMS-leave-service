package pfa.dev.leaveservie.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.core.support.RepositoryMethodInvocationListener;
import org.springframework.stereotype.Service;
import pfa.dev.leaveservie.dto.LeaveTypeDto;
import pfa.dev.leaveservie.enities.LeaveType;
import pfa.dev.leaveservie.mapper.LeaveRequestMapper;
import pfa.dev.leaveservie.mapper.LeaveTypeMapper;
import pfa.dev.leaveservie.repositories.LeaveTypeRepository;

@Service
@RequiredArgsConstructor

public class LeaveTypeServiceImpl implements LeaveTypeService {
    private final LeaveTypeRepository leaveTypeRepository;
    private final LeaveTypeMapper leaveTypeMapper;

    @Override
    public LeaveTypeDto createLeaveType(LeaveTypeDto leaveTypeDto) {
        LeaveType leaveType = existsLeaveTypeByName(leaveTypeDto.getName());
        if (leaveType != null) {
            throw  new IllegalArgumentException("===========================LeaveType already exists");
        }
        leaveTypeMapper.toEntity(leaveTypeDto);
        leaveType = leaveTypeRepository.save(leaveTypeMapper.toEntity(leaveTypeDto));


        return leaveTypeMapper.toDto(leaveType);
    }

    @Override
    public LeaveTypeDto updateLeaveType(LeaveTypeDto leaveTypeDto, Long id) {
        LeaveType leaveType =existsLeaveTypeByName(leaveTypeDto.getName());
        if (leaveType != null) {
            throw  new RuntimeException("==========================LeaveType already exists");

        }
      LeaveType leave =  leaveTypeMapper.toEntity(leaveTypeDto);
        leaveType = leaveTypeRepository.save(leave);

        return leaveTypeMapper.toDto(leaveType);
    }

    @Override
    public LeaveTypeDto findLeaveTypeById(Long id) {
        LeaveType leaveType = leaveTypeRepository.findById(id).orElseThrow(()->  new RuntimeException("LeaveType not found")  )   ;
        return leaveTypeMapper.toDto(leaveType);
    }

    @Override
    public Page<LeaveTypeDto> findLeaveTypeByPage(Pageable pageable) {
        return leaveTypeRepository.findAll(pageable).map(leaveTypeMapper::toDto);


    }

    @Override
    public void deleteLeaveTypeById(Long id) {
        LeaveType leaveType = leaveTypeRepository.findById(id).orElseThrow(()->
                new RuntimeException("LeaveType not found")  );
        leaveTypeRepository.deleteById(id);

    }
    private LeaveType existsLeaveTypeByName(String name) {
        return leaveTypeRepository.findByName(name);
    }
}
