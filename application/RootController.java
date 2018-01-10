package application;

import users.UsersDAO;
// import users.UserCtrl;

public class RootController
{

    private AbstractDAO dao;
    private RootView view;
    // private UserCtrl loggedUser;
    public RootController()
    {
        dao = new UsersDAO();
        view = new RootView();
    }

    public void runApplication()
    {
        view.displayIntro();
        view.displayLoginScreen();
        view.displayLogoutScreen();
        view.displayOutro();
    }
}
