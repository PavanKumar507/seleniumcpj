package HomePage;

import HomePage.Commands.LoginCommand;

public class HomePage {

    public static void GoTo() {
        new HomePageImpl().GoTo();
    }
    public static LoginCommand Login(){
        return new LoginCommand();
    }

}
