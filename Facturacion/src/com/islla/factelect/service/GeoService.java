package com.islla.factelect.service;

import java.util.Set;

import com.islla.factelect.domain.City;
import com.islla.factelect.domain.State;

public interface GeoService {

	public Set<State> findAllStates();

	public Set<City> findCitiesForState(String state);

}
