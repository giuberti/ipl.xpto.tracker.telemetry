package com.ipl.xpto.trackingTelemetry.service.impl;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ipl.xpto.trackingTelemetry.model.TelemetryProfile;
import com.ipl.xpto.trackingTelemetry.repository.TelemetryProfileRepository;
import com.ipl.xpto.trackingTelemetry.service.ITelemetryProfileService;

@Service
public class TelemetryProfileService extends BaseEntityService<UUID, TelemetryProfile, TelemetryProfileRepository>
    implements ITelemetryProfileService {

  public TelemetryProfileService(TelemetryProfileRepository repository) {
		super(repository);
  }

}
