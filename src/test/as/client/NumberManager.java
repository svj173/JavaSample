package test.as.client;

public class NumberManager
{
    private static NumberManager ourInstance = new NumberManager();

    public static int num   = 0;

    public static NumberManager getInstance() {
        return ourInstance;
    }

    private NumberManager() {
    }

    public synchronized  int getNumber()
    {
        num++;
        return num;
    }


}
