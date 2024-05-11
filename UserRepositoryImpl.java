
//import nl.fontys.individualproject.repositories.CoachCreateRepository;
//import nl.fontys.individualproject.repositories.Entity.CoachEntity;
//import nl.fontys.individualproject.repositories.Entity.PlayerEntity;
//import nl.fontys.individualproject.repositories.Entity.UserEntity;
//import nl.fontys.individualproject.repositories.PlayerCreateRepository;
//import nl.fontys.individualproject.repositories.UserRepository;
//import org.springframework.stereotype.Repository;
//
//import java.lang.reflect.Type;
//import java.net.Proxy;
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Objects;
//import java.util.Optional;
//
//@Repository
//public class UserRepositoryImpl implements UserRepository, PlayerCreateRepository, CoachCreateRepository {
//
//    private List<UserEntity> userAvailable;
//
//    private static int UserID = 3 ;
//    public UserRepositoryImpl(){
//        this.userAvailable = new ArrayList<>();
//          this.userAvailable.add( PlayerEntity.builder()
//                  .id(1)
//                  .gmail("jackie@gmail.com")
//                  .username("jackie16")
//                  .password("badhabit")
//                  .dateOfBirth(LocalDate.of(2002, 7,20))
//                  .playingLocation(PlayingLocation.AMSTERDAM)
//                  .position(Position.FORWARD).build());
//          this.userAvailable.add( PlayerEntity.builder()
//                .id(2)
//                .gmail("kevinnguyen@gmail.com")
//                .username("kevin17")
//                .password("spiderboy")
//                .dateOfBirth(LocalDate.of(2003, 9,12))
//                .playingLocation(PlayingLocation.EINDHOVEN)
//                .position(Position.MIDFIELDER).build());
//          this.userAvailable.add( CoachEntity.builder()
//                  .id(3)
//                  .gmail("tmmy@gmail.com")
//                  .username("tommy")
//                  .password("tm34")
//                  .dateOfBirth(LocalDate.of(2003, 9,12))
//                  .playingLocation(PlayingLocation.EINDHOVEN)
//                  .description("I can speak english")
//                  .experience("2 years with Manchester United")
//                  .build());
//    }
//
//    @Override
//    public List<UserEntity> getAllUser(Class<?> classType){
//        try{
//            List<UserEntity> userList = new ArrayList<>();
//            for(UserEntity userEntity : this.userAvailable){
//                if( classType.isInstance(userEntity)){
//                    userList.add(userEntity);
//                }
//            }
//            return userList;
//        }
//        catch (Exception e){
//            return null;
//        }
//    }
//
//    @Override
//    public Optional<UserEntity> getUserByID(int id){
//        for(UserEntity userEntity : this.userAvailable){
//            if(Objects.equals(userEntity.getId(), id)){
//                return Optional.of(userEntity);
//            }
//        }
//        return Optional.empty();
//    }
//
//    @Override
//    public Boolean isGmailExistWithCreate(String gmail){
//        return this.userAvailable
//                .stream()
//                .anyMatch(user -> user.getGmail().equals(gmail));
//    }
//
//    @Override
//    public Boolean isUsernameExistWithCreate(String username){
//        return this.userAvailable
//                .stream()
//                .anyMatch(user -> user.getUsername().equals(username));
//    }
//
//    @Override
//    public Boolean isGmailExistWithID(String gmail,int userID){
//        return this.userAvailable
//                .stream()
//                .filter(user -> user.getId() != userID)
//                .anyMatch(user -> user.getGmail().equals(gmail));
//    }
//
//    @Override
//    public Boolean isUsernameExistWithID(String username,int userID){
//        return this.userAvailable
//                .stream()
//                .filter(user -> user.getId() != userID)
//                .anyMatch(user -> user.getGmail().equals(username));
//    }
//
//    @Override
//    public Boolean updateUser(UserEntity userRequestUpdated){
//        try {
//            for(int i = 0 ; i < this.userAvailable.size() ; i++){
//                UserEntity userEntity = this.userAvailable.get(i);
//                if(userEntity.getId() == userRequestUpdated.getId()){
//                    this.userAvailable.set(i,userRequestUpdated);
//                    return true;
//                }
//            }
//            return false;
//        }
//        catch (Exception e){
//            return false;
//        }
//    }
//
//    @Override
//    public Boolean deleteUserByID(int id){
//        try {
//            for(int i = 0 ; i < this.userAvailable.size() ; i++){
//                UserEntity userEntity = this.userAvailable.get(i);
//                if(userEntity.getId() == id){
//                    this.userAvailable.remove(i);
//                    return true;
//                }
//            }
//            return false;
//        }
//        catch (Exception e){
//            return false;
//        }
//    }
//
//    @Override
//    public Boolean findID(int PlayerID){
//        for(UserEntity userEntity : this.userAvailable){
//            if(Objects.equals(userEntity.getId(), PlayerID)){
//                return true;
//            }
//        }
//        return false;
//    }
//    //
//    @Override
//    public Boolean createPlayer(PlayerEntity PlayerEntity){
//        try{
//            UserID++;
//            PlayerEntity.setId(UserID);
//            this.userAvailable.add(PlayerEntity);
//            return true;
//        }
//        catch (Exception e){
//            return false;
//        }
//    }
//
//    @Override
//    public Boolean createCoach(CoachEntity coachEntity){
//        try{
//            UserID++;
//            coachEntity.setId(UserID);
//            this.userAvailable.add(coachEntity);
//            return true;
//        }
//        catch (Exception e){
//            return false;
//        }
//    }
//
//
//}
