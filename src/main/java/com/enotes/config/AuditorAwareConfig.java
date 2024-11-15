package com.enotes.config;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;

public class AuditorAwareConfig implements AuditorAware<Integer> {

	@Override
	public Optional<Integer> getCurrentAuditor() {
		return Optional.of(1);
	}

}
