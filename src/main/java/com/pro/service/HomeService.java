package com.pro.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.pro.dto.HomeDTO;

@Service
public class HomeService {

	private List<HomeDTO> homeDtoList;

//	public HomeService() {
//		init();
//	}

	public List<HomeDTO> getAllMessages() {
		return homeDtoList;
	}

	public Page<HomeDTO> getAllMessages(Pageable pageable, String searchText) {
		init();
		if (searchText != null && !searchText.isEmpty()) {
			homeDtoList = homeDtoList.stream()
					.filter(homeDTO -> homeDTO.getMessage().toLowerCase().contains(searchText.toLowerCase()))
					.collect(Collectors.toList());
        }
		
		int start = (int) pageable.getOffset();
		int end = Math.min((start + pageable.getPageSize()), homeDtoList.size());

		List<HomeDTO> pageContent = homeDtoList.subList(start, end);

		return new PageImpl<>(pageContent, pageable, homeDtoList.size());
	}

	private void init() {
		homeDtoList = new ArrayList<HomeDTO>();
		
		for (int i=1; i<=10; i++) {
			HomeDTO m = new HomeDTO("mensagem " + i);
			homeDtoList.add(m);
		}
	}
}