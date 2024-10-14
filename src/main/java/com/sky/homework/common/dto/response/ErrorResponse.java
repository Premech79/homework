package com.sky.homework.common.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Collection;

@Data
@AllArgsConstructor
public class ErrorResponse {

	private Collection<String> messages;

}