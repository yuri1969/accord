package org.neociclo.accord.odetteftp.camel;

import org.apache.camel.Consumer;
import org.apache.camel.Endpoint;
import org.apache.camel.spi.PollingConsumerPollStrategy;
import org.neociclo.odetteftp.protocol.EndSessionException;

public class DefaultOdettePollingStrategy implements PollingConsumerPollStrategy {

	public boolean begin(Consumer consumer, Endpoint endpoint) {
		OdetteEndpoint odette = (OdetteEndpoint) endpoint;

		// is session working? if true, skip polling this time
		if (odette.getOdetteOperations().isConnected()) {
			return false;
		}

		// let listener be aware of polling
		OdetteAuditListener listener = odette.getConfiguration().getListener();
		if (listener != null) {
			listener.sessionStarted();
		}

		return true;
	}

	public void commit(Consumer consumer, Endpoint endpoint) {
		OdetteEndpoint odette = (OdetteEndpoint) endpoint;

		// let listener be aware of polling
		OdetteAuditListener listener = odette.getConfiguration().getListener();
		if (listener != null) {
			listener.sessionEnded();
		}

		try {
			odette.getOdetteOperations().disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean rollback(Consumer consumer, Endpoint endpoint, int retryCounter, Exception cause) throws Exception {

		// only retry under theses error codes: 08, 09, 99
		if (cause instanceof EndSessionException) {
			EndSessionException ese = (EndSessionException) cause;

			switch (ese.getReason()) {
			case UNSPECIFIED_ABORT:
			case TIME_OUT:
			case RESOURCES_NOT_AVAIABLE:
				if (retryCounter == OdetteConfiguration.DEFAULT_RETRY_COUNT)
					return true;
			default:
				return false;
			}
		}

		return false;
	}

}