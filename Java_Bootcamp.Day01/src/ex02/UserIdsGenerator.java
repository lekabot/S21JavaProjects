package ex02;

public class UserIdsGenerator {
    private static UserIdsGenerator instance;
    private int lastGeneratedId = 0;
    private UserIdsGenerator() {}

    public static UserIdsGenerator getInstance() {
        if (instance == null) {
            instance = new UserIdsGenerator();
        }
        return instance;
    }
    public int generateId() {
        return ++lastGeneratedId;
    }
}
