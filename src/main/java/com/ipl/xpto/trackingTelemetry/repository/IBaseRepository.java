package com.ipl.xpto.trackingTelemetry.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.ipl.xpto.trackingTelemetry.model.IEntity; 

@NoRepositoryBean
public interface IBaseRepository<ENTITY extends IEntity<?>, ID extends Serializable> extends JpaRepository<ENTITY, ID>   {

}
