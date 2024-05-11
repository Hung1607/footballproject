package nl.fontys.individualproject.services.impl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import nl.fontys.individualproject.domain.response.Coach;
import nl.fontys.individualproject.repositories.CoachRepository;
import nl.fontys.individualproject.repositories.Entity.CoachEntity;
import nl.fontys.individualproject.services.impl.converter.CoachConvert;
import nl.fontys.individualproject.services.validation.user.UserValidation;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CoachStatusService {
    private CoachRepository coachRepository;
    private UserValidation userValidation;
    public List<Coach> getAllCoachRequest(){
        List<CoachEntity> coachList  = this.coachRepository.findAll();
        List<Coach> requestCoachList = new ArrayList<>();
        for(CoachEntity coach : coachList){
            if(Objects.equals(coach.getStatus(), "PENDING")){
                Optional<Coach> c = Optional.of(coach).map(CoachConvert::convert);
                if(c.isPresent()){
                    Coach requestCoach = c.get();
                    requestCoachList.add(requestCoach);
                }
            }
        }
        return requestCoachList;
    }

    public List<Coach> getAllActiveCoach(){
        List<CoachEntity> coachList  = this.coachRepository.findAll();
        List<Coach> activeCoachList = new ArrayList<>();
        for(CoachEntity coach : coachList){
            if(Objects.equals(coach.getStatus(), "ACCEPT")){
                Optional<Coach> c = Optional.of(coach).map(CoachConvert::convert);
                if(c.isPresent()){
                    Coach requestCoach = c.get();
                    activeCoachList.add(requestCoach);
                }
            }
        }
        return activeCoachList;
    }


    @Transactional
    public void acceptCoachStatus(int coachID){
        this.userValidation.checkingUserExistWithID(coachID);
        this.coachRepository.updateCoachStatus(coachID,"ACCEPT");
    }
    @Transactional
    public void declineCoachStatus(int coachID){
        this.userValidation.checkingUserExistWithID(coachID);
        this.coachRepository.updateCoachStatus(coachID,"DECLINE");
    }
}
