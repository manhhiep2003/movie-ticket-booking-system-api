package com.sailing.moviebooking.mapper;

import com.sailing.moviebooking.dto.request.PermissionRequest;
import com.sailing.moviebooking.dto.response.PermissionResponse;
import com.sailing.moviebooking.model.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest permissionRequest);
    PermissionResponse toPermissionResponse(Permission permission);
}
