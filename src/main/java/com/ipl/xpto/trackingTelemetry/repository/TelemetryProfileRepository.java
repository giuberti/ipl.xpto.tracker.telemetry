package com.ipl.xpto.trackingTelemetry.repository;

import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.ipl.xpto.trackingTelemetry.model.TelemetryProfile;

@Repository
public interface TelemetryProfileRepository extends IBaseRepository<TelemetryProfile, UUID>{
  

}
