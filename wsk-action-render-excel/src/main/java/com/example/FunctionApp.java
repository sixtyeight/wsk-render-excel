package com.example;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import lombok.Data;

/**
 * Hello FunctionApp
 */
public class FunctionApp {
	@Data
	public static class Payload {
		public List<Employee> employees = new ArrayList<>();
	}

	@Data
	public static class Employee {
		public String name;
		public String birthDate;
		public Double payment;
		public Double bonus;
	}

	public static JsonObject main(JsonObject args) throws IOException {
		Payload payload = new Gson().fromJson(args, Payload.class);
		List<Employee> employees = payload.employees;

		JsonObject response = new JsonObject();
		try (InputStream is = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("template.xlsx")) {
			try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
				Context context = new Context();
				context.putVar("employees", employees);
				JxlsHelper.getInstance().processTemplate(is, bos, context);

				response.addProperty("report", Base64.getEncoder().encodeToString(bos.toByteArray()));
			}
		}

		return response;
	}
}
