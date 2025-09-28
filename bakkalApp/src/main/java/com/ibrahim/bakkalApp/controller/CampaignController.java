package com.ibrahim.bakkalApp.controller;

import com.ibrahim.bakkalApp.repository.CampaignRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/campaigns")
public class CampaignController {

    private final CampaignRepository campaignRepository;

    public CampaignController(CampaignRepository campaignRepository) {
        this.campaignRepository = campaignRepository;
    }

    @GetMapping("/{id}/image")
    public Optional<ResponseEntity<String>> getCampaignImage(@PathVariable Long id) {
        return campaignRepository.findById(id)
                .map(campaign -> ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_TYPE, campaign.getMimeType())
                        .body(campaign.getImageBase64()));
    }
}