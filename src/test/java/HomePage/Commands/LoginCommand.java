package HomePage.Commands;

import CP.CommandBase;
import HomePage.HomePageImpl;

public class LoginCommand extends CommandBase {

    private String _userName;
    private String _password;
    public LoginCommand WithUserName(String userName)
    {
        _userName = userName;
        return this;
    }

    public LoginCommand WithPassword(String password)
    {
        _password = password;
        return this;
    }

     protected void ExecuteUsingUi(){
        HomePageImpl homePage = new HomePageImpl();
            homePage.login(_userName,_password);
    }
}
