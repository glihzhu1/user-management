package com.all.management.user.model;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AlluserRepository extends JpaSpecificationExecutor<Alluser>, JpaRepository<Alluser, Integer> {

	public List<Alluser> findByLoginIdIgnoreCase(String LoginId);
	public List<Alluser> findByEmailIgnoreCase(String email);
	public List<Alluser> findByLoginIdAndEmailIgnoreCase(String LoginId, String email);
	public List<Alluser> findByLoginIdOrEmailIgnoreCase(String LoginId, String email);
	
}
