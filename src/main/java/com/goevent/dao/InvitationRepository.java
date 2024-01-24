package com.GoEvent.dao;

import com.GoEvent.model.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvitationRepository extends JpaRepository<Invitation, Long> {
}
