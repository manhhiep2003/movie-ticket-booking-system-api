package com.sailing.moviebooking.mapper;

import com.sailing.moviebooking.dto.request.RoleRequest;
import com.sailing.moviebooking.dto.response.RoleResponse;
import com.sailing.moviebooking.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest roleRequest);
    RoleResponse toRoleResponse(Role role);
}
