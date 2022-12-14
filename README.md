# Overview and Inspiration 

Introducing UofTinder, a social networking Android application built by a group of UofT students.
Inspired by Tinder, we wanted to create an application with more options in terms of the type of relationships people may want to build with others. 

The program offers users the opportunity to either look for their significant other, friends, or academic partners. 
Then, a compatibility algorithm analyzes certain profile metrics (e.g., hobbies and interests, sexual orientation, academic focuses) to recommend potential matches to people based on their calculated compatibility. 
If two people match with each other through this selection process, they proceed to a chat feature where they have the opportunity to talk and get to know each other more.

Some design patterns that we implemented within our project include Model View Presenter (MVP), Façade, Observer, and Dependency Injection.

# Installation/Execution Instructions
1. Download and install Android studio (https://developer.android.com/studio). Select Android Virtual Device.
2. On first launch, select 'do not import settings' and 'standard installation type' when prompted. Accept both android-sdk-license and android-sdk-preview- license.
3. Navigate to the folder you would like to store the code in a terminal that recognizes git. Type the following command:
    - `git clone https://github.com/CSC207-2022F-UofT/course-project-group-80.git`
    - Optionally, type: `git clone https://github.com/CSC207-2022F-UofT/course-project-group-80.git ./UofTinder`
      - Note: the ./UofTinder part tells git to rename the cloned folder to UofTinder
4. Navigate to the folder and convert to the following branch
```
  cd ./UofTinder
  git checkout main
```
5. Launch Android Studio, and select Open. Navigate to and select the folder where the repo is cloned locally.
6. Once the project is opened, gradle should start syncing the files. Wait until it is done.
7. In the menu bar, Android studio should already selected the app configuration and Pixel_3a_API_33_x86_64 for device (OK to select different device, as long as it's a cellphone and API is not lower than 23). Click the hammer to the left of app to build the project.
8. Open the Device Manager tab to the right of the screen, find your virtual device, and click the triangle in the Actions tab to launch the device.
9. The app should now be launched. Please watch our demo video (https://youtu.be/EKVf1XHtWYk) to see an overview of the app's functionalities and/or continue reading for a written summary.

# How to Run the App
1. If you already have an account, you can enter in your email and password in login [(1:15)](https://youtu.be/EKVf1XHtWYk?t=75). Skip steps 2-5.
2. Else, if you don't have an account, click sign up [(0:06)](https://youtu.be/EKVf1XHtWYk?t=6). 
3. Once you create an account, you will be taken to a page to enter your email and password, as well as a basic information page [(0:30)](https://youtu.be/EKVf1XHtWYk?t=30). This page will prompt you to select one of three users (Academic, Friendship, or Romantic). In the video demo, we selected Academic.
4. Next, based on the User type you selected previously (Academic, Friendship, or Romantic), you will be shown a tailored questionnaire to answer. This is used so that a list of compatible users can be generated. In the video, we are shown the Academic Questionnaire [(0:45)](https://youtu.be/EKVf1XHtWYk?t=45).
5. Once you filled out the questionnaire, you can optionally upload an image as your profile picture [(0:57)](https://youtu.be/EKVf1XHtWYk?t=57).
6. Next, the Recommendation Feed is shown, where you can click 'yes' or 'no' on the profiles that are displayed [(1:38)](https://youtu.be/EKVf1XHtWYk?t=98). Whichever option you decide to click will determine if a match is created.
7. If a match is created, a small pop-up at the bottom will appear [(1:54)](https://youtu.be/EKVf1XHtWYk?t=114).
8. You can enter the chat function at the top right button of the Recommendation Feed [(1:57)](https://youtu.be/EKVf1XHtWYk?t=117). If you have any matches, they will appear in the list and you can send them messages [(2:02)](https://youtu.be/EKVf1XHtWYk?t=122).
9. If you want to filter your matches by age, program of study, year of study, or campus, you can click the middle top button of the Recommendation Feed to select filters [(2:13)](https://youtu.be/EKVf1XHtWYk?t=133). This will filter out your generated compatible matches by the criteria selected.
10. Once you run out of compatible matches, a screen will appear saying that there are no new recommendations [(2:27)](https://youtu.be/EKVf1XHtWYk?t=147).
11. There is also the option to logout of your profile at the top left button of Recommendation View [(1:07)](https://youtu.be/EKVf1XHtWYk?t=67).

# Core Functionalities
**1. User login**
- Login requires an email and password that have been already registered in the database to a ```User``` object
- Incorrect/absent information will trigger failure views that indicate that fields are missing
- Correct information will trigger a success view, and will switch the view from Login to the home feed

**2. Create new account**
- User can choose between three different types of users when registering: Romantic, Academic, and Friendship
- An email and password are required to identify each user
- Password needs to be at least 6 characters long

**3. Profile setup/questionnaire**
- The user cannot continue before answering profile setup questions
- Questionnaire answers for each user are later used in generating a recommendation feed for the current user

**4. Generate recommendation feed**
- Based on the user responses to the mandatory questionnaire, a user score is computed for each user
- We retrieve all users from the database and create an ordered compatibility list based on computed similarity scores

**5. Display recommendation feed**
- Each user in the generated compatibility list is displayed on the home feed, including their profile picture and relevant information
- The current user is able to select "Yes" or "No" on the displayed user, indicating whether they would like to match or not

**6. Filter recommendation feed**
- We are able to filter the compatibility list based on answers to any questions on the questionnaire
- Filter options vary by user type, and applying the filter alters the recommendation feed shown

**7. Creating matches**
- Every time the user likes someone displayed on the recommendation feed, we check to see if a match can be created between the two
- If they like each other, we open a chat between the two users and display a success view to indicate that a match has been created

**8. Chat feature**
- Pressing on the chat tab opens up a contact list including all of the people that the current user has matched with
- Users are able to chat with each other through a messaging interface, which is available as soon as they have matched

**9. User persistence**
- As soon as the user has registered their account and answered the questionnaire, a ```User``` object containing their information is pushed to Firebase
- Whenever the current user's internal data is updated as they use the app, we synchronize these changes with the database.

# Unit Tests Implemented for Functionalities
## In the `test` folder
**Filter Recommendation Feed** 
- filterFeedTestFilters: tests that we can apply filters to the feed.
- filterFeedTestAge: tests that we can filter by age.
- filterFeedTestNoFilters: tests that we can see the fee without any filters.
- filterFeedTestFiltersAndAge: test that we can filter by age and other filters.

**User Score Facade**
- generateCompatibilityScoreTest1: tests to see whether the correct compatibility gets generated for current user (User 1).
- generateCompatibilityScoreTest2: tests to see whether the correct compatibility gets generated for current user (User 2).
- generateCompatibilityScoreTest3: tests to see whether the correct compatibility gets generated for current user (User 3).
- compareTest1: tests to see compare method returns the correct similarity scores for two users (User 1 and User 2).
- compareTest2: tests to see compare method returns the correct similarity scores for two users (User 3 and User 2).

**User Entity Methods**
- infoStringTestAcademicUser: tests to get the correct display information string for an academic user.
- infoStringTestRomanticUser: tests to get the correct display information string for a romantic user. Also tests the case of not providing answers for several questions.
- infoStringTestFriendshipUser: tests for getting the correct display information string for a friendship user. Also tests the case of not providing an answer for a question between two questions with answers provided.

**Create Account**
- tests to ensure that the current user trying to create a new account enters information to answer all questions on the provided questionnaires.

## In the `androidTest` folder
### Backend specific tests
**Recommendation Interactor**
- orderCompatibilityListTest1: tests that RecommendationInteractor.orderCompatibilityList reorders the compatibility list from most compatible user to least compatible user when there are 3 total users.
- orderCompatibilityListTest2: tests that RecommendationInteractor.orderCompatibilityListTest2 does nothing to the compatibility list when there are no other users in the database (besides the current user).
- showMostCompUser2UsersTest: tests that RecommendationInteractor.showMostCompUser returns the most compatible user when there are 2 other users.
- showMostCompUserNoUsersTest: tests that RecommendationInteractor.showMostCompUser returns null when there are no other compatible users
- removeMostCompUserTest: tests that RecommendationInteractor.removeMostCompUser removes the most compatible user in the compatibility list.
- removeCurrentUserTest:  tests that RecommendationInteractor.removeCurrentUser removes the current user from the current user's compatibility list.
- removeVisitedUsersTest: tests that RecommendationInteractor.removeVisitedUsers removes the visited users from the current user's compatibility list.

**Match Interactor** 
- checkMatchListsUpdatedLocal: test to see if the match lists for two users are both updated in the local User classes.
- checkMatchListsUpdatedRemote: test to see if the match lists for two users are both updated in the database upon match.
- currUserSkipsDisplayedUserLocal: test to see if the local User viewed and liked lists are updated when the currentUser does not 'like' the displayedUser.
- currUserLikesDisplayedUserLocal: test to see if the local User viewed and liked lists are updated when the currentUser 'likes' the displayedUser.
- currUserSkipsDisplayedUserRemote: test to see if the viewed and liked lists of the current user are updated in the database when the currentUser does not 'like' the displayedUser.
- currUserLikesDisplayedUserRemote: test to see if the viewed and liked lists of the current user are updated in the database when the currentUser does not 'like' the displayedUser.

**Realtime Database**
- uploadUser_isCorrect: test if the upload task can be done without error.
- getAllUsers_isCorrect: test if we can get all users without error.
- getUser_isCorrect: test that we can get the correct user without error.

**Login Interactor**
- tests to verify appropriate and correct inputs for email and password fields of the login user story

**Chatting**
- tests to ensure that users can send and receive chat messages from each other.

### Android User Interface specific tests
**Create Account Activity**
- testChangeToLogin: checks if view switched activity_login.xml correctly
- testNoEmail: check if no email, no passwords are input that createActViewEmailEdtTxt has focus
- testNoPasswords: check if with email, no passwords are input that createActViewEmailEdtTxt has focus
- testNoFirstPassword: check if the first password is not input, but re-enter password has input that createActViewPasswordEdtTxt has focus
- testNoReEnterPassword: check if with email, and only one password are input that createActViewReEnterPasswordEdtTxt has focus
- testPasswordsNotMatching: check if with email and two passwords are input, but the passwords do not match, createActViewReEnterPasswordEdtTxt has focus
- testCreateAccountFailure: check if with email and two passwords are input, and the passwords do match but the user already exists, no user is created
- testCreateAccountSuccess: check if with email and two passwords are input, and the passwords match, and the user does not exist in the database, a user is created 
    - Created user is deleted afterwards

**Login Activity**
- testChangeToCreateAccount: checks if view switched activity_create_account.xml correctly
- testNoEmailNoPassword: check if no email, no password is input that loginEmail has focus
- testNoEmailWithPassword: check if no email, with password is input that loginEmail has focus
- testWithEmailNoPassword: check if with email, no password is input that loginPassword has focus
- testIncorrectEmailPasswordLogin: check if with incorrect email and password combination that current user is null
- testCorrectEmailPasswordLogin: check if with correct email and password combination that current user is not null

**Logout**
- logoutTest: tests that the logout button works in Recommendation View

**Filter Activity**
- testSwitchToFilterPage: test that the program switches to the filter page after clicking on the 'Edit Filters' button
- testResetChangeToRecommendation: test that the program switches to the recommendation view after clicking the 'reset' button
- testResetFilters: test that the program 'reset' button properly deselects all options
- testFilterButtons: test that all the program's filter check boxes can be selected
- testApplyFiltersChangeView: test that the program switches to the recommendation view after clicking the 'Filter' button on the filter page
- testApplyFiltersStay: test that the keeps the selected filters after clicking the 'Filter' button and switching to Recommendation view page.
- testDeselect: test that the program's filter check boxes can be deselected
- testTwoSelectOneDeselect: test that the program's filter check boxes can be deselected and other selected boxes will not be affected

**Chat Activity**
- testSwitchContactPage: tests that the program switches to the contact page after clicking the 'Enter Chat' button on the filter page and that the right contacts are displayed.
- testBackButton: tests that the program switches back to the recommendation view after clicking the back button in the contact view.

# Next steps for UofTinder
Given more time, we would create prettier layout for viewing other user profiles on the recommendation feed and also add filter layouts for romantic and friendship users.
We would also add the ability to view and edit one's own profile.
