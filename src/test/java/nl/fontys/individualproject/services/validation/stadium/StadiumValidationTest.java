package nl.fontys.individualproject.services.validation.stadium;

import nl.fontys.individualproject.domain.enume.PlayingLocation;
import nl.fontys.individualproject.domain.enume.SurfaceType;
import nl.fontys.individualproject.domain.request.StadiumRequest;
import nl.fontys.individualproject.domain.response.Stadium;
import nl.fontys.individualproject.repositories.Entity.StadiumEntity;
import nl.fontys.individualproject.repositories.StadiumRepository;
import nl.fontys.individualproject.services.exception.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StadiumValidationTest {

    @Mock
    private StadiumRepository stadiumRepository;
    @InjectMocks
    private StadiumValidation stadiumValidation;
    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void stadiumRequestChecking_ShouldThrowBlankInput_WithName() {
        StadiumRequest stadiumRequest = StadiumRequest.builder()
                .name("")
                .address("asc")
                .capacity(0)
                .build();

        assertThrows(BlankInput.class,() -> this.stadiumValidation.stadiumRequestChecking(stadiumRequest));
    }

    @Test
    void stadiumRequestChecking_ShouldThrowBlankInput_WithAddress() {
        StadiumRequest stadiumRequest = StadiumRequest.builder()
                .name("asc")
                .address("")
                .capacity(0)
                .build();

        assertThrows(BlankInput.class,() -> this.stadiumValidation.stadiumRequestChecking(stadiumRequest));
    }

    @Test
    void stadiumRequestChecking_ShouldThrowBlankInput_WithCapacity() {
        StadiumRequest stadiumRequest = StadiumRequest.builder()
                .name("asc")
                .address("asc")
                .capacity(0)
                .build();

        assertThrows(BlankInput.class,() -> this.stadiumValidation.stadiumRequestChecking(stadiumRequest));
    }
    @Test
    void stadiumRequestChecking_ShouldThrowBlankInput_WithMinusCapacity() {
        StadiumRequest stadiumRequest = StadiumRequest.builder()
                .name("asc")
                .address("asc")
                .capacity(-12)
                .build();

        assertThrows(BlankInput.class,() -> this.stadiumValidation.stadiumRequestChecking(stadiumRequest));
    }

    @Test
    void stadiumRequestChecking_ShouldThrowStadiumNameAlreadyExist() {
        StadiumRequest stadiumRequest = StadiumRequest.builder()
                .name("asd")
                .address("asd")
                .capacity(12)
                .build();

        when(this.stadiumRepository.existsByName(Mockito.anyString())).thenReturn(true);

        assertThrows(StadiumNameAlreadyExist.class,() -> this.stadiumValidation.stadiumRequestChecking(stadiumRequest));
    }

    @Test
    void stadiumRequestChecking_ShouldThrowStadiumAddressAlreadyExist() {
        StadiumRequest stadiumRequest = StadiumRequest.builder()
                .name("asd")
                .address("asd")
                .capacity(12)
                .build();

        when(this.stadiumRepository.existsByAddress(Mockito.anyString())).thenReturn(true);

        assertThrows(StadiumAddressAlreadyExist.class,() -> this.stadiumValidation.stadiumRequestChecking(stadiumRequest));
    }

    @Test
    void stadiumRequestChecking() {
        StadiumRequest stadiumRequest = StadiumRequest.builder()
                .name("asd")
                .address("asd")
                .capacity(12)
                .build();

        this.stadiumValidation.stadiumRequestChecking(stadiumRequest);
    }

    @Test
    void getStadiumByIdChecking() {
        StadiumEntity stadiumEntity = StadiumEntity.builder()
                .id(2)
                .name("Kasperkola")
                .address("Minota")
                .playingLocation(PlayingLocation.EINDHOVEN)
                .capacity(10)
                .surfaceType(SurfaceType.NaturalTurf)
                .build();
        this.stadiumValidation.getStadiumByIdChecking(Optional.of(stadiumEntity));
    }

    @Test
    void getStadiumByIdChecking_ShouldThrowIdNotfoundException() {
        assertThrows(IdNotfoundException.class, () -> this.stadiumValidation.getStadiumByIdChecking(Optional.empty()));
    }

    @Test
    void updateStadiumChecking_ShouldThrowBlankInput_withName() {
        Stadium stadiumRequest = Stadium.builder()
                .name("")
                .address("asc")
                .capacity(0)
                .build();

        assertThrows(BlankInput.class,() -> this.stadiumValidation.updateStadiumChecking(stadiumRequest));
    }

    @Test
    void updateStadiumChecking_ShouldThrowBlankInput_withAddress() {
        Stadium stadiumRequest = Stadium.builder()
                .name("asd")
                .address("")
                .capacity(0)
                .build();

        assertThrows(BlankInput.class,() -> this.stadiumValidation.updateStadiumChecking(stadiumRequest));
    }

    @Test
    void updateStadiumChecking_ShouldThrowBlankInput_withCapacty() {
        Stadium stadiumRequest = Stadium.builder()
                .name("asd")
                .address("")
                .capacity(0)
                .build();

        assertThrows(BlankInput.class,() -> this.stadiumValidation.updateStadiumChecking(stadiumRequest));
    }

    @Test
    void updateStadiumChecking_ShouldThrowBlankInput_withMinusCapacty() {
        Stadium stadiumRequest = Stadium.builder()
                .name("asd")
                .address("asca")
                .capacity(-20)
                .build();

        assertThrows(BlankInput.class,() -> this.stadiumValidation.updateStadiumChecking(stadiumRequest));
    }

    @Test
    void updateStadiumChecking_empty_DuplicateList() {
        Stadium stadiumRequest = Stadium.builder()
                .name("asd")
                .address("asdc")
                .capacity(20)
                .build();
        when(this.stadiumRepository.getByNameAndAddressIgnoreIdCase(anyString(),anyString(),anyInt())).thenReturn(Collections.emptyList());
        this.stadiumValidation.updateStadiumChecking(stadiumRequest);
    }

    @Test
    void updateStadiumChecking_notEmpty_DuplicateList() {
        Stadium stadiumRequest = Stadium.builder()
                .name("asd")
                .address("asdc")
                .capacity(20)
                .build();

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

        List<StadiumEntity> stadiumEntityList = new ArrayList<>();

        stadiumEntityList.add(expected1);
        stadiumEntityList.add(expected2);

        when(this.stadiumRepository.getByNameAndAddressIgnoreIdCase(anyString(),anyString(),anyInt())).thenReturn(stadiumEntityList);
        assertThrows(CredentialAlreadyExist.class,() -> this.stadiumValidation.updateStadiumChecking(stadiumRequest));
    }

    @Test
    void idChecking() {
        when(this.stadiumRepository.existsById(Mockito.anyInt())).thenReturn(true);
        this.stadiumValidation.idChecking(12);
    }

    @Test
    void idChecking_ThrowIdNotfoundException() {
        when(this.stadiumRepository.existsById(Mockito.anyInt())).thenReturn(false);
        assertThrows(IdNotfoundException.class, () -> this.stadiumValidation.idChecking(12));
    }
}