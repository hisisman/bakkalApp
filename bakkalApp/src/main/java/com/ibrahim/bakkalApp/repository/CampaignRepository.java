package com.ibrahim.bakkalApp.repository;

import com.ibrahim.bakkalApp.entity.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampaignRepository extends JpaRepository<Campaign, Long> {
}