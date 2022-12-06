package com.group80.uoftinder.logout;

public class LogOutPresenter {
    private final LogOutViewInterface logOutViewInterface;
    private LogOutInteractor logOutInteractor;

    public LogOutPresenter(LogOutViewInterface logOutViewInterface) {
        this.logOutViewInterface = logOutViewInterface;
    }

    public void setLogOutInteractor(LogOutInteractor logOutInteractor) {
        this.logOutInteractor = logOutInteractor;
    }

    /**
     * Calls interactor's signOut() method to log out the user
     */
    public void signOut() {
        logOutInteractor.signOut();
    }

    /**
     * Show the Login screen
     */
    public void showLogin() {
        logOutViewInterface.showLogin();
    }
}
