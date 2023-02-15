package com.ipl.xpto.trackingTelemetry.controller;

import static com.ipl.xpto.trackingTelemetry.util.Constants.TRACE_ID;
import static com.ipl.xpto.trackingTelemetry.util.Constants.X_TRACE_ID;
import static java.util.Objects.nonNull;

import java.util.HashSet;
import java.util.UUID;

import org.slf4j.MDC;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.ipl.xpto.trackingTelemetry.exception.EntityAlreadyExistsException;
import com.ipl.xpto.trackingTelemetry.exception.EntityNotFoundException;
import com.ipl.xpto.trackingTelemetry.mapper.MapperDtoEntity;
import com.ipl.xpto.trackingTelemetry.model.Sensor;
import com.ipl.xpto.trackingTelemetry.model.TelemetryProfile;
import com.ipl.xpto.trackingTelemetry.openapi.api.TelemetryprofilesApi;
import com.ipl.xpto.trackingTelemetry.openapi.model.CreateTelemetryProfileRequestDto;
import com.ipl.xpto.trackingTelemetry.openapi.model.CreateTelemetryProfileResponseDto;
import com.ipl.xpto.trackingTelemetry.openapi.model.GetTelemetryProfileResponseDto;
import com.ipl.xpto.trackingTelemetry.openapi.model.ListTelemetryProfilesResponseDto;
import com.ipl.xpto.trackingTelemetry.openapi.model.UpdateTelemetryProfileRequestDto;
import com.ipl.xpto.trackingTelemetry.service.ISensorService;
import com.ipl.xpto.trackingTelemetry.service.ITelemetryProfileService;
import com.ipl.xpto.trackingTelemetry.util.Messages;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class TelemetryProfileController implements TelemetryprofilesApi {

	private final ITelemetryProfileService service;
	private final ISensorService sensorService;
	private final MapperDtoEntity mapper;

	@Override
	public ResponseEntity<CreateTelemetryProfileResponseDto> createTelemetryProfile(
			CreateTelemetryProfileRequestDto createTelemetryProfileRequestDto) {

		HttpHeaders headers = new HttpHeaders();

		headers.set(X_TRACE_ID, MDC.get(TRACE_ID));

		TelemetryProfile newEntity = mapper.mapDtoToEntity(createTelemetryProfileRequestDto);
		newEntity.setSensors(new HashSet<>());
		if (nonNull(createTelemetryProfileRequestDto.getSensors())) {
			for (String sensorId : createTelemetryProfileRequestDto.getSensors()) {
				Sensor sensor = sensorService.findById(UUID.fromString(sensorId))
						.orElseThrow(() -> new EntityNotFoundException(String.format(
								Messages.SENSOR_ASSOCIATION_FAILED_SENSOR_NOT_FOUND,
								sensorId)));
				newEntity.getSensors().add(sensor);
			}
		}

		newEntity = service.save(newEntity);

		return new ResponseEntity<>(mapper.mapEntityToCreateResponseDto(newEntity), headers, HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<Void> deleteTelemetryProfile(String telemetryprofileId) {

		HttpHeaders headers = new HttpHeaders();

		headers.set(X_TRACE_ID, MDC.get(TRACE_ID));

		TelemetryProfile persistedSensor = service.findById(UUID.fromString(telemetryprofileId))
				.orElseThrow(() -> new EntityNotFoundException(
						String.format(Messages.TELEMETRY_PROFILE_NOT_FOUND_FOR_ID, telemetryprofileId)));

		service.delete(persistedSensor);

		return ResponseEntity.noContent().headers(headers).build();
	}

	@Override
	public ResponseEntity<GetTelemetryProfileResponseDto> getTelemetryProfile(String telemetryprofileId) {

		HttpHeaders headers = new HttpHeaders();

		headers.set(X_TRACE_ID, MDC.get(TRACE_ID));

		TelemetryProfile persistedEntity = service.findById(UUID.fromString(telemetryprofileId))
				.orElseThrow(() -> new EntityNotFoundException(
						String.format(Messages.TELEMETRY_PROFILE_NOT_FOUND_FOR_ID, telemetryprofileId)));

		return ResponseEntity.ok().headers(headers).body(mapper.mapEntityToDto(persistedEntity));
	}

	@Override
	public ResponseEntity<ListTelemetryProfilesResponseDto> listTelemetryProfiles() {

		HttpHeaders headers = new HttpHeaders();

		headers.set(X_TRACE_ID, MDC.get(TRACE_ID));

		ListTelemetryProfilesResponseDto responseDto = mapper
				.convertTelemetryProfileCollectionToListDTO(service.findAll());

		return ResponseEntity.ok().headers(headers).body(responseDto);

	}

	@Override
	public ResponseEntity<Void> updateTelemetryProfile(String telemetryprofileId,
			UpdateTelemetryProfileRequestDto updateTelemetryProfileRequestDto) {

		HttpHeaders headers = new HttpHeaders();

		headers.set(X_TRACE_ID, MDC.get(TRACE_ID));

		TelemetryProfile persistedEntity = service.findById(UUID.fromString(telemetryprofileId))
				.orElseThrow(() -> new EntityNotFoundException(
						String.format(Messages.TELEMETRY_PROFILE_NOT_FOUND_FOR_ID, telemetryprofileId)));

		TelemetryProfile newEntity = mapper.mapDtoToEntity(updateTelemetryProfileRequestDto);

		persistedEntity.setName(newEntity.getName());
		persistedEntity.getSensors().clear();
		newEntity.getSensors().forEach(sensorId -> {
			Sensor sensor = sensorService.findById(sensorId)
					.orElseThrow(() -> new EntityNotFoundException(String.format(
							Messages.SENSOR_ASSOCIATION_FAILED_SENSOR_NOT_FOUND,
							sensorId)));
			persistedEntity.getSensors().add(sensor);
		});

		service.update(persistedEntity);

		return ResponseEntity.noContent().headers(headers).build();
	}
	
	@Override
	public ResponseEntity<Void> addSensorToTelemetryProfile(String telemetryprofileId, String sensorId) {
		
		HttpHeaders headers = new HttpHeaders();

		headers.set(X_TRACE_ID, MDC.get(TRACE_ID));

		TelemetryProfile persistedEntity = service.findById(UUID.fromString(telemetryprofileId))
				.orElseThrow(() -> new EntityNotFoundException(
						String.format(Messages.TELEMETRY_PROFILE_NOT_FOUND_FOR_ID, telemetryprofileId)));
		
		Sensor sensor = sensorService.findById(UUID.fromString(sensorId))
				.orElseThrow(() -> new EntityNotFoundException(String.format(
						Messages.SENSOR_ASSOCIATION_FAILED_SENSOR_NOT_FOUND,
						sensorId)));

		if(persistedEntity.getSensors().contains(sensor))
			throw new EntityAlreadyExistsException(String.format(
					"Sensor already exists on informed Telemtry Profile. Sensor ID: %s",
					sensorId));
			
		persistedEntity.getSensors().add(sensor);
		
		service.update(persistedEntity);

		return ResponseEntity.noContent().headers(headers).build();
	}
	
	@Override
	public ResponseEntity<Void> removeSensorFromTelemetryProfile(String telemetryprofileId, String sensorId) {
		
		HttpHeaders headers = new HttpHeaders();

		headers.set(X_TRACE_ID, MDC.get(TRACE_ID));

		TelemetryProfile persistedEntity = service.findById(UUID.fromString(telemetryprofileId))
				.orElseThrow(() -> new EntityNotFoundException(
						String.format(Messages.TELEMETRY_PROFILE_NOT_FOUND_FOR_ID, telemetryprofileId)));
		
		Sensor sensor = sensorService.findById(UUID.fromString(sensorId))
				.orElseThrow(() -> new EntityNotFoundException(String.format(
						Messages.SENSOR_ASSOCIATION_FAILED_SENSOR_NOT_FOUND,
						sensorId)));

		if(!persistedEntity.getSensors().contains(sensor))
			throw new EntityNotFoundException(String.format(
					"Sensor already exists on informed Telemtry Profile. Sensor ID: %s",
					sensorId));
			
		persistedEntity.getSensors().remove(sensor);
		
		service.update(persistedEntity);

		return ResponseEntity.noContent().headers(headers).build();
	}
}
