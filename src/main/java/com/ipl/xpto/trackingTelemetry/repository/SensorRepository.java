package com.ipl.xpto.trackingTelemetry.repository;

import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.ipl.xpto.trackingTelemetry.model.Sensor;

@Repository
public interface SensorRepository extends IBaseRepository<Sensor, UUID>{
  

}
