package nl.fontys.individualproject.services.impl;

import nl.fontys.individualproject.domain.enume.PlayingLocation;
import nl.fontys.individualproject.domain.response.Coach;
import nl.fontys.individualproject.repositories.CoachRepository;
import nl.fontys.individualproject.repositories.Entity.CoachEntity;
import nl.fontys.individualproject.services.impl.converter.CoachConvert;
import nl.fontys.individualproject.services.validation.user.UserValidation;
import org.apache.el.stream.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class CoachStatusServiceTest {
    @Mock
    private CoachRepository coachRepository;
    @Mock
    private UserValidation userValidation;

    @InjectMocks
    private CoachStatusService coachStatusService;

    @Test
    void getAllCoachRequest() {
        List<CoachEntity> coachEntityList = new ArrayList<>();
        CoachEntity expect = CoachEntity.builder()
                .id(12)
                .username("Jakacc")
                .gmail("jackie@gmail.com")
                .password("lombox")
                .dateOfBirth(LocalDate.of(2002,9,9))
                .playingLocation(PlayingLocation.EINDHOVEN)
                .description("I can do all thing")
                .experience("1 year with SOLID")
                .status("PENDING")
                .build();
        coachEntityList.add(expect);
        when(this.coachRepository.findAll()).thenReturn(coachEntityList);

        List<Coach> coaches = this.coachStatusService.getAllCoachRequest();
        assertEquals(1,coaches.size());
    }

    @Test
    void getAllCoachRequest_c_not_present() {
        List<CoachEntity> coachEntityList = new ArrayList<>();
        CoachEntity expect = CoachEntity.builder()
                .status("ACCEPT")
                .build();
        coachEntityList.add(expect);
        when(this.coachRepository.findAll()).thenReturn(coachEntityList);

        List<Coach> coaches = this.coachStatusService.getAllCoachRequest();
        assertEquals(0,coaches.size());
    }

    @Test
    void getAllActiveCoach() {
        List<CoachEntity> coachEntityList = new ArrayList<>();
        CoachEntity expect = CoachEntity.builder()
                .id(12)
                .username("Jakacc")
                .gmail("jackie@gmail.com")
                .password("lombox")
                .dateOfBirth(LocalDate.of(2002,9,9))
                .playingLocation(PlayingLocation.EINDHOVEN)
                .description("I can do all thing")
                .experience("1 year with SOLID")
                .status("ACCEPT")
                .build();
        coachEntityList.add(expect);
        when(this.coachRepository.findAll()).thenReturn(coachEntityList);
        List<Coach> coaches = this.coachStatusService.getAllActiveCoach();
        assertEquals(1,coaches.size());
    }

    @Test
    void getAllActiveCoach_c_not_present() {
        List<CoachEntity> coachEntityList = new ArrayList<>();
        CoachEntity expect = CoachEntity.builder()
                .status("PENDING")
                .build();
        coachEntityList.add(expect);
        when(this.coachRepository.findAll()).thenReturn(coachEntityList);

        List<Coach> coaches = this.coachStatusService.getAllActiveCoach();
        assertEquals(0,coaches.size());
    }

    @Test
    void acceptCoachStatus() {
        doNothing().when(this.userValidation).checkingUserExistWithID(12);

        doNothing().when(this.coachRepository).updateCoachStatus(12,"ACCEPT");

        this.coachStatusService.acceptCoachStatus(12);
        verify(coachRepository, times(1)).updateCoachStatus(anyInt(),anyString());
    }

    @Test
    void declineCoachStatus() {
        doNothing().when(this.userValidation).checkingUserExistWithID(12);

        doNothing().when(this.coachRepository).updateCoachStatus(12,"DECLINE");

        this.coachStatusService.declineCoachStatus(12);
        verify(coachRepository, times(1)).updateCoachStatus(anyInt(),anyString());

    }
}