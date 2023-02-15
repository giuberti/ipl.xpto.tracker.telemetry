package com.ipl.xpto.trackingTelemetry.service.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ipl.xpto.trackingTelemetry.model.Sensor;
import com.ipl.xpto.trackingTelemetry.repository.SensorRepository;
import com.ipl.xpto.trackingTelemetry.service.ISensorService;

@Service
public class SensorService extends BaseEntityService<UUID, Sensor, SensorRepository> implements ISensorService {

	public SensorService(SensorRepository repository) {
		super(repository);
	}
	
	public static Set<Sensor> convertSetIdsToListSensors(Collection<String> listIds) {

		Set<Sensor> sensors = new HashSet<>();

		listIds.forEach(id->{
			Sensor sensor = new Sensor();
			sensor.setId(UUID.fromString(id));
			sensors.add(sensor);
		});

	    return sensors;
	  }

}
