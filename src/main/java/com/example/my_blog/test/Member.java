package com.example.my_blog.test;


import lombok.AllArgsConstructor; // 생성자를 만들어줌
import lombok.Data; //  getter/setter 를 생성해줌
import lombok.NoArgsConstructor; //  빈생성자를 만들어줌

@Data
@AllArgsConstructor // 전체 생성자
@NoArgsConstructor // 빈 생성자
// 위 어노테이션 모두 Lombok 라이브러리임
public class Member {
    private int id;
    private String username;
    private String password;
    private String email;
}






/* Lombok 라이브러리의 어노테이션(@Data, @AllArgsConstructor 등 덕분에
   따로 getter, setter 메서드를 만들고 생성자를 만들 필요 없음
    public Member(int id, String username, String password, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public int getId() {
        return id;
      }

    public void setId(int id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
*/

