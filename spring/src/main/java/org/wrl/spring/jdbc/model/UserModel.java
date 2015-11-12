package org.wrl.spring.jdbc.model;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: wangrl
 * @Date: 2015-11-12 15:33
 */
public class UserModel {
    private int id;
    private String myName;

    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }
    public void setMyName(String myName) {
        this.myName = myName;
    }
    public String getMyName() {
        return myName;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        UserModel other = (UserModel) obj;
        if (id != other.id)
            return false;
        return true;
    }
}
