package nl.fontys.individualproject.services.impl;

import jakarta.transaction.Transactional;
import nl.fontys.individualproject.domain.enume.PlayingLocation;
import nl.fontys.individualproject.domain.response.Stadium;
import nl.fontys.individualproject.domain.enume.SurfaceType;
import nl.fontys.individualproject.domain.request.StadiumRequest;
import nl.fontys.individualproject.repositories.Entity.StadiumEntity;
import nl.fontys.individualproject.repositories.StadiumRepository;
import nl.fontys.individualproject.services.exception.*;
import nl.fontys.individualproject.services.validation.stadium.StadiumValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StadiumServiceTest {
    @Mock
    StadiumRepository stadiumRepository; // mimic the behaviours of the real object
    @Mock
    StadiumValidation stadiumValidation;
    @InjectMocks
    StadiumService stadiumService; // inject mock object into class being tested
    @BeforeEach
    void setUp() {

    }
    @Test
    void createStadium(){
        StadiumRequest stadiumRequest = StadiumRequest.builder()
                .name("Alloniasdcaza")
                .address("Kolaplaasdcperizer")
                .playingLocation(PlayingLocation.EINDHOVEN)
                .capacity(10)
                .surfaceType(SurfaceType.NaturalTurf)
                .build();

        StadiumEntity expected = StadiumEntity.builder()
                .id(1)
                .name("Alloniasdcaza")
                .address("Kolaplaasdcperizer")
                .playingLocation(PlayingLocation.EINDHOVEN)
                .capacity(10)
                .surfaceType(SurfaceType.NaturalTurf)
                .build();

        // set up behaviour , when calling id : 1 should return specific Stadium(1L, "Example Stadium") rather than query sql (mock result)

        doNothing().when(this.stadiumValidation).stadiumRequestChecking(stadiumRequest);
        when(this.stadiumRepository.save(Mockito.any(StadiumEntity.class))).thenReturn(expected);
        this.stadiumService.createStadium(stadiumRequest);
        verify(this.stadiumRepository,times(1)).save(Mockito.any(StadiumEntity.class));

//assertThat(expected).isSameAs(actual); TODO : Check this situation
    }

    @Test
    void createStadium_shouldThrowBlankInputException(){
        StadiumRequest stadiumRequest = StadiumRequest.builder()
                .name("")
                .address("")
                .playingLocation(PlayingLocation.EINDHOVEN)
                .capacity(0)
                .surfaceType(SurfaceType.NaturalTurf)
                .build();
        // void method
        doThrow(BlankInput.class).when(this.stadiumValidation).stadiumRequestChecking(stadiumRequest);

        assertThrows(BlankInput.class, () -> this.stadiumService.createStadium(stadiumRequest));
    }

    @Test
    void createStadium_shouldThrowStadiumNameAlreadyExist(){
        StadiumRequest stadiumRequest = StadiumRequest.builder()
                .name("Rapenland")
                .address("132 Kaspersa")
                .playingLocation(PlayingLocation.EINDHOVEN)
                .capacity(0)
                .surfaceType(SurfaceType.NaturalTurf)
                .build();
        // void method
        doThrow(StadiumNameAlreadyExist.class).when(this.stadiumValidation).stadiumRequestChecking(stadiumRequest);

        assertThrows(StadiumNameAlreadyExist.class, () -> this.stadiumService.createStadium(stadiumRequest));
    }

    @Test
    void createStadium_shouldThrowStadiumAddressAlreadyExist(){
        StadiumRequest stadiumRequest = StadiumRequest.builder()
                .name("Rapenland")
                .address("132 Kaspersa")
                .playingLocation(PlayingLocation.EINDHOVEN)
                .capacity(0)
                .surfaceType(SurfaceType.NaturalTurf)
                .build();
        // void method
        doThrow(StadiumAddressAlreadyExist.class).when(this.stadiumValidation).stadiumRequestChecking(stadiumRequest);

        assertThrows(StadiumAddressAlreadyExist.class, () -> this.stadiumService.createStadium(stadiumRequest));
    }

    @Test
    void getStadiumByID() {
        StadiumEntity expected = StadiumEntity.builder()
                .id(1)
                .name("Alloniasdcaza")
                .address("Kolaplaasdcperizer")
                .playingLocation(PlayingLocation.EINDHOVEN)
                .capacity(10)
                .surfaceType(SurfaceType.NaturalTurf)
                .build();

        when(this.stadiumRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(expected));

        doNothing().when(this.stadiumValidation).getStadiumByIdChecking(Optional.of(expected));

        Stadium actual = this.stadiumService.getStadiumByID(1).get();

        assertThat(expected.getId()).isSameAs(actual.getId());
        assertThat(expected.getName()).isSameAs(actual.getName());
        assertThat(expected.getAddress()).isSameAs(actual.getAddress());
        assertThat(expected.getPlayingLocation()).isSameAs(actual.getPlayingLocation());
        assertThat(expected.getCapacity()).isSameAs(actual.getCapacity());
        assertThat(expected.getSurfaceType()).isSameAs(actual.getSurfaceType());
    }

    @Test
    void getStadiumByID_shouldThrowIdNotFoundException() {

        when(this.stadiumRepository.findById(1)).thenReturn(Optional.empty());

        doThrow(IdNotfoundException.class).when(this.stadiumValidation).getStadiumByIdChecking(Optional.empty());

        assertThrows(IdNotfoundException.class, () -> this.stadiumService.getStadiumByID(1));
    }



    @Test
    void getAllStadium() {
        StadiumEntity expected1 = StadiumEntity.builder()
                .id(1)
                .name("Alloniasdcaza")
                .address("Kolaplaasdcperizer")
                .playingLocation(PlayingLocation.EINDHOVEN)
                .capacity(10)
                .surfaceType(SurfaceType.NaturalTurf)
                .build();
        StadiumEntity expected2 = StadiumEntity.builder()
                .id(2)
                .name("Kasperkola")
                .address("Minota")
                .playingLocation(PlayingLocation.EINDHOVEN)
                .capacity(10)
                .surfaceType(SurfaceType.NaturalTurf)
                .build();

        List<StadiumEntity> expectedStadiumList = new ArrayList<>();
        expectedStadiumList.add(expected1);
        expectedStadiumList.add(expected2);

        when(this.stadiumRepository.findAll()).thenReturn(expectedStadiumList);

        List<Stadium> actual = this.stadiumService.getAllStadium();

        assertEquals(actual.size(),expectedStadiumList.size());
    }

    @Test
    void updateStadium() {
        Stadium updateStadium = Stadium.builder()
                .id(1)
                .name("Alloniasdcaza")
                .address("Kolaplaasdcperizer")
                .playingLocation(PlayingLocation.EINDHOVEN)
                .capacity(10)
                .surfaceType(SurfaceType.NaturalTurf)
                .build();

        StadiumEntity expectedStadiumUpdate = StadiumEntity.builder()
                .id(updateStadium.getId())
                .name(updateStadium.getName())
                .address(updateStadium.getAddress())
                .playingLocation(updateStadium.getPlayingLocation())
                .capacity(updateStadium.getCapacity())
                .surfaceType(updateStadium.getSurfaceType())
                .build();

        doNothing().when(this.stadiumValidation).updateStadiumChecking(updateStadium);
        when(this.stadiumRepository.save(Mockito.any(StadiumEntity.class))).thenReturn(expectedStadiumUpdate);
        this.stadiumService.updateStadium(updateStadium);

        verify(this.stadiumRepository,times(1)).save(Mockito.any(StadiumEntity.class));
//        Stadium actualUpdated = this.stadiumService.updateStadium(updateStadium);

//        assertThat(expectedStadiumUpdate.getId()).isSameAs(actualUpdated.getId());
//        assertThat(expectedStadiumUpdate.getName()).isSameAs(actualUpdated.getName());
//        assertThat(expectedStadiumUpdate.getAddress()).isSameAs(actualUpdated.getAddress());
//        assertThat(expectedStadiumUpdate.getPlayingLocation()).isSameAs(actualUpdated.getPlayingLocation());
//        assertThat(expectedStadiumUpdate.getCapacity()).isSameAs(actualUpdated.getCapacity());
//        assertThat(expectedStadiumUpdate.getSurfaceType()).isSameAs(actualUpdated.getSurfaceType());
    }

    @Test
    void updateStadium_shouldThrowBlankInput() {
        Stadium updateStadium = Stadium.builder()
                .id(1)
                .name("")
                .address("")
                .playingLocation(PlayingLocation.EINDHOVEN)
                .capacity(0)
                .surfaceType(SurfaceType.NaturalTurf)
                .build();

        doThrow(BlankInput.class).when(this.stadiumValidation).updateStadiumChecking(updateStadium);

        assertThrows(BlankInput.class, () -> this.stadiumService.updateStadium(updateStadium));
    }

    @Test
    void updateStadium_shouldThrowCredentialAlreadyExist() {
        Stadium updateStadium = Stadium.builder()
                .id(1)
                .name("Kasperas")
                .address("12 Napolien")
                .playingLocation(PlayingLocation.EINDHOVEN)
                .capacity(0)
                .surfaceType(SurfaceType.NaturalTurf)
                .build();

        doThrow(CredentialAlreadyExist.class).when(this.stadiumValidation).updateStadiumChecking(updateStadium);

        assertThrows(CredentialAlreadyExist.class, () -> this.stadiumService.updateStadium(updateStadium));
    }

    @Test
    void deleteStadiumByID() {
        Stadium expectedToDelete = Stadium.builder()
                .id(1)
                .name("Alloniasdcaza")
                .address("Kolaplaasdcperizer")
                .playingLocation(PlayingLocation.EINDHOVEN)
                .capacity(10)
                .surfaceType(SurfaceType.NaturalTurf)
                .build();

        doNothing().when(this.stadiumValidation).idChecking(expectedToDelete.getId());

        doNothing().when(this.stadiumRepository).deleteById(1);

        this.stadiumService.deleteStadiumByID(1);

        verify(stadiumValidation, times(1)).idChecking(expectedToDelete.getId());
        verify(stadiumRepository, times(1)).deleteById(1);
    }

    @Test
    void deleteStadiumByID_shouldThrowIdNotFoundException() {
        doThrow(IdNotfoundException.class).when(this.stadiumValidation).idChecking(1);

        assertThrows(IdNotfoundException.class, () -> this.stadiumService.deleteStadiumByID(1));
    }
}