package com.backend.mediConnect.dto;

import com.backend.mediConnect.entity.RoleName;
import jakarta.validation.constraints.NotNull;

public class UpdateRoleDto {
    @NotNull
    private Long roleId;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
