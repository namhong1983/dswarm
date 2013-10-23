package de.avgl.dmp.controller.eventbus;

import java.util.List;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

import de.avgl.dmp.converter.DMPConverterException;
import de.avgl.dmp.converter.flow.CSVResourceFlowFactory;
import de.avgl.dmp.converter.flow.CSVSourceResourceTriplesFlow;
import de.avgl.dmp.persistence.DMPPersistenceException;
import de.avgl.dmp.persistence.model.internal.impl.MemoryDBInputModel;
import de.avgl.dmp.persistence.model.resource.Configuration;
import de.avgl.dmp.persistence.model.resource.Resource;
import de.avgl.dmp.persistence.services.InternalService;

@Singleton
public class CSVConverterEventRecorder {

	private final InternalService	internalService;

	@Inject
	public CSVConverterEventRecorder(@Named("MemoryDb") final InternalService internalService, final EventBus eventBus) {

		this.internalService = internalService;
		eventBus.register(this);
	}

	@Subscribe
	public void convertConfiguration(final CSVConverterEvent event) {
		final Configuration configuration = event.getConfiguration();
		final Resource resource = event.getResource();

		List<org.culturegraph.mf.types.Triple> result = null;
		try {
			final CSVSourceResourceTriplesFlow flow = CSVResourceFlowFactory.fromConfiguration(configuration, CSVSourceResourceTriplesFlow.class);

			final String path = resource.getAttribute("path").asText();
			result = flow.applyFile(path);

		} catch (DMPConverterException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}

		if (result != null) {
			for (final org.culturegraph.mf.types.Triple triple : result) {

				final MemoryDBInputModel mdbim = new MemoryDBInputModel(triple);

				try {

					internalService.createObject(resource.getId(), configuration.getId(), mdbim);
				} catch (DMPPersistenceException e) {

					e.printStackTrace();
				}
			}
		}
	}
}