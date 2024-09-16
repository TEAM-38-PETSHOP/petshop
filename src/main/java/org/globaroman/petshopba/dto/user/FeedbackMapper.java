package org.globaroman.petshopba.dto.user;

import org.globaroman.petshopba.controller.UserFeedbackDto;
import org.globaroman.petshopba.model.user.Feedback;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING)
public interface FeedbackMapper {
    Feedback toEntity(UserFeedbackDto userFeedbackDto);

    UserFeedbackDto toDto(Feedback feedback);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Feedback partialUpdate(UserFeedbackDto userFeedbackDto, @MappingTarget Feedback feedback);
}
