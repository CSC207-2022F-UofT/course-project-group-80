package com.group80.uoftinder;
import android.icu.text.UFormat;

import com.group80.uoftinder.entities.User;

import java.util.ArrayList;
import java.util.Map;
import java.util.Hashtable;
import java.util.List;

public class GenerateCompatibilityList {
    private User curUser;
    private int curUserScore;
    private ArrayList<String> compatibilityList;
    private RecOutputBoundary recOutputBoundary;
    private List<User> users;
    private UserScoreFacade usf;

    //TODO: use API
    // Navigate all users
    // RelatimDbControllder realtimeDbController = ...;
    // List<Users> realtimeDbController.getAllUsers(Void unused);
    //TODO: end use API

    public GenerateCompatibilityList() {
        RealtimeDbController realtimeDbController = ...;
        List<User> users = realtimeDbController.getAllUsers(Void unused, type=curUser.getUserType());
        usf = new UserScoreFacade(curUser, new CreateAccountInteractor()) // may need to change 2nd param
        int curUserScore = curUser.getScore();
    }

    public void generateCompatibilityList() {
        Map<String, Integer> compScores = calculateCompatibilityScores(users);
        for (int i = 0 ; i < users.size() ; i++) {
            String maxKey = getMaxKey(compScores);
            compatibilityList.add(maxKey);
            compScores.remove(maxKey);
        }
        recOutputBoundary.displayCompatibleUsers(compatibilityList);
    }

    public String getMaxKey(Map<String, Integer> userScore) {
        // TODO: change this to get key of max value
        int maxValue = 0;
        String maxKey = "";
        for (String key : userScore.keySet()) {
            if (userScore.get(key) > maxValue) {
                maxValue = userScore.get(key);
                maxKey = key;
            }
        }
        return maxKey;
    }

    public Map<String, Integer> calculateCompatibilityScores(List<User> users) {
        Map<String, Integer> compScores = new Hashtable<>();
        int curUserScore = curUser.getScore();
        for (User user : users) {
            calculateCompatibiltyScore(compScores, user);
        }
        return compScores;
    }

    public void calculateCompatibiltyScore(Map<String, Integer> compScores, User compUser) {
        int userScore = compUser.getScore();
        int compScore = usf.compare(curUserScore, userScore);
        compScores.put(compUser.getUid(), compScore);
    }

    public void update() {
        if (compatibilityList.size() > 0) {
            this.compatibilityList.remove(0);
            recOutputBoundary.displayCompatibleUsers(compatibilityList);
        }
    }
}
