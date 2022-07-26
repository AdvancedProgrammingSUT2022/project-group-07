package game.Common.Model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;

public class User {
    private String username;
    private String password;
    private String nickname;
    private int score;
    private boolean isLoggedIn;
    private int avatarNumber;
    private String avatarFilePath;
    byte[] byteImageArray ;
    private HashSet<String> friends = new HashSet<>() ;

    public User(String username, String password, String nickname) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.score = 0;
        this.isLoggedIn = false;
        this.avatarNumber = (int)Math.floor(Math.random()*10);
        this.avatarFilePath = null;
    }
    public User(){

    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }

    public int getAvatarNumber() {
        return avatarNumber;
    }

    public void setAvatarNumber(int avatarNumber) {
        this.avatarNumber = avatarNumber;
    }

    public String getAvatarFilePath() {
        return avatarFilePath;
    }

    public void setAvatarFilePath(String avatarFilePath) {
        this.avatarFilePath = avatarFilePath;
    }

    public void setByteImageArray(byte[] byteImageArray) {
        this.byteImageArray = byteImageArray;
    }

    public byte[] getByteImageArray() {
        return byteImageArray;
    }

    public void addFriend (String username){
        this.friends.add(username);
    }

    public HashSet<String> getFriends() {
        return friends;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}
