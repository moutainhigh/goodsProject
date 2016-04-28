package com.mendao.business.dto;

import java.util.List;
import java.util.Map;

public class GrowthReport {

	private Long id;
	
	private List<String> content;
	
	private List<String> options;
	
	private Map<Long, String> stickers;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<String> getContent() {
		return content;
	}

	public void setContent(List<String> content) {
		this.content = content;
	}

	public Map<Long, String> getStickers() {
		return stickers;
	}

	public void setStickers(Map<Long, String> stickers) {
		this.stickers = stickers;
	}

	public List<String> getOptions() {
		return options;
	}

	public void setOptions(List<String> options) {
		this.options = options;
	}
	
	
}
