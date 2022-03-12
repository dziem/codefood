package com.akmal.codefood.repository;

import com.akmal.codefood.entity.Serve;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ServeRepository extends OptionalHandlingRepository<Serve, String>, JpaSpecificationExecutor<Serve> {
}
