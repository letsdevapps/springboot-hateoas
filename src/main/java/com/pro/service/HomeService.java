package com.pro.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.pro.dto.HomeDTO;

@Service
public class HomeService {

	private List<HomeDTO> homeDtoList;

	public HomeService() {
		init();
	}

	public List<HomeDTO> getAllMessages() {
		return homeDtoList;
	}

	private void init() {
		homeDtoList = new ArrayList<HomeDTO>();

		HomeDTO m1 = new HomeDTO("mensagem 1");
		HomeDTO m2 = new HomeDTO("mensagem 2");
		HomeDTO m3 = new HomeDTO("mensagem 3");
		HomeDTO m4 = new HomeDTO("mensagem 4");
		HomeDTO m5 = new HomeDTO("mensagem 5");
		HomeDTO m6 = new HomeDTO("mensagem 6");
		HomeDTO m7 = new HomeDTO("mensagem 7");
		HomeDTO m8 = new HomeDTO("mensagem 8");
		HomeDTO m9 = new HomeDTO("mensagem 9");
		HomeDTO m10 = new HomeDTO("mensagem 10");

		homeDtoList.add(m1);
		homeDtoList.add(m2);
		homeDtoList.add(m3);
		homeDtoList.add(m4);
		homeDtoList.add(m5);
		homeDtoList.add(m6);
		homeDtoList.add(m7);
		homeDtoList.add(m8);
		homeDtoList.add(m9);
		homeDtoList.add(m10);
	}
}