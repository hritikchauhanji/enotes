package com.enotes.config;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;

import com.enotes.entity.User;
import com.enotes.util.CommonUtil;

public class AuditorAwareConfig implements AuditorAware<Integer> {

	@Override
	public Optional<Integer> getCurrentAuditor() {
		User loggedInUser = CommonUtil.getLoggedInUser();
		return Optional.of(loggedInUser.getId());
	}

}
