package CP;


public abstract class BasePageImpl {
    public abstract String Url();

    public boolean IsAt() {
        return Driver.Instance.getCurrentUrl().equals(Url());
    }

    public void GoTo()
    {

        if (!IsAt())
        {
            Driver.GoToUrl(Url());
        }
        else
        {
            Driver.Refresh();
        }

    }
}
