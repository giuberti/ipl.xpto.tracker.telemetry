package com.ipl.xpto.trackingTelemetry.model;

import java.io.Serializable;

public interface IEntity<T extends Serializable> {
  
  public T getId();

}
