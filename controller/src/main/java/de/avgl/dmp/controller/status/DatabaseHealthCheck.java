package de.avgl.dmp.controller.status;


import com.codahale.metrics.health.HealthCheck;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import de.avgl.dmp.persistence.DatabaseConnectionCheck;

@Singleton
public class DatabaseHealthCheck extends HealthCheck {
	private final DatabaseConnectionCheck database;

	@Inject
	public DatabaseHealthCheck(final DatabaseConnectionCheck database) {
		this.database = database;
	}

	@Override
	public HealthCheck.Result check() throws Exception {
		if (database.isConnected()) {
			return HealthCheck.Result.healthy();
		} else {
			return HealthCheck.Result.unhealthy("Cannot connect to the database at " + database.getUrl());
		}
	}
}