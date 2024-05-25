package com.hsn.restaurant.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PagebleResponse<T> {

	private List<T>content;
	private Long totalElement;
	private Integer totalPage;
	private Integer pageNumber;
	private boolean first;
	private boolean last;
}
