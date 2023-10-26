package com.driver.services;


import com.driver.EntryDto.SubscriptionEntryDto;
import com.driver.model.Subscription;
import com.driver.model.SubscriptionType;
import com.driver.model.User;
import com.driver.repository.SubscriptionRepository;
import com.driver.repository.UserRepository;
import com.driver.transformers.SubscriptionTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class SubscriptionService {

    @Autowired
    SubscriptionRepository subscriptionRepository;

    @Autowired
    UserRepository userRepository;

    public Integer buySubscription(SubscriptionEntryDto subscriptionEntryDto){

        //Save The subscription Object into the Db and return the total Amount that user has to pay
        Optional<User>userOptional=userRepository.findById(subscriptionEntryDto.getUserId());
        if(userOptional.isPresent()){
            User user =userOptional.get();
            Subscription subscription= SubscriptionTransformer.subscriptionTransformerToSubscription(subscriptionEntryDto);
            if(subscriptionEntryDto.getSubscriptionType()== SubscriptionType.BASIC){
                subscription.setTotalAmountPaid(500+(200*subscription.getNoOfScreensSubscribed()));
            }else if(subscriptionEntryDto.getSubscriptionType()==SubscriptionType.PRO){
                subscription.setTotalAmountPaid(800+(250* subscription.getNoOfScreensSubscribed()));
            }else if(subscriptionEntryDto.getSubscriptionType()==SubscriptionType.ELITE){
                subscription.setTotalAmountPaid(1000+(350*subscription.getNoOfScreensSubscribed()));
            }
            subscription.setUser(user);
            user.setSubscription(subscription);
            subscriptionRepository.save(subscription);
            return subscription.getTotalAmountPaid();
        }
        return null;
    }

    public Integer upgradeSubscription(Integer userId)throws Exception{

        //If you are already at an ElITE subscription : then throw Exception ("Already the best Subscription")
        //In all other cases just try to upgrade the subscription and tell the difference of price that user has to pay
        //update the subscription in the repository
        Optional<User>userOptional=userRepository.findById(userId);
        if(userOptional.isPresent()){
            User user =userOptional.get();
            if(user.getSubscription().getSubscriptionType()==SubscriptionType.ELITE){
                throw new Exception("Already the best Subscription");
            }else if(user.getSubscription().getSubscriptionType()==SubscriptionType.PRO){
                int currCost=user.getSubscription().getTotalAmountPaid();
                int updCost=1000+(350*(user.getSubscription().getNoOfScreensSubscribed()));
                user.getSubscription().setSubscriptionType(SubscriptionType.PRO);
                user.getSubscription().setTotalAmountPaid(updCost);
                return updCost-currCost;
            }else if(user.getSubscription().getSubscriptionType()==SubscriptionType.BASIC){
                int currCost=user.getSubscription().getTotalAmountPaid();
                int UpdCost=800+(250*(user.getSubscription().getNoOfScreensSubscribed()));
                user.getSubscription().setSubscriptionType(SubscriptionType.PRO);
                user.getSubscription().setTotalAmountPaid(UpdCost);
                return UpdCost-currCost;
            }
            userRepository.save(user);
        }

        return null;
    }

    public Integer calculateTotalRevenueOfHotstar(){

        //We need to find out total Revenue of hotstar : from all the subscriptions combined
        //Hint is to use findAll function from the SubscriptionDb
        List<Subscription>subscriptions=subscriptionRepository.findAll();
        int cost=0;
        for(Subscription subscription:subscriptions){
            cost+=subscription.getTotalAmountPaid();
        }
        return cost;
    }

}
