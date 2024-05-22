package team.haedal.gifticionfunding.service.common;

public interface UseCase <Input, Output>{
    Output invoke(Input input);
}
