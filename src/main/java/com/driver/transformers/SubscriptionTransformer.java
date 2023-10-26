package com.driver.transformers;

import com.driver.EntryDto.SubscriptionEntryDto;
import com.driver.model.Subscription;
import com.driver.model.SubscriptionType;
import com.driver.model.User;

public class SubscriptionTransformer {
    public static Subscription subscriptionTransformerToSubscription(SubscriptionEntryDto subscriptionEntryDto, User user){
        Subscription subscription=new Subscription();
        subscription.setSubscriptionType(subscriptionEntryDto.getSubscriptionType());
        subscription.setNoOfScreensSubscribed(subscriptionEntryDto.getNoOfScreensRequired());
        subscription.setUser(user);
        return subscription;
    }
}
