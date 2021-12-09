package spd.trello.domain;

public class Main {
    public static void main(String[] args) {
        User testUser = new User();
        testUser.setFirstName("Selim");
        testUser.setLastName("Bekberov");
        testUser.setEmail("s.bekberov@gmail.com");
        System.out.println(testUser.getFirstName()+" "+testUser.getLastName()+" "+testUser.getEmail());
    }
}
