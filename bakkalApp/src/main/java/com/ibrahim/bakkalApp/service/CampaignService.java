package com.ibrahim.bakkalApp.service;

import com.ibrahim.bakkalApp.entity.Campaign;
import com.ibrahim.bakkalApp.repository.CampaignRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CampaignService {

    private final CampaignRepository campaignRepository;

    public CampaignService(CampaignRepository campaignRepository) {
        this.campaignRepository = campaignRepository;
    }

    public List<Campaign> findAll() {
        return campaignRepository.findAll();
    }

    public Campaign save(Campaign campaign) {
        return campaignRepository.save(campaign);
    }

    public void deleteById(Long id) {
        campaignRepository.deleteById(id);
    }
}