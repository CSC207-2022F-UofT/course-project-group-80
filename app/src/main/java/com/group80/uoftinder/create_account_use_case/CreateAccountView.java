package com.group80.uoftinder.create_account_use_case;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.group80.uoftinder.R;
import com.group80.uoftinder.entities.Constants;
import com.group80.uoftinder.entities.User;
//import com.group80.uoftinder.feed.RecommendationView;
import com.group80.uoftinder.feed.RecommendationView;
import com.group80.uoftinder.firebase.realtime.RealtimeDbWriteListener;
import com.group80.uoftinder.firebase.realtime.UserRealtimeDbFacade;
import com.group80.uoftinder.login_use_case.LoginActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Code for all the views that the user will go through to create a user
 */
public class CreateAccountView extends AppCompatActivity {

    private List<List<Integer>> answers = new ArrayList<>();
    private final UserAccountController control = new UserAccountController();
    private final CreateAccountPresenter proceed = new CreateAccountPresenter();

    private EditText createAccountEmail;
    private EditText createAccountPassword1;
    private EditText createAccountPassword2;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createaccountview);

        mAuth = FirebaseAuth.getInstance();

        createAccountEmail = findViewById(R.id.accountEmail);
        createAccountPassword1 = findViewById(R.id.password1);
        createAccountPassword2 = findViewById(R.id.password2);

        Button buttonShowLoginView = findViewById(R.id.loginButton);
        buttonShowLoginView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoginView(v);
            }
        });

        Button enter = findViewById(R.id.accountEnter);
        enter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                createAccount();

            }
        });
    }
    /**
     * Creates the createaccountview.xml and gets inputs, proceeds to next page if information is
     * entered correctly
     */
    public void createAccount() {
        String email = createAccountEmail.getText().toString().trim();
        String password1 = createAccountPassword1.getText().toString().trim();
        String password2 = createAccountPassword2.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            createAccountEmail.setError("Email is required!");
            createAccountEmail.requestFocus();
        }
        else if (TextUtils.isEmpty(password1)) {
            createAccountPassword1.setError("Password is required!");
            createAccountPassword1.requestFocus();
        }
        else if (TextUtils.isEmpty(password2)) {
            createAccountPassword2.setError("Second password is required!");
            createAccountPassword2.requestFocus();
        }
        else if (!password1.equals(password2)) {
            createAccountPassword2.setError("Passwords do not match!");
            createAccountPassword1.requestFocus();
            createAccountPassword2.requestFocus();
        }
        else {
            mAuth.createUserWithEmailAndPassword(email, password1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(CreateAccountView.this, "User registered Successfully!",
                                Toast.LENGTH_SHORT).show();
                        FirebaseUser firebaseUser = mAuth.getCurrentUser();
                        User currentUser = new User(firebaseUser.getUid());

                        createBasicInfoView(currentUser);
                    }
                    else {
                        Toast.makeText(CreateAccountView.this, "Registration failed :(",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    /**
     * Creates the basicinfoview.xml and gets inputs, proceeds to next page if information is
     * entered correctly
     * @param currentUser   the current user trying to register
     */
    private void createBasicInfoView(User currentUser) {

        setContentView(R.layout.basicinfoview);

        Button enter = findViewById(R.id.cont);
        enter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String userName = ((EditText) findViewById(R.id.name)).getText().toString().trim();
                String userAge = ((EditText) findViewById(R.id.age)).getText().toString();

                ChipGroup identity_group = findViewById(R.id.identityGroup);
                int identity_count = identity_group.getChildCount();
                String identity = "";

                //loops through all chips(answers) of identity answers and finds which chip was
                //selected
                for(int i = 0; i<identity_count; i++) {
                    Chip chip  = (Chip) identity_group.getChildAt(i);
                    if(chip.isChecked()) {
                        identity = chip.getText().toString();
                    }
                }
                //loops through all chips(answers) of type answers and finds which chip was
                //selected
                ChipGroup type_group = findViewById(R.id.typeGroup);
                int type_count = type_group.getChildCount();
                String type = "";
                for(int i = 0; i<type_count; i++) {
                    Chip chip  = (Chip) type_group.getChildAt(i);
                    if(chip.isChecked()) {
                        type = chip.getText().toString();
                    }
                }

                //checks if all information was entered correctly
                boolean move_on = control.newAccount(userName, userAge, identity, type);
                if(move_on) {
                    //sets information for the currentUser
                    currentUser.setName(userName);
                    currentUser.setAge(Integer.parseInt(userAge));
                    currentUser.setGender(identity);
                    currentUser.setUserType(type);

                    createQuestionnaireView(currentUser, type);
                }
                else {
                    String text = "Please enter your information correctly";
                    TextView error = findViewById(R.id.error_basicinfo);
                    error.setText(text);
                }

            }
        });

    }

    /**
     * Creates questionnaire view for the correct type
     * @param currentUser   the current user trying to register
     * @param type      a string representing the type of user that the user selected (only
     *                  "academic" for now)
     */
    private void createQuestionnaireView(User currentUser, String type) {
        if(type.compareTo("Academic")==0) {
            createAcademicQuestionnaire(currentUser);
        }
        else if(type.compareTo("Friendship")==0) {
            createFriendshipQuestionnaire(currentUser);
        }
        else if(type.compareTo("Romantic")==0) {
            createRomanticQuestionnaire(currentUser);
        }
    }

    /**
     * Creates the academic_questionnaire.xml and gets inputs, proceeds to recommendation page if
     * information is entered correctly
     * @param currentUser   the current user trying to register
     */
    private void createAcademicQuestionnaire(User currentUser) {
        setContentView(R.layout.academic_questionnaire);

        Button enter = findViewById(R.id.academic_finish);
        enter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ChipGroup year_group = findViewById(R.id.yeargroup);
                int year_count = year_group.getChildCount();
                int year = -1; //sets year to -1 (user did not select a chip)
                //loops through all chips(answers) of year answers and finds which chip was
                //selected
                for(int i = 0; i<year_count; i++) {
                    Chip chip  = (Chip) year_group.getChildAt(i);
                    if(chip.isChecked()) {
                        year = i;
                    }
                }
                //loops through all chips(answers) of majors answers and finds the chips that were
                //selected
                ChipGroup major_group = findViewById(R.id.majorgroup);
                int major_count = major_group.getChildCount();
                List<Integer> majors = new LinkedList<>();
                for(int i = 0; i<major_count; i++) {
                    Chip chip  = (Chip) major_group.getChildAt(i);
                    if(chip.isChecked()) {
                        majors.add(i);
                    }
                }

                //loops through all chips(answers) of campus answers and finds which chip was
                //selected
                ChipGroup campus_group = findViewById(R.id.campusgroup);
                int campus_count = campus_group.getChildCount();
                int campus = -1;
                for(int i = 0; i<campus_count; i++) {
                    Chip chip  = (Chip) campus_group.getChildAt(i);
                    if(chip.isChecked()) {
                        campus = i;
                    }
                }

                //checks all questions have an answer
                boolean move_on = control.finalAccountAcademic(year, majors, campus);

                if (move_on) {
                    //adds answers user selected to answers
                    answers.add(Collections.singletonList(year));
                    answers.add(majors);
                    answers.add(Collections.singletonList(campus));
                    currentUser.setAnswers((answers));
                    //store User into database
                    UserRealtimeDbFacade.uploadUser(currentUser);

                    Toast.makeText(CreateAccountView.this, "Account created :D",
                            Toast.LENGTH_SHORT).show();

                    //proceed into recommendation view
                    Intent intent  = new Intent(CreateAccountView.this, RecommendationView.class);
                    intent.putExtra(Constants.CURRENT_USER_STRING, currentUser);
                    startActivity(intent);
                    finish();
                }
                else {
                    String text = "Please enter your information correctly";
                    TextView error = findViewById(R.id.error_questionnaire);
                    error.setText(text);
                }
            }
        });
    }

    /**
     * Creates the friendship_questionnaire.xml and gets inputs, proceeds to recommendation page if
     * information is entered correctly
     * @param currentUser   the current user trying to register
     */
    private void createFriendshipQuestionnaire(User currentUser) {
        setContentView(R.layout.friendship_questionnaire);

        Button enter = findViewById(R.id.friend_finish);
        enter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ChipGroup year_group = findViewById(R.id.yeargroup);
                int year_count = year_group.getChildCount();
                int year = -1; //sets year to -1 (user did not select a chip)
                //loops through all chips(answers) of year answers and finds which chip was
                //selected
                for (int i = 0; i < year_count; i++) {
                    Chip chip = (Chip) year_group.getChildAt(i);
                    if (chip.isChecked()) {
                        year = i;
                    }
                }
                //loops through all chips(answers) of majors answers and finds the chips that were
                //selected
                ChipGroup major_group = findViewById(R.id.majorgroup);
                int major_count = major_group.getChildCount();
                List<Integer> majors = new LinkedList<>();
                for (int i = 0; i < major_count; i++) {
                    Chip chip = (Chip) major_group.getChildAt(i);
                    if (chip.isChecked()) {
                        majors.add(i);
                    }
                }

                //loops through all chips(answers) of campus answers and finds which chip was
                //selected
                ChipGroup campus_group = findViewById(R.id.campusgroup);
                int campus_count = campus_group.getChildCount();
                int campus = -1;
                for (int i = 0; i < campus_count; i++) {
                    Chip chip = (Chip) campus_group.getChildAt(i);
                    if (chip.isChecked()) {
                        campus = i;
                    }
                }

                ChipGroup interests_group = findViewById(R.id.interestsgroup);
                int interests_count = interests_group.getChildCount();
                List<Integer> interests = new LinkedList<>();
                for (int i = 0; i < interests_count; i++) {
                    Chip chip = (Chip) interests_group.getChildAt(i);
                    if (chip.isChecked()) {
                        interests.add(i);
                    }
                }

                ChipGroup colour_group = findViewById(R.id.colourgroup);
                int colour_count = colour_group.getChildCount();
                List<Integer> colours = new LinkedList<>();
                for (int i = 0; i < colour_count; i++) {
                    Chip chip = (Chip) colour_group.getChildAt(i);
                    if (chip.isChecked()) {
                        colours.add(i);
                    }
                }

                //checks all questions have an answer
                boolean move_on = control.finalAccountFriendship(year, majors, campus, interests,
                        colours);

                if (move_on) {
                    //adds answers user selected to answers
                    answers.add(Collections.singletonList(year));
                    answers.add(majors);
                    answers.add(Collections.singletonList(campus));
                    answers.add(interests);
                    answers.add(colours);
                    currentUser.setAnswers((answers));
                    //store User into database
                    UserRealtimeDbFacade.uploadUser(currentUser);

                    //proceed into recommendation view
                    Intent intent  = new Intent(CreateAccountView.this, RecommendationView.class);
                    intent.putExtra(Constants.CURRENT_USER_STRING, currentUser);
                    startActivity(intent);
                    finish();
                } else {
                    String text = "Please enter your information correctly";
                    TextView error = findViewById(R.id.error_questionnaire);
                    error.setText(text);
                }
            }
        });
    }

    /**
     * Creates the romantic_questionnaire.xml and gets inputs, proceeds to recommendation page if
     * information is entered correctly
     * @param currentUser   the current user trying to register
     */
    private void createRomanticQuestionnaire(User currentUser) {
        setContentView(R.layout.romantic_questionnaire);

        Button enter = findViewById(R.id.friend_finish);
        enter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ChipGroup sexuality_group = findViewById(R.id.sexualitygroup);
                int sexuality_count = sexuality_group.getChildCount();
                int sexuality = -1;
                for (int i = 0; i < sexuality_count; i++) {
                    Chip chip = (Chip) sexuality_group.getChildAt(i);
                    if (chip.isChecked()) {
                        sexuality = i;
                    }
                }
                //loops through all chips(answers) of majors answers and finds the chips that were
                //selected
                ChipGroup major_group = findViewById(R.id.majorgroup);
                int major_count = major_group.getChildCount();
                List<Integer> majors = new LinkedList<>();
                for (int i = 0; i < major_count; i++) {
                    Chip chip = (Chip) major_group.getChildAt(i);
                    if (chip.isChecked()) {
                        majors.add(i);
                    }
                }

                //loops through all chips(answers) of campus answers and finds which chip was
                //selected
                ChipGroup campus_group = findViewById(R.id.campusgroup);
                int campus_count = campus_group.getChildCount();
                int campus = -1;
                for (int i = 0; i < campus_count; i++) {
                    Chip chip = (Chip) campus_group.getChildAt(i);
                    if (chip.isChecked()) {
                        campus = i;
                    }
                }

                ChipGroup interests_group = findViewById(R.id.interestsgroup);
                int interests_count = interests_group.getChildCount();
                List<Integer> interests = new LinkedList<>();
                for (int i = 0; i < interests_count; i++) {
                    Chip chip = (Chip) interests_group.getChildAt(i);
                    if (chip.isChecked()) {
                        interests.add(i);
                    }
                }

                ChipGroup distance_group = findViewById(R.id.distancegroup);
                int distance_count = distance_group.getChildCount();
                int distance = -1;
                for (int i = 0; i < distance_count; i++) {
                    Chip chip = (Chip) distance_group.getChildAt(i);
                    if (chip.isChecked()) {
                        distance = i;
                    }
                }

                ChipGroup relationship_group = findViewById(R.id.relationshipgroup);
                int relationship_count = relationship_group.getChildCount();
                int relationship = -1;
                for (int i = 0; i < relationship_count; i++) {
                    Chip chip = (Chip) relationship_group.getChildAt(i);
                    if (chip.isChecked()) {
                        relationship = i;
                    }
                }

                //checks all questions have an answer
                boolean move_on = control.finalAccountRomantic(sexuality, majors, campus, interests,
                        distance, relationship);

                if (move_on) {
                    //adds answers user selected to answers
                    answers.add(Collections.singletonList(sexuality));
                    answers.add(majors);
                    answers.add(Collections.singletonList(campus));
                    answers.add(interests);
                    answers.add(Collections.singletonList(distance));
                    answers.add(Collections.singletonList(relationship));
                    currentUser.setAnswers((answers));
                    //store User into database
                    UserRealtimeDbFacade.uploadUser(currentUser);

                    Toast.makeText(CreateAccountView.this, "Account created :D",
                            Toast.LENGTH_SHORT).show();

                    //proceed into recommendation view
                    Intent intent  = new Intent(CreateAccountView.this, RecommendationView.class);
                    intent.putExtra(Constants.CURRENT_USER_STRING, currentUser);
                    startActivity(intent);
                    finish();
                } else {
                    String text = "Please enter your information correctly";
                    TextView error = findViewById(R.id.error_questionnaire);
                    error.setText(text);
                }
            }
        });
    }

    /**
     * Returns view back to loginView
     * @param view      the current view (createAccountView)
     */
    private void showLoginView(View view) {
        Intent intent  = new Intent(CreateAccountView.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}