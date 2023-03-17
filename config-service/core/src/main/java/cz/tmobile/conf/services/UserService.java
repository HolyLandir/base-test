package cz.tmobile.conf.services;

import java.util.Collections;
import java.util.List;

import javax.ws.rs.core.Response;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cz.tmobile.conf.api.IUserService;
import cz.tmobile.conf.api.data.UserInfo;
import cz.tmobile.conf.persistence.mappers.UserMapper;

@Component
public class UserService implements IUserService {
	private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private UserMapper mapper;

	public UserService() {
		super();
	}

	@Override
	@Transactional
	public Response listValues() {
		LOG.trace("List of users requested");
		List<UserInfo> values = mapper.listUsers();
		if (CollectionUtils.isEmpty(values)) {
			LOG.trace("There are no users");
			return Response.ok(Collections.emptyList()).build();
		}
		LOG.trace("There are {} users", values.size());
		return Response.ok(values).build();
	}

}
