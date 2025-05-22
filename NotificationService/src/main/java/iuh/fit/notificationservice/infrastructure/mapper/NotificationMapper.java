package iuh.fit.notificationservice.infrastructure.mapper;

import iuh.fit.notificationservice.application.dto.request.notification.NotificationRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface NotificationMapper {
    NotificationMapper INSTANCE = Mappers.getMapper(NotificationMapper.class);

    @Mapping(target = "data",expression = "java(toJSON(user))")
    NotificationRequest mapRequestToEntity();
}
