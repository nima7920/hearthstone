import org.json.simple.*;
import java.io.*;
public class Player {
    // defining basic fields:
    private String name,password;
    private int gems;
    // defining getters and setters:
// Name:
    public void setName(String s) {
        this.name=s;
    }
    public String getName() {
        return this.name;
    }
    // Password
    public void setPassword(String s) {
        this.password=s;
    }
    public String getPassword() {
        return this.password;
    }
    // Gems:
    public void setGems(int g) {
        this.gems=g;
    }
    public int getGems(int g) {
        return this.gems;
    }
public void createProfile() throws IOException{

}
// isProfileExist checks whether a profile with a given name exists:
public boolean isProfileExist(String Name){

        return false;
}
public void loginProfile(String Name,String Password){

}
public void updateProfile(){

}
}
