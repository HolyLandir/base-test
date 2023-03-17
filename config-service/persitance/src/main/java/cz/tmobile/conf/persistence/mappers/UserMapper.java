package cz.tmobile.conf.persistence.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cz.tmobile.conf.api.data.User;
import cz.tmobile.conf.api.data.UserInfo;

public interface UserMapper {
	
	public List<UserInfo> listUsers();

	public User getUser(@Param("personalId") long personalId);
}
