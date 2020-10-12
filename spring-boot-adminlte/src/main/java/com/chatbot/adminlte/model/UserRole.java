package com.chatbot.adminlte.model;

import javax.persistence.*;

@Entity
@Table(name = "user_role")
public class UserRole  extends AbstractModel<Long>{

    @Column(name = "user_id")
    private Long userId;
    @Column(name = "role_id")
    private Long roleId;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
