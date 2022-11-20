package com.group80.uoftinder;

import com.group80.uoftinder.entities.User;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

/**
 * Unit tests for the UserScoreFacade class
 */
public class UserScoreFacadeUnitTest {
    @Test
    public void generateCompatibilityScoreTest1() {
        CreateAccountInteractor.setAnswerSchema(new int[] {3, 4, 4, 5, 4});
        CreateAccountInteractor.setIsMultiSelect(new boolean[] {false, true, true, false, false});

        ArrayList<HashSet<Integer>> userAnswers = new ArrayList<>();
        userAnswers.add(new HashSet<>(Collections.singletonList(1)));
        userAnswers.add(new HashSet<>(Arrays.asList(1, 2)));
        userAnswers.add(new HashSet<>(Arrays.asList(0, 1, 3)));
        userAnswers.add(new HashSet<>(Collections.singletonList(0)));
        userAnswers.add(new HashSet<>(Collections.singletonList(2)));

        User currUser = new User();
        currUser.setAnswers(userAnswers);

        UserScoreFacade usf = new UserScoreFacade(currUser);
        int userScore = usf.generateCompatibilityScore();
        // binary: 010110110100010
        // decimal: 11682

        assert userScore == 11682;
    }

    @Test
    public void generateCompatibilityScoreTest2() {
        CreateAccountInteractor.setAnswerSchema(new int[] {3, 4, 4, 5, 4});
        CreateAccountInteractor.setIsMultiSelect(new boolean[] {false, true, true, false, false});

        ArrayList<HashSet<Integer>> userAnswers = new ArrayList<>();
        userAnswers.add(new HashSet<>(Collections.singletonList(2))); // single
        userAnswers.add(new HashSet<>(Collections.singletonList(1))); // multi
        userAnswers.add(new HashSet<>(Arrays.asList(1, 2))); // multi
        userAnswers.add(new HashSet<>(Collections.singletonList(4))); // single
        userAnswers.add(new HashSet<>(Collections.singletonList(2))); // single

        User currUser = new User();
        currUser.setAnswers(userAnswers);

        UserScoreFacade usf = new UserScoreFacade(currUser);
        int userScore = usf.generateCompatibilityScore();
        // binary: 100100011010010
        // decimal: 18642

        assert userScore == 18642;
    }

    @Test
    public void compareTest() {
        CreateAccountInteractor.setAnswerSchema(new int[] {3, 4, 4, 5, 4});
        CreateAccountInteractor.setIsMultiSelect(new boolean[] {false, true, true, false, false});

        ArrayList<HashSet<Integer>> userAnswers = new ArrayList<>();
        userAnswers.add(new HashSet<>(Collections.singletonList(1)));
        userAnswers.add(new HashSet<>(Arrays.asList(1, 2)));
        userAnswers.add(new HashSet<>(Arrays.asList(0, 1, 3)));
        userAnswers.add(new HashSet<>(Collections.singletonList(0)));
        userAnswers.add(new HashSet<>(Collections.singletonList(2)));

        User currUser = new User();
        currUser.setAnswers(userAnswers);

        UserScoreFacade usf = new UserScoreFacade(currUser);
        // binary: 01-0110-1101-000-10
        int userScore2 = 18642;
        // binary: 10-0100-0110-100-10

        int similarity = usf.compare(userScore2);
        assert similarity == 3; // 0 + 1 + 1 + 0 + 1
    }
}