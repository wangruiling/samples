package org.wrl.samples.mongo.mentity;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: wangrl
 * @Date: 2015-12-01 15:01
 */
public class NameEntity {
    private String username;
    private String nickname;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return "NameEntity{" +
                "username='" + username + '\'' +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}
