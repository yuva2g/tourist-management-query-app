package com.cognizant.fse.assignment.touristmanagementqueryapp.component;

import com.cognizant.fse.assignment.touristmanagementqueryapp.domain.TouristCompany;
import com.cognizant.fse.assignment.touristmanagementqueryapp.domain.TouristPlace;
import com.cognizant.fse.assignment.touristmanagementqueryapp.model.TouristCompanyDTO;
import com.cognizant.fse.assignment.touristmanagementqueryapp.repository.TouristCompanyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Map;

/**
 * MessageProducerService class should be used to receive messages from Kafka Topic
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MessageReceiverService {

    private final TouristCompanyRepository touristCompanyRepository;
    private static final String QUEUE_NAME = "yuva-fse-service-bus-queue";

    @JmsListener(destination = QUEUE_NAME, containerFactory = "jmsListenerContainerFactory")
    public void receiveMessage(Map<String, Object> messsageMap) {

        log.info("Tourist Company with id: {} received successfully", messsageMap.get("id"));
        TouristCompany touristCompany = map2TouristCompany(messsageMap);
        // TouristCompany touristCompany = mapToTouristCompany(message);
        touristCompanyRepository.save(touristCompany);
    }

    private TouristCompany map2TouristCompany(Map<String, Object> messsageMap) {
        TouristCompany touristCompany = new TouristCompany();
        touristCompany.setId(((Integer) messsageMap.get("id")).longValue());
        touristCompany.setBranchName((String) messsageMap.get("branchName"));
        touristCompany.setContact((String) messsageMap.get("contact"));
        touristCompany.setEmail((String) messsageMap.get("email"));
        touristCompany.setWebsite((String) messsageMap.get("website"));
        touristCompany.setTouristPlaces(((ArrayList<Object>) messsageMap.get("touristPlaces")).stream()
                .map(touristPlaceMap -> {
                    Map<String, Object> map = (Map<String, Object>) touristPlaceMap;
                    TouristPlace touristPlace = new TouristPlace();
                    touristPlace.setId(((Integer) map.get("id")).longValue());
                    touristPlace.setName((String) map.get("name"));
                    touristPlace.setTariff((Double) map.get("tariff"));
                    return touristPlace;
                }).toList());
        return touristCompany;
    }

    private TouristCompany mapToTouristCompany(TouristCompanyDTO message) {
        // implement this method
        TouristCompany touristCompany = new TouristCompany();
        touristCompany.setId(message.getId());
        touristCompany.setBranchName(message.getBranchName());
        touristCompany.setContact(message.getContact());
        touristCompany.setEmail(message.getEmail());
        touristCompany.setWebsite(message.getWebsite());
        touristCompany.setTouristPlaces(message.getTouristPlaces().stream().map(touristPlaceDTO -> {
            TouristPlace touristPlace = new TouristPlace();
            touristPlace.setId(touristPlaceDTO.getId());
            touristPlace.setName(touristPlaceDTO.getName());
            touristPlace.setTariff(touristPlaceDTO.getTariff());
            return touristPlace;
        }).toList());
        return touristCompany;
    }
}
